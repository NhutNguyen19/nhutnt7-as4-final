package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.UserCreationRequest;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.model.Role;
import com.fis.bank.training.model.User;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( request.getUsername() );
        user.setPassword( request.getPassword() );
        user.setEmail( request.getEmail() );

        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.password( user.getPassword() );
        userResponse.email( user.getEmail() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userResponse.roles( new ArrayList<Role>( set ) );
        }

        return userResponse.build();
    }
}
