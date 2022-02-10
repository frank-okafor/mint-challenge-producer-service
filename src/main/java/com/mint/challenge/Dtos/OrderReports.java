package com.mint.challenge.Dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class OrderReports {
	private Long id;
	private String deliveryDestination;
	private List<ProductOrderRequest> products;
	private String customerName;
	private String customerEmail;
	private String customerPhoneNumber;
	private BigDecimal totalOrderAmount;
	private LocalDateTime paidDate;
	private LocalDateTime dateCreated;
	private LocalDateTime dateModified;
	private String deliveryStatus;
	private Boolean paid;
}
