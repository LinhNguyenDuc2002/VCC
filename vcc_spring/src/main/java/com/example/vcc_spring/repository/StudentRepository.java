package com.example.vcc_spring.repository;

import com.example.vcc_spring.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {
    public Integer id;

    public Map<Integer, Student> studentMap;

    public static StudentRepository instance;

    private StudentRepository() {
        this.id = (Integer) 1;
        this.studentMap = new HashMap<>();
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }

        return instance;
    }

    public Student save(Student student) {
        if(!studentMap.containsKey(student.getId())){
            studentMap.put(student.getId(), student);
            id += 1;
        }
        else {
            studentMap.put(student.getId(), student);
        }

        return student;
    }

    public boolean existsById(Integer id) {
        return studentMap.containsKey(id);
    }

    public Student findById(Integer id) {
        if(!studentMap.containsKey(id)) return null;

        for(Student student : studentMap.values()) {
            if(student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    public void delete(Integer id) {
        studentMap.remove(id);
    }

    public List<Student> findByName(String name) {
        List<Student> students = new ArrayList<>();

        for (Student student : studentMap.values()) {
            if (student.getName().contains(name)) {
                students.add(student);
            }
        }

        return students;
    }

    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    public List<Student> sortByName() {
        List<Student> students = new ArrayList<>(studentMap.values());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return students;
    }

    public List<Student> sortByGpa() {
        List<Student> students = new ArrayList<>(studentMap.values());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o1.getAverage() - o2.getAverage());
            }
        });

        return students;
    }
}
