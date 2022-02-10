package com.mint.challenge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.Dtos.OrderRequest;
import com.mint.challenge.Dtos.ProductOrderRequest;
import com.mint.challenge.Dtos.ProductRequest;
import com.mint.challenge.models.Order;
import com.mint.challenge.models.Product;

public class TestHelper {

	public static ProductRequest createProductRequest() {
		return ProductRequest.builder().amount(new BigDecimal(90)).description("lovely product").name("Iphone13")
				.numberInStock(10L).build();
	}

	public static ProductOrderRequest makeProuctOrderRequest() {
		return ProductOrderRequest.builder().productId(4L).productName("Iphone13").quantity(1L).build();
	}

	public static OrderRequest orderRequest() {
		return OrderRequest.builder().customerEmail("okafor.e.frank@gmail.com").customerName("frank")
				.deliveryDestination("Lagos").customerPhoneNumber("07060999234").products(productsList()).build();
	}

	public static List<ProductOrderRequest> productsList() {
		return new ArrayList<>(List.of(makeProuctOrderRequest()));
	}

	public static Product createProduct() {
		return Product.builder().amount(new BigDecimal(190)).description("lovely product").name("Iphone13")
				.numberInStock(100L).build();
	}

	public static Order createOrder() {
		return Order.builder().customerEmail("okafor.e.frank@gmail.com").customerName("frank")
				.customerPhoneNumber("07060999234").deliveryStatus("goog").deliveryDestination("Lagos")
				.reports(new OrderReports()).build();
	}

}
