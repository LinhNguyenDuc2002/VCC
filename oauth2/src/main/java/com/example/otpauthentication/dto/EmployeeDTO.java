package com.example.otpauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    private String username;

    private String fullName;

    private String password;

    private String email;
}
