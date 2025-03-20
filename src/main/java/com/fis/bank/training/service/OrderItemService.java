package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.OrderItemRequest;
import com.fis.bank.training.dto.response.OrderItemResponse;

import java.util.List;

public interface OrderItemService {

    String startOrderProcess(String userId);
    List<OrderItemResponse> insertOrder(OrderItemRequest request);
    List<OrderItemResponse> getOrderItems();
    void deleteOrderItem(String id);
}
