package com.fis.bank.training.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fis.bank.training.constant.Status;
import com.fis.bank.training.model.OrderItem;
import com.fis.bank.training.model.User;
import jakarta.persistence.OneToMany;
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
public class OrderRequest {
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    Date orderDate;
    Status status;
    double totalAmount;
    List<OrderItem> orderItems;
    User user;
}
