package com.mint.challenge.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mint.challenge.Dtos.ProductRequest;
import com.mint.challenge.Dtos.ServiceResponse;
import com.mint.challenge.exception.ProductServiceException;
import com.mint.challenge.models.Product;
import com.mint.challenge.repository.ProductRepository;
import com.mint.challenge.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = { "productCache" })
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper mapper;

	@Override
	public ServiceResponse<Product> createNewProduct(ProductRequest request) {
		if (productRepository.findByName(request.getName()).isPresent()) {
			return new ServiceResponse<>(HttpStatus.BAD_REQUEST,
					"Product with name " + request.getName() + " already exists.");
		}

		Product product = Product.builder().amount(request.getAmount()).description(request.getDescription())
				.name(request.getName()).numberInStock(request.getNumberInStock()).build();
		return new ServiceResponse<Product>(HttpStatus.OK, "record saved successfully", saveProduct(product));
	}

	@Override
	public ServiceResponse<Product> updateProductDetails(ProductRequest request) {
		if (productRepository.checkIfNameExists(request.getName(), request.getId()) > 0) {
			return new ServiceResponse<>(HttpStatus.BAD_REQUEST,
					"Product with name " + request.getName() + " is already used by another product.");
		}

		return productRepository.findById(request.getId()).map(product -> {
			product = mapper.map(request, Product.class);
			product.setDateModified(LocalDateTime.now());
			return new ServiceResponse<Product>(HttpStatus.OK, "record updated successfully",
					saveUpdatedProduct(product));
		}).orElseThrow(() -> new ProductServiceException(HttpStatus.NOT_FOUND,
				"product with id " + request.getId() + " does not exist"));

	}

	@Override
	public ServiceResponse<Page<Product>> getAllProducts(int pageNumber, int pagesize) {
		return new ServiceResponse<Page<Product>>(HttpStatus.OK, "products retreived successfully",
				getAll(pageNumber, pagesize));
	}

	@Override
	public ServiceResponse<Product> getProductByName(String productName) {
		return new ServiceResponse<Product>(HttpStatus.OK, "product found successfully",
				getProductByProductName(productName));
	}

	@CachePut(key = "#product.name", unless = "#result == null")
	private Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Cacheable(unless = "#result == null")
	private Product getProductByProductName(String productName) {
		return productRepository.findByName(productName).map(product -> {
			return product;
		}).orElseThrow(() -> new ProductServiceException(HttpStatus.NOT_FOUND,
				"product with name " + productName + " does not exist"));
	}

	@Override
	@CacheEvict(key = "#product.id")
	public Product saveUpdatedProduct(Product product) {
		return productRepository.save(product);
	}

	@Cacheable(unless = "#result == null")
	private Page<Product> getAll(int pageNumber, int pageSize) {
		Sort sort = Sort.by(Sort.Order.desc("dateCreated").ignoreCase());
		pageNumber = pageNumber == 0 ? 1 : pageNumber;
		pageSize = pageSize == 0 ? 10 : pageSize;
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		Page<Product> products = productRepository.findAll(pageable);
		return products;
	}

	@Override
	@Cacheable(unless = "#result == null")
	public Product findById(Long id) {
		return productRepository.findById(id).get();
	}

}
