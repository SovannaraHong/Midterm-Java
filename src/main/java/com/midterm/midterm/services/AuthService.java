package com.midterm.midterm.services;

import com.midterm.midterm.dto.request.LoginRequest;
import com.midterm.midterm.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}