package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderResponse;
import com.fis.bank.training.model.Order;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse checkout(String id);
}
