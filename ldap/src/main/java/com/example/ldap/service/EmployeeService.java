package com.example.ldap.service;

import com.example.ldap.dto.EmployeeDTO;
import com.example.ldap.dto.EmployeeRequest;

public interface EmployeeService {
    String hello() throws Exception;

    EmployeeDTO add(EmployeeRequest employeeRequest) throws Exception;
}
