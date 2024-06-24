package handle;

import entity.Memory;
import entity.Student;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActionImpl implements Action {
    public static final Memory memory = Memory.getInstance();

    public static Action action = new ActionImpl();

    private ActionImpl() {
    }

    @Override
    public void addStudent(Integer id, String name, String sex, Integer age, Double toan, Double ly, Double hoa) {
        Student student = new Student(id, name, sex, age, toan, ly, hoa);
        memory.studentMap.put(id, student);
        memory.id += 1;
    }

    @Override
    public void updateStudent(Student student) {
        memory.studentMap.put(student.getId(), student);
    }

    @Override
    public void deleteStudent(Integer id) {
        if (!memory.studentMap.containsKey(id)) {
            System.out.println("Error!");
        }

        memory.studentMap.remove(id);
    }

    @Override
    public void findByName(String name) {
        for (Student student : memory.studentMap.values()) {
            if (student.getName().contains(name)) {
                System.out.println(student);
            }
        }
    }

    @Override
    public void sortByGPA() {
        List<Student> students = new ArrayList<>(memory.studentMap.values());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o1.getAverage() - o2.getAverage());
            }
        });

        printStudents(students);
    }

    @Override
    public void sortByName() {
        List<Student> students = new ArrayList<>(memory.studentMap.values());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        printStudents(students);
    }

    @Override
    public void showAllStudents() {
        printStudents(new ArrayList<>(memory.studentMap.values()));
    }

    @Override
    public void writeToFile() {
        try {
            FileWriter output = new FileWriter("student1.txt");

            for (Student student : memory.studentMap.values()) {
                output.write(String.valueOf(student));
                output.write("\n");
            }
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Success!");
    }

    private void printStudents(List<Student> students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
