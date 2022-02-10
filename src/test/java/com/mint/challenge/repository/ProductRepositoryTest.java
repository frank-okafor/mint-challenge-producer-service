package com.mint.challenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.mint.challenge.models.Product;
import com.mint.challenge.utils.TestHelper;

@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() throws Exception {
		productRepository.deleteAll();
	}

	@Test
	void testFindByName() {
		Product product = TestHelper.createProduct();
		productRepository.save(product);
		assertThat(productRepository.findByName(product.getName()).isPresent()).isTrue();
	}

	@Test
	void testCheckIfNameExists() {
		Product product = TestHelper.createProduct();
		product = productRepository.save(product);
		int result = productRepository.checkIfNameExists(product.getName(), product.getId());
		assertThat(result > 0);
	}

}
