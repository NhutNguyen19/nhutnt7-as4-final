package com.fis.bank.training.dto.response;

import com.fis.bank.training.model.Order;
import com.fis.bank.training.model.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    String id;
    int quantity;
    double unitPrice;
    Order order;
    Product product;
}
