package com.fis.bank.training.model;

import java.util.Set;
import java.util.UUID;

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
public class Role {

    @Id
    String name;
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;
}
