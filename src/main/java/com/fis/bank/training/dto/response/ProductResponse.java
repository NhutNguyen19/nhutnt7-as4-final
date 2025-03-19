package com.fis.bank.training.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fis.bank.training.model.Category;
import com.fis.bank.training.model.OrderItem;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    String id;
    String name;
    String description;
    double price;
    int stockQuantity;
    Category category;
}
