package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
