package com.fis.bank.training.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue
    UUID id;
    String username;
    String password;
    String email;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;
}
