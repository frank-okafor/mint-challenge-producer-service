package com.mint.challenge.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.Dtos.OrderRequest;
import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.models.Order;
import com.mint.challenge.models.Product;
import com.mint.challenge.repository.OrderRepository;
import com.mint.challenge.service.OrderService;
import com.mint.challenge.service.ProductService;
import com.mint.challenge.utils.TestHelper;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class OrderServiceImplTest {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private ModelMapper mapper;
	@Mock
	private ProductService productService;
	@Mock
	private KafkaTemplate<String, OrderReports> kafkaTemplate;

	private OrderService orderService;

	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImpl(orderRepository, mapper, productService, kafkaTemplate);
	}

	@Test
	void testMakeNewOrder() {
		Product product = TestHelper.createProduct();
		Order order = TestHelper.createOrder();
		when(productService.saveUpdatedProduct(any(Product.class))).thenReturn(product);
		when(productService.findById(anyLong())).thenReturn(product);
		when(mapper.map(any(), any())).thenReturn(order);
		OrderRequest request = TestHelper.orderRequest();
		ServiceResponse<OrderReports> response = orderService.makeNewOrder(request, true);
		assertThat(response.getMessage())
				.isEqualTo("your order has been successfully registered and delivered. Thank You");
	}

}
