package com.fis.bank.training.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.saveUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("Successful")
                .data(userService.getUsers())
                .build();
    }

    @PutMapping("/{id}/update")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable("id") String id) {
        log.info("Updating user with ID: {}", id);
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.updateUser(request, id))
                .build();
    }

    @GetMapping("/{id}/user")
    ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.getUserById(id))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<UserResponse> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder().message("Successful").build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.getMyInfo())
                .build();
    }
}
