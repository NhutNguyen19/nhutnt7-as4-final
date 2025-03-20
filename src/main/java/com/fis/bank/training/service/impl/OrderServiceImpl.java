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
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    RuntimeService runtimeService;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được để trống.");
        }

        // Ánh xạ request thành entity Order
        Order order = orderMapper.toOrder(request);
        order.setCheckIn(LocalDateTime.now());
        order.setStatus(Status.PENDING); // Đơn hàng chờ duyệt

        // Lấy danh sách OrderItem từ request
        List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(orderItemRequest -> orderItemRepository.findById(orderItemRequest.getId())
                        .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + orderItemRequest.getId())))
                .collect(Collectors.toList());

        // Gán danh sách OrderItem vào Order
        order.setOrderItems(orderItems);

        // Lưu Order vào database
        order = orderRepository.save(order);

        // Bắt đầu quy trình Camunda
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", order.getId());
        variables.put("userId", request.getUser().getId());

        runtimeService.startProcessInstanceByKey("order_process", variables);

        return orderMapper.toOrderResponse(order);
    }


    @Override
    public OrderResponse checkout(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không thể đặt hàng với ID: " + id));

        order.setCheckOut(LocalDateTime.now());
        order.setStatus(Status.COMPLETE);
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }
}
