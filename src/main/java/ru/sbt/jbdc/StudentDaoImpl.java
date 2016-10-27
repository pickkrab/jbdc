package ru.sbt.jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    private final Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveStudent(Student student) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Students (id, name, surname) VALUES (?, ?, ?)");
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Student selectOne(List<Student> students) {
        return students.isEmpty() ? null : students.get(0);
    }

    private List<Student> execute(String sql, Consumer<PreparedStatement> consumer) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            return DB.dbSudent(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private interface Consumer<T> {
        void accept(T t) throws Exception;
    }

    @Override
    public Student findById(int id) {
        return selectOne(execute("SELECT * FROM Students WHERE id = ?", s -> s.setInt(1, id)));
    }

    @Override
    public List<Student> findByName(String name) {
        return execute("SELECT * FROM Students WHERE name = ?", s -> s.setString(1, name));
    }

    @Override
    public List<Student> findBySurName(String surname) {
        return execute("SELECT * FROM Students WHERE surname = ?", s -> s.setString(1, surname));
    }
}
