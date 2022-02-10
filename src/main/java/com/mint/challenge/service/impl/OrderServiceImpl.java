package com.mint.challenge.service.impl;

import static com.mint.challenge.configuration.KafkaConfig.KAFKATOPIC;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.Dtos.OrderRequest;
import com.mint.challenge.Dtos.ProductOrderRequest;
import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.enums.DeliveryStatus;
import com.mint.challenge.exception.ProductServiceException;
import com.mint.challenge.models.Order;
import com.mint.challenge.models.Product;
import com.mint.challenge.repository.OrderRepository;
import com.mint.challenge.service.OrderService;
import com.mint.challenge.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = { "orderCache" })
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final ModelMapper mapper;
	private final ProductService productService;
	private final KafkaTemplate<String, OrderReports> kafkaTemplate;

	@Override
	public ServiceResponse<OrderReports> makeNewOrder(OrderRequest request, Boolean paidStatus) {
		if (request.getProducts().isEmpty()) {
			return new ServiceResponse<>(HttpStatus.BAD_REQUEST, "Products list cannot be empty");
		}
		String message = "your order has been successfully saved, awaiting payment";
		Order order = mapper.map(request, Order.class);
		if (paidStatus != null && paidStatus) {
			order.setPaidDate(LocalDateTime.now());
			order.setDeliveryStatus(DeliveryStatus.DELIVERED.name());
			order.setPaid(true);
		}
		Map.Entry<BigDecimal, List<ProductOrderRequest>> validations = processReportProductsList(request.getProducts());
		order.setTotalAmount(validations.getKey());
		OrderReports reports = mapOrderReport(order);
		reports.setProducts(validations.getValue());
		order.setReports(reports);
		orderRepository.save(order);
		if (order.getPaid()) {
			message = "your order has been successfully registered and delivered. Thank You";
			sendKafkaReport(reports);
		}
		return new ServiceResponse<OrderReports>(HttpStatus.OK, message, reports);
	}

	private Map.Entry<BigDecimal, List<ProductOrderRequest>> processReportProductsList(
			List<ProductOrderRequest> orderedProducts) {
		BigDecimal totalAmount = new BigDecimal("0.00");
		List<ProductOrderRequest> result = new ArrayList<>();
		for (ProductOrderRequest request : orderedProducts) {
			Product product = productService.findById(request.getProductId());
			if (product.getNumberInStock() == 0) {
				throw new ProductServiceException(HttpStatus.BAD_REQUEST,
						"Sorry!! Product with name " + product.getName() + " is out of stock");
			}
			if (request.getQuantity() > product.getNumberInStock()) {
				throw new ProductServiceException(HttpStatus.BAD_REQUEST,
						"Sorry!! We do not have this quantity in stock for " + product.getName());
			}
			request.setProductName(product.getName());
			BigDecimal amount = product.getAmount().multiply(new BigDecimal(request.getQuantity()));
			request.setTotalAmount(amount);
			totalAmount = totalAmount.add(amount);
			result.add(request);
			product.setNumberInStock(product.getNumberInStock() - request.getQuantity());
			productService.saveUpdatedProduct(product);
		}
		return Maps.immutableEntry(totalAmount, result);
	}

	@Override
	public ServiceResponse<OrderReports> payForOrder(Long orderId) {
		return orderRepository.findById(orderId).map(order -> {
			OrderReports reports = order.getReports();
			reports.setPaidDate(LocalDateTime.now());
			reports.setDeliveryStatus(DeliveryStatus.DELIVERED.name());
			reports.setPaid(true);
			order.setReports(reports);
			order.setPaidDate(LocalDateTime.now());
			order.setDeliveryStatus(DeliveryStatus.DELIVERED.name());
			order.setPaid(true);
			orderRepository.save(order);
			sendKafkaReport(reports);
			return new ServiceResponse<OrderReports>(HttpStatus.OK, "order record updated successfully", reports);
		}).orElseThrow(() -> new ProductServiceException(HttpStatus.NOT_FOUND,
				"order with id " + orderId + " does not exist"));
	}

	@Async
	public void sendKafkaReport(OrderReports reports) {
		kafkaTemplate.send(KAFKATOPIC, reports);
	}

	private static OrderReports mapOrderReport(Order order) {
		return OrderReports.builder().customerEmail(order.getCustomerEmail()).customerName(order.getCustomerName())
				.customerPhoneNumber(order.getCustomerPhoneNumber()).dateCreated(order.getDateCreated())
				.dateModified(order.getDateModified()).deliveryDestination(order.getDeliveryDestination())
				.deliveryStatus(order.getDeliveryStatus()).id(order.getId()).paidDate(order.getPaidDate())
				.paid(order.getPaid()).totalOrderAmount(order.getTotalAmount()).build();
	}

}
