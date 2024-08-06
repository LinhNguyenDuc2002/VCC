package com.example.otpauthentication.repository;

import com.example.otpauthentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name IN :roleNames")
    List<Role> findAllByRoleName(List<String> roleNames);
}
