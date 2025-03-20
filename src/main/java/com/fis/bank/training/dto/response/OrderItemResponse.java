package com.fis.bank.training.dto.response;

import com.fis.bank.training.model.Order;
import com.fis.bank.training.model.Product;
import com.fis.bank.training.model.User;
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
    String productName;  // Chỉ lưu tên sản phẩm thay vì object Product
    String userId;
}
