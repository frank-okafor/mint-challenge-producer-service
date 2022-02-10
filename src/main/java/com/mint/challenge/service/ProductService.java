package com.mint.challenge.service;

import org.springframework.data.domain.Page;

import com.mint.challenge.Dtos.ProductRequest;
import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.models.Product;

public interface ProductService {

	ServiceResponse<Product> createNewProduct(ProductRequest request);

	ServiceResponse<Product> updateProductDetails(ProductRequest request);

	ServiceResponse<Page<Product>> getAllProducts(int pageNumber, int pagesize);

	ServiceResponse<Product> getProductByName(String productName);

	Product findById(Long id);

	Product saveUpdatedProduct(Product product);

}
