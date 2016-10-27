package ru.sbt.jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentVisitsDaoImpl implements  StudentVisitsDao {
    private final Connection connection;

    public StudentVisitsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void newPoint(int lessonId, int studentId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO StudentVisits (lessonId, studentId) VALUES (?, ?)");
            statement.setInt(1, lessonId);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
