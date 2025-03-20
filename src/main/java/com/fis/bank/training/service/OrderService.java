package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse> getPendingOrders();
    void completeOrderApprovalTask(String taskId, boolean isApproved);
    void cancelOrderTask(String taskId);
}
