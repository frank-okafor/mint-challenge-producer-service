package com.mint.challenge.service;

import com.mint.challenge.Dtos.OrderReports;
import com.mint.challenge.Dtos.OrderRequest;
import com.mint.challenge.Dtos.ServiceResponse;

public interface OrderService {

	ServiceResponse<OrderReports> makeNewOrder(OrderRequest request, Boolean paidStatus);

	ServiceResponse<OrderReports> payForOrder(Long orderId);

}
