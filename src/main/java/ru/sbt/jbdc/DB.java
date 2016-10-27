package ru.sbt.jbdc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DB {
    public static List<Student> dbSudent(ResultSet result) throws SQLException {
        List<Student> students = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String surname = result.getString("surname");
            students.add(new Student(id, name, surname));
        }
        return students;
    }

    public static List<Lesson> dbLesson(ResultSet result) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt("id");
            String NameofLesson = result.getString("name");
            Date date = result.getDate("date");
            lessons.add(new Lesson(id, NameofLesson, date));
        }
        return lessons;
    }
}
