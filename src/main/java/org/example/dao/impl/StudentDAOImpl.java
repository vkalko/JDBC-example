package org.example.dao.impl;

import org.example.dao.StudentDAO;
import org.example.entity.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final DataSource dataSource;

    public StudentDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        createTable();
    }

    @Override
    public void save(Student student) {
        String insertStudentQuery = "INSERT INTO students VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertStudentQuery);
            statement.setInt(1, student.getId());
            statement.setString(2, student.getFullName());
            statement.setString(3, student.getSpecialty());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional<Student> get(int id) {
        Student student = null;
        String selectStudentQuery = "SELECT * FROM students";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectStudentQuery);
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFullName(resultSet.getString("fullname"));
                student.setSpecialty(resultSet.getString("specialty"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String getAllStudentsQuery = "SELECT * FROM students";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllStudentsQuery);

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFullName(resultSet.getString("fullname"));
                student.setSpecialty(resultSet.getString("specialty"));
                students.add(student);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return students;
    }

    @Override
    public boolean update(Student student) {
        String updateStudentQuery = "UPDATE students SET fullname = ?, specialty = ? WHERE id = ?";
        boolean success = false;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateStudentQuery);
            statement.setString(1, student.getFullName());
            statement.setString(2, student.getSpecialty());
            statement.setInt(3, student.getId());
            success = statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return success;
    }

    @Override
    public void delete(Student student) {
        String deleteStudentQuery = "DELETE FROM students WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteStudentQuery);
            statement.setInt(1, student.getId());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void createTable() {
        try (Connection connection = dataSource.getConnection()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY NOT NULL, " +
                    "fullname VARCHAR(50), " +
                    "specialty VARCHAR(50))";
            Statement statement = connection.createStatement();
            boolean success = statement.execute(createTableQuery);
            if (success) {
                System.out.println("Table was created.");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
