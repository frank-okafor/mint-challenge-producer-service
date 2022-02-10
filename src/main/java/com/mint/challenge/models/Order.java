package com.mint.challenge.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.enums.DeliveryStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Setter
@Getter
@ToString
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Order extends BaseModel<Product> implements Serializable {

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Builder.Default
	@Column(name = "paid_status", nullable = false)
	private Boolean paid = false;

	@Column(name = "delivery_destination")
	private String deliveryDestination;

	@Column(name = "customer_name")
	private String customerName;

	@Email
	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "customer_phone_number")
	private String customerPhoneNumber;

	@Column(name = "paid_date")
	private LocalDateTime paidDate;

	@Builder.Default
	@Column(name = "delivery_status")
	private String deliveryStatus = DeliveryStatus.PENDING.name();

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "order_report")
	@Basic(fetch = FetchType.LAZY)
	private OrderReports reports;

}
