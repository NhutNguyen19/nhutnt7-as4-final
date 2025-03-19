package com.fis.bank.training.service.impl;

import com.fis.bank.training.dto.request.OrderItemRequest;
import com.fis.bank.training.dto.response.OrderItemResponse;
import com.fis.bank.training.exception.AppException;
import com.fis.bank.training.exception.ErrorCode;
import com.fis.bank.training.mapper.OrderItemMapper;
import com.fis.bank.training.mapper.OrderMapper;
import com.fis.bank.training.model.Order;
import com.fis.bank.training.model.OrderItem;
import com.fis.bank.training.model.Product;
import com.fis.bank.training.repository.OrderItemRepository;
import com.fis.bank.training.repository.OrderRepository;
import com.fis.bank.training.repository.ProductRepository;
import com.fis.bank.training.service.OrderItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    OrderItemMapper orderItemMapper;
    OrderItemRepository orderItemRepository;
    ProductRepository productRepository;
    OrderRepository orderRepository;

    @Override
    public OrderItemResponse insertOrder(OrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // ✅ Sửa lỗi ép kiểu sai
        Product product = productRepository.findById(request.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        OrderItem orderItem = OrderItem.builder()
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice()) // ✅ Sửa lỗi gọi `getPrice()`
                .order(order)
                .product(product) // ✅ Sửa lỗi truyền `Product`
                .build();

        orderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toOrderItemResponse(orderItem);
    }

    @Override
    public List<OrderItemResponse> getOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream().map(orderItemMapper::toOrderItemResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(String id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderItem"));
        orderItemRepository.delete(orderItem);
    }
}
