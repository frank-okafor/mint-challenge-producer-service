package com.mint.challenge.Dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ProductRequest {
	@NotNull(message = "product name cannot be null")
	@NotBlank(message = "product name must be provided")
	private String name;
	@NotNull(message = "product description cannot be null")
	@NotBlank(message = "product description must be provided")
	private String description;
	@NotNull(message = "product amount cannot be null")
	private BigDecimal amount;
	@NotNull(message = "number of remaining product in stock must be provided")
	private Long numberInStock;
	private long id = 0;

}
