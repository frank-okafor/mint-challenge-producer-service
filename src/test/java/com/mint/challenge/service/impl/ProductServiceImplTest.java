package com.mint.challenge.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.models.Product;
import com.mint.challenge.repository.ProductRepository;
import com.mint.challenge.service.ProductService;
import com.mint.challenge.utils.TestHelper;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProductServiceImplTest {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ModelMapper mapper;

	private ProductService productService;

	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductServiceImpl(productRepository, mapper);
	}

	@Test
	void testCreateNewProduct() {
		Product product = TestHelper.createProduct();
		when(productRepository.save(any(Product.class))).thenReturn(product);
		ServiceResponse<Product> response = productService.createNewProduct(TestHelper.createProductRequest());
		assertThat(response.getMessage()).isEqualTo("record saved successfully");
	}
}
