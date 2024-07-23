package com.example.otpauthentication.controller;

import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;
import com.example.otpauthentication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeRequest employeeRequest) throws Exception {
        return ResponseEntity.ok(employeeService.add(employeeRequest));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@AuthenticationPrincipal OAuth2User principal) throws Exception {
        return ResponseEntity.ok("Welcome " + principal.getName());
    }
}
