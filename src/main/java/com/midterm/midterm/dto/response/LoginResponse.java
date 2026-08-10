package com.midterm.midterm.dto.response;

import com.midterm.midterm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long sid;
    private String userName;
    private Role role;
}