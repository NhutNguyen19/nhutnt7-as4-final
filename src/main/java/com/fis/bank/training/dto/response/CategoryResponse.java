package com.fis.bank.training.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fis.bank.training.model.Product;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String id;
    String name;
    String description;
    Product product;
}
