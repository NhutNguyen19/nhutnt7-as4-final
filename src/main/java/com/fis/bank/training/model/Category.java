package com.fis.bank.training.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String description;

    @ManyToOne
    @JsonBackReference
    Product product;

    public Category(String name, String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public Category(String defaultCategory) {
    }
}
