package com.fis.bank.training.dto.request;

import com.fis.bank.training.model.Order;
import com.fis.bank.training.model.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {
    String id;
    int quantity;
    double unitPrice;
    List<ProductRequest> products;
    Order order;
}
