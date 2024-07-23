package com.example.otpauthentication.service.impl;

import com.example.otpauthentication.cache.CacheMemory;
import com.example.otpauthentication.dto.Credential;
import com.example.otpauthentication.dto.EmailMessage;
import com.example.otpauthentication.dto.EmployeeDTO;
import com.example.otpauthentication.dto.EmployeeRequest;
import com.example.otpauthentication.entity.Department;
import com.example.otpauthentication.entity.Employee;
import com.example.otpauthentication.entity.Role;
import com.example.otpauthentication.repository.DepartmentRepository;
import com.example.otpauthentication.repository.EmployeeRepository;
import com.example.otpauthentication.repository.RoleRepository;
import com.example.otpauthentication.security.util.OTPUtil;
import com.example.otpauthentication.security.util.SecurityUtil;
import com.example.otpauthentication.service.EmailService;
import com.example.otpauthentication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Value("${spring.mail.from-address}")
    private String sender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CacheManager cacheManager;

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

    @Override
    public String authenticate(Credential credential) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword()));
            Employee employee = employeeRepository.findByUsername(credential.getUsername()).get();

            // Generate OTP
            String otp = OTPUtil.generateOTP();

            // Save
            String uuidKey = UUID.randomUUID().toString();
            cacheManager.getCache("user").put(
                    uuidKey,
                    CacheMemory.builder()
                            .key(uuidKey)
                            .username(credential.getUsername())
                            .password(credential.getPassword())
                            .otp(otp)
                            .build()
                    );

            // Send OTP
            emailService.sendEmail(
                    EmailMessage.builder()
                            .subject("Authenticate your account")
                            .receiver(employee.getEmail())
                            .sender(sender)
                            .otp(otp)
                            .build()
            );

            return uuidKey;
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Username or password is incorrect!");
        }
    }
}
