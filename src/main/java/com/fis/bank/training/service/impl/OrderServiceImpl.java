package com.fis.bank.training.service.impl;

import com.fis.bank.training.constant.Status;
import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderItemResponse;
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
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    TaskService taskService;

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

        // 🔹 Bắt đầu quy trình Camunda
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", order.getId());
        variables.put("userId", request.getUser().getId());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("order_process", variables);

        // 🔹 Kiểm tra nếu process instance đã khởi chạy
        if (processInstance == null) {
            throw new RuntimeException("Không thể khởi chạy quy trình xử lý đơn hàng!");
        }

        // 🔹 Lấy Task "Tạo hóa đơn" để hoàn thành (nếu cần)
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .taskDefinitionKey("approve") // ID của User Task "Tạo hóa đơn"
                .taskAssignee("user")
                .singleResult();

        if (task != null) {
            taskService.complete(task.getId(), variables);
            System.out.println("Hoàn thành task 'Tạo hóa đơn' cho đơn hàng: " + order.getId());
        } else {
            System.out.println("Không tìm thấy task 'Tạo hóa đơn'!");
        }

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getPendingOrders() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskDefinitionKey("order_pending")
                .taskAssignee("user")
                .list();

        // Lấy danh sách orderId từ các task đang chờ xử lý
        List<String> orderIds = tasks.stream()
                .map(task -> taskService.getVariable(task.getId(), "orderId"))
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toList());

        // Lấy danh sách đơn hàng từ database dựa vào orderId
        List<Order> pendingOrders = orderRepository.findByIdIn(orderIds);

        return pendingOrders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Chuyển đổi từ Order entity sang OrderResponse
     */
    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .checkIn(order.getCheckIn())
                .checkOut(order.getCheckOut())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUser().getId())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .id(item.getId())
                                .productName(item.getProduct().getName())
                                .unitPrice(item.getUnitPrice())
                                .quantity(item.getQuantity())
                                .userId(order.getUser().getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public void completeOrderApprovalTask(String taskId, boolean isApproved) {
        // Lấy thông tin task từ Camunda
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        // Kiểm tra task có thuộc nhóm "admin" không
        if (!task.getAssignee().equals("admin")) {
            throw new RuntimeException("Task must be assigned to admin group");
        }

        // Lấy orderId từ biến quy trình
        String orderId = taskService.getVariable(taskId, "orderId").toString();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Xử lý phê duyệt đơn hàng
        Map<String, Object> variables = new HashMap<>();
        variables.put("isApproved", isApproved); // Truyền biến vào Camunda để điều hướng Gateway

        // Cập nhật trạng thái đơn hàng vào database
        orderRepository.save(order);

        // Hoàn thành task duyệt đơn hàng
        taskService.complete(taskId, variables);
    }


    public void cancelOrderTask(String taskId) {
        // Lấy task từ Camunda
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        // Kiểm tra task có được gán cho user không
        if (!task.getAssignee().equals("user")) {
            throw new RuntimeException("Task must be assigned to a user");
        }

        // Lấy orderId từ biến quy trình
        String orderId = taskService.getVariable(taskId, "orderId").toString();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Xóa đơn hàng khỏi database
        orderRepository.delete(order);

        // Hoàn thành task hủy đơn hàng
        taskService.complete(taskId);
    }


}
