package com.example.ldap.service.impl;

import com.example.ldap.dto.EmployeeDTO;
import com.example.ldap.dto.EmployeeRequest;
import com.example.ldap.dto.LdapUser;
import com.example.ldap.entity.Department;
import com.example.ldap.entity.Employee;
import com.example.ldap.entity.Role;
import com.example.ldap.repository.DepartmentRepository;
import com.example.ldap.repository.EmployeeRepository;
import com.example.ldap.repository.RoleRepository;
import com.example.ldap.service.EmployeeService;
import org.apache.naming.NameParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public String hello() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            LdapUserDetails userDetails = (LdapUserDetails) authentication.getPrincipal();

            LdapUser user = ldapTemplate.findByDn(
                    new NameParser() {
                        @Override
                        public Name parse(String name) throws NamingException {
                            Name parsedName = new NameParserImpl().parse(name);

                            return parsedName;
                        }
                    }.parse(userDetails.getDn()),
                    LdapUser.class);

            return "Welcome " + user.getCn();
        }

        return null;
    }

    @Override
    public EmployeeDTO add(EmployeeRequest employeeRequest) throws Exception {
        if (!StringUtils.hasText(employeeRequest.getUsername()) ||
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
