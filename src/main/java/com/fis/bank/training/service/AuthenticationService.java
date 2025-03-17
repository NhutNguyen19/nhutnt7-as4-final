package com.fis.bank.training.service;

import com.fis.bank.training.dto.request.*;
import com.fis.bank.training.dto.response.AuthenticationResponse;
import com.fis.bank.training.dto.response.IntrospectResponse;
import com.fis.bank.training.dto.response.UserResponse;
import com.fis.bank.training.model.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    void resetPassword(ResetPasswordRequest request);

    UserResponse createAdmin(UserCreationRequest request);
}
