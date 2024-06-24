package com.example.vcc_spring.service;

import com.example.vcc_spring.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAll();

    Student add(Student student);

    Student update(Integer id, Student student) throws Exception;

    void delete(Integer id) throws Exception;

    List<Student> findByName(String name);

    List<Student> sortBy(String by);
}
