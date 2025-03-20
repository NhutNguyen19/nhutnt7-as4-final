package com.fis.bank.training.controller;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderResponse;
import com.fis.bank.training.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @PostMapping("/create-order")
    ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.createOrder(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<OrderResponse>> getPendingOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .data(orderService.getPendingOrders())
                .build();
    }

    @PostMapping("/approve")
    ApiResponse<String> completeOrderApprovalTask(@RequestParam String taskId, @RequestParam boolean isApproved){
        orderService.completeOrderApprovalTask(taskId, isApproved);
        return ApiResponse.<String>builder()
                .build();
    }

    @PostMapping("/cancel")
    ApiResponse<String> cancelOrder(@RequestParam String taskId){
        orderService.cancelOrderTask(taskId);
        return ApiResponse.<String>builder().build();
    }
}
