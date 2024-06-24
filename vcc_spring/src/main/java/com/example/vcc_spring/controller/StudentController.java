package com.example.vcc_spring.controller;

import com.example.vcc_spring.entity.Student;
import com.example.vcc_spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PutMapping("/student/{id}")
    public Student update(@PathVariable Integer id,@RequestBody Student student) throws Exception {
        return studentService.update(id, student);
    }

    @DeleteMapping("/student/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        studentService.delete(id);
    }

    @GetMapping("/student/search")
    public List<Student> search(@RequestParam(value = "name", required = true) String name) {
        return studentService.findByName(name);
    }

    @GetMapping("/student/sort")
    public List<Student> sort(@RequestParam(value = "by", required = true) String sort) {
        return studentService.sortBy(sort);
    }

    @GetMapping("/student")
    public List<Student> getAll() {
        return studentService.getAll();
    }

}
