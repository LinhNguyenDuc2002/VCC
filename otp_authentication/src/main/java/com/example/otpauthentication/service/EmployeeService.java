package com.example.otpauthentication.service;

import com.example.otpauthentication.dto.Credential;
import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface EmployeeService {
    String hello() throws Exception;

    EmployeeDTO add(EmployeeRequest employeeRequest) throws Exception;

    String authenticate(Credential credential) throws NoSuchAlgorithmException, InvalidKeyException;
}
