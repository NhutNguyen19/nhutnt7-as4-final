package com.fis.bank.training.dto.response;

import com.fis.bank.training.constant.Status;
import com.fis.bank.training.model.OrderItem;
import com.fis.bank.training.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    Date orderDate;
    Status status;
    double totalAmount;
    String userId; // Thêm userId vào đây
    List<OrderItemResponse> orderItems;
}
