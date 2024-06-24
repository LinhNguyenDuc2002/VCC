package com.example.vcc_spring.service;

import com.example.vcc_spring.entity.Student;
import com.example.vcc_spring.repository.StudentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository = StudentRepository.getInstance();

    @Cacheable("student") //The result will be saved in cache under 'student'
    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student add(Student student) {
        student.setId(studentRepository.id);
        student.setAverage(setAverage(student.getToan(), student.getLy(), student.getHoa()));
        student.setResult(setResult(student.getAverage()));

        return studentRepository.save(student);
    }

    @Override
    public Student update(Integer id, Student student) throws Exception {
        if(!studentRepository.existsById(id)) {
            throw new Exception("Not found student");
        }

        Student studentEntity = studentRepository.findById(id);
        studentEntity.setName(student.getName());
        studentEntity.setSex(student.getSex());
        studentEntity.setAge(student.getAge());
        studentEntity.setToan(student.getToan());
        studentEntity.setLy(student.getLy());
        studentEntity.setHoa(student.getHoa());
        studentEntity.setAverage(setAverage(studentEntity.getToan(), studentEntity.getLy(), studentEntity.getHoa()));
        studentEntity.setResult(setResult(studentEntity.getAverage()));

        return studentRepository.save(studentEntity);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if(!studentRepository.existsById(id)) {
            throw new Exception("Not found student");
        }

        studentRepository.delete(id);
    }

    @Cacheable("student")
    @Override
    public List<Student> findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> sortBy(String by) {
        if(by.equals("name")) return studentRepository.sortByName();
        else if(by.equals("gpa")) return studentRepository.sortByGpa();
        else return null;
    }

    private double setAverage(Double toan, Double ly, Double hoa) {
        double kq = (toan + ly + hoa) / 3;
        return Math.ceil(kq * 100.0)/100.0;
    }

    private String setResult(Double average) {
        if (average >= 8) return "Gioi";

        else if (average >= 6.5) return "Kha";

        else if (average >= 5) return "Trung Binh";

        else return "Yeu";
    }
}
