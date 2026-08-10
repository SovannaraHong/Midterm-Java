package com.midterm.midterm.dto.request;

import com.midterm.midterm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequest {
    private String userName;
    private String password;
    private Role role;
}