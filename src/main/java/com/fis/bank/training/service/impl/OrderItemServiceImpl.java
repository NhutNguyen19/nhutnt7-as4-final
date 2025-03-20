package com.fis.bank.training.service.impl;

import com.fis.bank.training.dto.request.OrderItemRequest;
import com.fis.bank.training.dto.request.ProductRequest;
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
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    RuntimeService runtimeService;
    TaskService taskService;


    @Override
    public String startOrderProcess(String userId) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("order",
                Variables.putValue("userId", userId));
        return "Quy trình đặt hàng đã bắt đầu: " + processInstance.getId();
    }

    @Override
    public List<OrderItemResponse> insertOrder(OrderItemRequest request) {
        // Tìm đơn hàng
        Order order = orderRepository.findById(request.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        List<String> orderItemIds = new ArrayList<>();

        // Lặp qua danh sách sản phẩm để thêm vào đơn hàng
        for (ProductRequest productRequest : request.getProducts()) {
            Product product = productRepository.findById(productRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            OrderItem orderItem = OrderItem.builder()
                    .quantity(productRequest.getStockQuantity()) // Lấy số lượng đặt hàng
                    .unitPrice(product.getPrice()) // Lấy giá từ sản phẩm
                    .order(order) // Gán vào đơn hàng
                    .product(product) // Gán vào sản phẩm
                    .build();

            orderItem = orderItemRepository.save(orderItem); // Lưu vào DB
            orderItemResponses.add(orderItemMapper.toOrderItemResponse(orderItem));
            orderItemIds.add(orderItem.getId()); // Lưu danh sách orderItemId
        }

        // Lấy task "Chọn sản phẩm" cho user đang thực hiện đơn hàng
        Task task = taskService.createTaskQuery()
                .taskDefinitionKey("select_product") // Đảm bảo taskDefinitionKey đúng
                .taskAssignee(request.getOrder().getUser().getId()) // Người dùng đặt hàng
                .singleResult();

        // Chỉ hoàn thành task nếu nó tồn tại
        if (task != null) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("orderItemIds", orderItemIds); // Truyền danh sách sản phẩm đã đặt
            variables.put("orderId", order.getId()); // Truyền ID đơn hàng để dùng sau này

            taskService.complete(task.getId(), variables);
        } else {
            throw new RuntimeException("Không tìm thấy task 'Chọn sản phẩm' để hoàn thành");
        }
        return orderItemResponses;
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
