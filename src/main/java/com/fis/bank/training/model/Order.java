package com.fis.bank.training.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fis.bank.training.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Date orderDate;
    Status status;
    double totalAmount;

    @OneToMany
    @JsonBackReference
    OrderItem orderItem;


}
