package com.fis.bank.training.service.impl;

import com.fis.bank.training.constant.Status;
import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderResponse;
import com.fis.bank.training.mapper.OrderMapper;
import com.fis.bank.training.model.Order;
import com.fis.bank.training.model.OrderItem;
import com.fis.bank.training.repository.OrderItemRepository;
import com.fis.bank.training.repository.OrderRepository;
import com.fis.bank.training.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = orderMapper.toOrder(request);
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItem().getId())
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        order.setOrderItem(orderItem);
        order.setCheckIn(LocalDateTime.now());
        order.setStatus(Status.PROCESSING);

        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse checkout(String id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Không thể đặt hàng"));
        order.setCheckOut(LocalDateTime.now());
        order.setStatus(Status.COMPLETE);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }
}
