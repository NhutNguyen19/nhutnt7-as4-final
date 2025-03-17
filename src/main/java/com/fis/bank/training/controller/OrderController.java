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

    @PostMapping("/{id}/check-out")
    ApiResponse<OrderResponse> checkout(@PathVariable String id){
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.checkout(id))
                .build();
    }
}
