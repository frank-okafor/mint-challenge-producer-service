package com.mint.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mint.challenge.models.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByName(String name);

	@Query(value = "select count(*) from product p where p.name = :name and p.id != :id", nativeQuery = true)
	int checkIfNameExists(@Param("name") String name, @Param("id") Long id);
}
