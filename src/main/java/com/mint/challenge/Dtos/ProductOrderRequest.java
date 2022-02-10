package com.mint.challenge.Dtos;

import java.math.BigDecimal;

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
public class ProductOrderRequest {
	@NotNull(message = "productId cannot be null")
	private Long productId;
	@NotNull(message = "quantity cannot be null")
	private Long quantity;
	private String productName;
	private BigDecimal totalAmount;
}
