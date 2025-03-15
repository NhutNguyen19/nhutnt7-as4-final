package com.fis.bank.training.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;

    String email;

    List<String> roles;
}
