package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.request.LoginRequest;
import com.midterm.midterm.dto.response.LoginResponse;
import com.midterm.midterm.entities.Staff;
import com.midterm.midterm.exception.AccountDisabledException;
import com.midterm.midterm.exception.InvalidCredentialsException;
import com.midterm.midterm.repository.StaffRepository;
import com.midterm.midterm.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StaffRepository staffRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        Staff staff = staffRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (staff.getPassword() == null || !staff.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        if (!staff.getStatus()) {
            throw new AccountDisabledException("This account has been deactivated. Contact an administrator.");
        }

        return LoginResponse.builder()
                .sid(staff.getSid())
                .userName(staff.getUserName())
                .role(staff.getRole().name())
                .build();
    }
}