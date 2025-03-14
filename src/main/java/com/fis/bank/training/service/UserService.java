package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse updateUpdate(UserUpdateRequest request, String id);
    UserResponse getUserById(String id);

    void deleteUser(String id);
}
