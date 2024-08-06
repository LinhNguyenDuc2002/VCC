package com.example.otpauthentication.repository;

import com.example.otpauthentication.entity.EmployeeRole;
import com.example.otpauthentication.entity.EmployeeRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleReppository extends JpaRepository<EmployeeRole, EmployeeRolePK> {
}
