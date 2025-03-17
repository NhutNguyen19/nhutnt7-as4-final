package com.fis.bank.training.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fis.bank.training.constant.Status;
import com.fis.bank.training.model.OrderItem;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

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
    OrderItem orderItem;
}
