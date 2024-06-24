package handle;

import entity.Student;

public interface Action {
    void addStudent(Integer id, String name, String sex, Integer age, Double toan, Double ly, Double hoa);

    void updateStudent(Student student);

    void deleteStudent(Integer id);

    void findByName(String name);

    void sortByGPA();

    void sortByName();

    void showAllStudents();

    void writeToFile();
}
