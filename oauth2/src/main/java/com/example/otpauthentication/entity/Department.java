package com.example.otpauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "founded_date")
    private Date foundedDate;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Collection<Employee> employees;
}
