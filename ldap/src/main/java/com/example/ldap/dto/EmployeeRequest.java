package com.example.ldap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeRequest {
    @NotNull
    private String username;

    @NotNull
    @JsonProperty("full_name")
    private String fullName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @JsonProperty("department_id")
    private Long departmentId;

    private List<String> roles;
}
