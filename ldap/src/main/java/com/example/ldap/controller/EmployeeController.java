package com.example.ldap.controller;

import com.example.ldap.dto.EmployeeDTO;
import com.example.ldap.dto.EmployeeRequest;
import com.example.ldap.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LdapTemplate ldapTemplate;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeRequest employeeRequest) throws Exception {
        return ResponseEntity.ok(employeeService.add(employeeRequest));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() throws Exception {
        return ResponseEntity.ok(employeeService.hello());
    }
}
