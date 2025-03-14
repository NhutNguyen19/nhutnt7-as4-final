package com.fis.bank.training.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis.bank.training.model.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;

    @JsonIgnore
    String password;
    String email;

    List<Role> roles;
}
