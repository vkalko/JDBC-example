package org.example;

import org.example.dao.StudentDAO;
import org.example.dao.impl.StudentDAOImpl;
import org.example.entity.Student;
import org.sqlite.SQLiteDataSource;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:students.db");
        StudentDAO studentDAO = new StudentDAOImpl(sqLiteDataSource);

        Student firstStudent = new Student(1, "Name1 Surname1", "economics");
        Student secondStudent = new Student(2, "Name2 Surname2", "engineering");
        Student thirdStudent = new Student(3, "Name3 Surname3", "law");
        Student firstStudentUpdated = new Student(1, "Name1 Surname1", "accounting");

        studentDAO.save(firstStudent);
        studentDAO.save(secondStudent);
        studentDAO.save(thirdStudent);

        List<Student> students = studentDAO.getAllStudents();
        System.out.println("After students were added:");
        students.forEach(System.out::println);

        studentDAO.update(firstStudentUpdated);
        System.out.println("After student was updated:");
        studentDAO.get(1).ifPresent(System.out::println);

        studentDAO.delete(secondStudent);

        students = studentDAO.getAllStudents();
        System.out.println("After student was deleted:");
        students.forEach(System.out::println);
    }
}
