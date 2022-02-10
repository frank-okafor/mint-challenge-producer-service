package com.mint.challenge.Dtos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class OrderRequest {

	private Long orderId;
	@NotNull(message = "delivery destination cannot be null")
	@NotBlank(message = "delivery destination must be provided")
	private String deliveryDestination;
	@NotNull(message = "products list cannot be null")
	private List<ProductOrderRequest> products;
	@NotNull(message = "customer Name cannot be null")
	@NotBlank(message = "customer Name must be provided")
	private String customerName;
	@Email
	@NotNull(message = "customer Email cannot be null")
	@NotBlank(message = "customer Email must be provided")
	private String customerEmail;
	@Pattern(regexp = "^0[7-9][0-1]\\\\d{8}$", message = "Phone number must be digits and not more than 9 characters")
	@NotNull(message = "customer PhoneNumber cannot be null")
	@NotBlank(message = "customer PhoneNumber must be provided")
	private String customerPhoneNumber;
}
