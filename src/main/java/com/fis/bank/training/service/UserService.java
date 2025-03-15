package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse saveUser(UserCreationRequest request);

    List<UserResponse> getUsers();

    UserResponse updateUser(UserUpdateRequest request, UUID id);
    UserResponse getUserById(UUID id);

    void deleteUser(UUID id);

    UserResponse getMyInfo();
}
