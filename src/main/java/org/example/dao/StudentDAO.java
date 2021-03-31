package org.example.dao;

import org.example.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    void save(Student student);

    Optional<Student> get(int id);

    List<Student> getAllStudents();

    boolean update(Student student);

    void delete(Student student);

}
