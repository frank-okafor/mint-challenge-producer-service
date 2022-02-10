package com.mint.challenge.controller;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.Dtos.OrderRequest;
import com.mint.challenge.Dtos.ProductRequest;
import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.models.Product;
import com.mint.challenge.service.OrderService;
import com.mint.challenge.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ApplicationController {

	private final OrderService orderService;
	private final ProductService productService;

	@PostMapping("products")
	@ApiOperation(value = "create a new product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceResponse.class) })
	public ResponseEntity<ServiceResponse<Product>> addNewProduct(@Valid @RequestBody ProductRequest request) {
		ServiceResponse<Product> response = productService.createNewProduct(request);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PutMapping("products")
	@ApiOperation(value = "update existing product details. The id in the product request is compulsory")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
	public ResponseEntity<ServiceResponse<Product>> updateProductDetails(@Valid @RequestBody ProductRequest request) {
		ServiceResponse<Product> response = productService.updateProductDetails(request);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("products")
	@ApiOperation(value = "get all products : paginated")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
	public ResponseEntity<ServiceResponse<Page<Product>>> getAllProducts(
			@RequestParam(value = "pageNumber", required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
		ServiceResponse<Page<Product>> response = productService.getAllProducts(pageNumber, pageSize);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("products/getbyname")
	@ApiOperation(value = "get product by name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
	public ResponseEntity<ServiceResponse<Product>> getProductByName(
			@RequestParam(value = "productName", required = true) String productName) {
		ServiceResponse<Product> response = productService.getProductByName(productName);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PostMapping("orders")
	@ApiOperation(value = "make a new order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
	public ResponseEntity<ServiceResponse<OrderReports>> makeNewOrder(@Valid @RequestBody OrderRequest request,
			@RequestParam(value = "paidStatus", required = false) Boolean paidStatus) {
		ServiceResponse<OrderReports> response = orderService.makeNewOrder(request, paidStatus);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PutMapping("orders/{id}")
	@ApiOperation(value = "pay for an order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful", response = ServiceResponse.class),
			@ApiResponse(code = 500, message = "internal error - critical!", response = ServiceException.class) })
	public ResponseEntity<ServiceResponse<OrderReports>> getAllStocks(@PathVariable("id") Long orderId) {
		ServiceResponse<OrderReports> response = orderService.payForOrder(orderId);
		return new ResponseEntity<>(response, response.getStatus());
	}

}
