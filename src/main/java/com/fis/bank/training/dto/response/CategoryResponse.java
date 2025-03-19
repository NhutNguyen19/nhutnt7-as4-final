package com.fis.bank.training.dto.response;

import com.fis.bank.training.model.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String id;
    String name;
    String description;
    List<ProductResponse> products;
}
