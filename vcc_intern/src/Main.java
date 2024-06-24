import entity.Memory;
import entity.Student;
import handle.Action;
import handle.ActionImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final Memory memory = Memory.getInstance();
    public static final Action action = ActionImpl.action;

    public static void main(String[] args) throws FileNotFoundException {
        readStudentFromFile();

        //Create menu
        System.out.println(
                "1. Them sinh vien" + "\n" +
                "2. Cap nhat thong tin sinh vien boi ID" + "\n" +
                "3. Xoa sinh vien boi ID" + "\n" +
                "4. Tim kiem sinh vien theo ten" + "\n" +
                "5. Sap xep sinh vien theo diem trung binh (GPA)" + "\n" +
                "6. Sap xep sinh vien theo ten" + "\n" +
                "7. Hien thi danh sach sinh vien" + "\n" +
                "8. Ghi danh sach sinh vien vao file \"student.txt\""+ "\n" +
                "0. Thoat"+ "\n"
        );

        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhap chuc nang ban chon: ");
            Integer input = (Integer) scanner.nextInt();
            scanner.nextLine();

            if(input == 1) {
                String name = scanner.nextLine();
                String sex = scanner.nextLine();
                scanner.nextLine();
                Integer age = Integer.valueOf(scanner.nextInt());
                Double toan = Double.valueOf(scanner.nextDouble());
                Double ly = Double.valueOf(scanner.nextDouble());
                Double hoa = Double.valueOf(scanner.nextDouble());

                action.addStudent(memory.id, name, sex, age, toan, ly, hoa);
                memory.id += 1;
            }
            else if(input == 2) {
                Integer id = Integer.valueOf(scanner.nextInt());
                scanner.nextLine();
                String name = scanner.nextLine();
                String sex = scanner.nextLine();
                Integer age = Integer.valueOf(scanner.nextInt());
                Double toan = Double.valueOf(scanner.nextDouble());
                Double ly = Double.valueOf(scanner.nextDouble());
                Double hoa = Double.valueOf(scanner.nextDouble());

                action.updateStudent(new Student(id, name, sex, age, toan, ly, hoa));
            }
            else if(input == 3) action.deleteStudent(Integer.valueOf(scanner.nextInt()));

            else if(input == 4) action.findByName(scanner.nextLine());

            else if(input == 5) action.sortByGPA();

            else if(input == 6) action.sortByName();

            else if(input == 7) action.showAllStudents();

            else if(input == 8) action.writeToFile();

            else break;
        }
    }

    /**
     * Read student list from file
     */
    public static void readStudentFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("student.txt"));
            String line = bufferedReader.readLine();

            while (line != null) {
                String name = line;
                String sex = bufferedReader.readLine();
                Integer age = Integer.valueOf(bufferedReader.readLine());
                Double toan = Double.valueOf(bufferedReader.readLine());
                Double ly = Double.valueOf(bufferedReader.readLine());
                Double hoa = Double.valueOf(bufferedReader.readLine());

                Student student = new Student(memory.id, name, sex, age, toan, ly, hoa);
                memory.studentMap.put(student.getId(), student);
                memory.id += 1;

                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}