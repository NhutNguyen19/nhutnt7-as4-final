package com.fis.bank.training.controller;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.OrderItemRequest;
import com.fis.bank.training.dto.response.OrderItemResponse;
import com.fis.bank.training.service.OrderItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OrderItems")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderItemController {

    OrderItemService orderItemService;

    @PostMapping("/create-order-item")
    ApiResponse<OrderItemResponse> createOrderItem(@RequestBody OrderItemRequest request){
        return ApiResponse.<OrderItemResponse>builder()
                .data(orderItemService.insertOrder(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<OrderItemResponse>> getOrderItems(){
        return ApiResponse.<List<OrderItemResponse>>builder()
                .data(orderItemService.getOrderItems())
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<String> deleteOrderItem(@PathVariable String id){
        orderItemService.deleteOrderItem(id);
        return ApiResponse.<String>builder()
                .build();
    }
}
