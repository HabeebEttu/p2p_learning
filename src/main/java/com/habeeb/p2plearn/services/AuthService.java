package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AuthResponse;
import com.habeeb.p2plearn.dto.LoginRequest;
import com.habeeb.p2plearn.dto.RegisterRequest;
import com.habeeb.p2plearn.models.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    void register(RegisterRequest request);
    User getCurrentUser();
}
