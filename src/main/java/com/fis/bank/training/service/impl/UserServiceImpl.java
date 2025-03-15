package com.fis.bank.training.service.impl;

import com.fis.bank.training.constant.PredefinedRole;
import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.request.UserUpdateRequest;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.exception.AppException;
import com.fis.bank.training.exception.ErrorCode;
import com.fis.bank.training.mapper.UserMapper;
import com.fis.bank.training.model.Role;
import com.fis.bank.training.model.User;
import com.fis.bank.training.repository.RoleRepository;
import com.fis.bank.training.repository.UserRepository;
import com.fis.bank.training.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findById(String.valueOf(PredefinedRole.USER_ROLE))
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roles.add(userRole);

        user.setRoles(roles);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        var role = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(role));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findById(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
