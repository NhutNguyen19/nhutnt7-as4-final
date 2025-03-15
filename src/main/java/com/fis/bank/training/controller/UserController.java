package com.fis.bank.training.controller;

import com.fis.bank.training.dto.ApiResponse;
import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.saveUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .message("Successful")
                .data(userService.getUsers())
                .build();
    }

    @PutMapping("/{id}/update")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable("id") UUID id){
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.updateUser(request, id))
                .build();
    }

    @GetMapping("/{id}/user")
    ApiResponse<UserResponse> getUserById(@PathVariable("id") UUID id){
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.getUserById(id))
                .build();
    }

    @DeleteMapping("/{id}/delete")
    ApiResponse<UserResponse> deleteUser(@PathVariable("id") UUID id){
        userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .message("Successful")
                .data(userService.getMyInfo())
                .build();
    }
}
