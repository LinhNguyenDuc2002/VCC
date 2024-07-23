package com.example.otpauthentication.service.impl;

import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;
import com.example.otpauthentication.entity.Department;
import com.example.otpauthentication.entity.Employee;
import com.example.otpauthentication.entity.Role;
import com.example.otpauthentication.repository.DepartmentRepository;
import com.example.otpauthentication.repository.EmployeeRepository;
import com.example.otpauthentication.repository.RoleRepository;
import com.example.otpauthentication.security.util.SecurityUtil;
import com.example.otpauthentication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String hello() throws Exception {
        Optional<String> username = SecurityUtil.getCurrentLoggedInUser();
        Employee employee = employeeRepository.findByUsername(username.get())
                .orElseThrow(() -> {
                    return new Exception("Employee is not found");
                });

        return "Welcome " + employee.getFullName();
    }

    @Override
    public EmployeeDTO add(EmployeeRequest employeeRequest) throws Exception {
        if(!StringUtils.hasText(employeeRequest.getUsername()) ||
                !StringUtils.hasText(employeeRequest.getPassword()) ||
                !StringUtils.hasText(employeeRequest.getFullName()) ||
                !StringUtils.hasText(employeeRequest.getEmail()) ||
                employeeRequest.getRoles().isEmpty()) {
            throw new Exception("The input is invalid");
        }

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> {
                    return new Exception("Department is not found");
                });

        List<Role> roles = roleRepository.findAllByRoleName(employeeRequest.getRoles());

        Employee employee = Employee.builder()
                .username(employeeRequest.getUsername())
                .password(passwordEncoder.encode(employeeRequest.getPassword()))
                .fullName(employeeRequest.getFullName())
                .email(employeeRequest.getEmail())
                .department(department)
                .roles(new ArrayList<Role>(roles))
                .build();
        employeeRepository.save(employee);

        roles.stream().forEach(role -> {
            role.getEmployees().add(employee);
        });
        roleRepository.saveAll(roles);

        return EmployeeDTO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .fullName(employee.getFullName())
                .password(employee.getPassword())
                .email(employee.getEmail())
                .build();
    }
}
