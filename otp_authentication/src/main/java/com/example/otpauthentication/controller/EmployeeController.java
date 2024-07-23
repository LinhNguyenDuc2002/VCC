package com.example.otpauthentication.controller;

import com.example.otpauthentication.dto.Credential;
import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;
import com.example.otpauthentication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeRequest employeeRequest) throws Exception {
        return ResponseEntity.ok(employeeService.add(employeeRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credential credential) throws Exception {
        return ResponseEntity.ok(employeeService.authenticate(credential));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOTP(
            @RequestParam(name = "key") String key,
            @RequestParam(name = "otp") String otp) throws Exception {
        return ResponseEntity.ok(employeeService.hello());
    }
}
