package com.example.otpauthentication.service;

import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;

public interface EmployeeService {
    String hello() throws Exception;

    EmployeeDTO add(EmployeeRequest employeeRequest) throws Exception;
}
