package com.midterm.midterm.dto.response;

import com.midterm.midterm.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {
    private Long sid;
    private String userName;
    private Role role;
    private String imageUrl;
    // password intentionally excluded - never sent back in responses
}