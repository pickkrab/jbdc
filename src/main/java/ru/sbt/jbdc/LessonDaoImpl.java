package ru.sbt.jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LessonDaoImpl implements LessonDao {
    private final Connection connection;

    public LessonDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveLesson(Lesson lesson) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Lesson (id, name, date) VALUES (?, ?, ?)");
            statement.setInt(1, lesson.getId());
            statement.setString(2, lesson.getName());
            statement.setDate(3, lesson.getDate());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Lesson selectOne(List<Lesson> lessons) {
        return lessons.isEmpty() ? null : lessons.get(0);
    }

    private interface Consumer<T> {
        void accept(T t) throws Exception;
    }

    private List<Lesson> execute(String sql, LessonDaoImpl.Consumer<PreparedStatement> consumer) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            consumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            return DB.dbLesson(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lesson findById(int id) {
        return selectOne(execute("SELECT * FROM Lessons WHERE id = ?", s -> s.setInt(1, id)));
    }

    @Override
    public List<Lesson> findByName(String name) {
        return execute("SELECT * FROM Lessons WHERE name = ?", s -> s.setString(1, name));
    }
}
