package com.fis.bank.training.dto.request;

import com.fis.bank.training.model.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String id;
    String name;
    String description;
    double price;
    int stockQuantity;

    Category category;
}
