package com.fis.bank.training.service;

import java.util.List;
import java.util.UUID;

import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;

public interface UserService {
    UserResponse saveUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse updateUser(UserUpdateRequest request, String id);

    UserResponse getUserById(String id);

    void deleteUser(String id);

    UserResponse getMyInfo();
}
