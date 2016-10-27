package ru.sbt.jbdc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection("jdbc:h2:~/test", "admin", "secret")) {
            createTable(connection);
            addLessons(connection);
            addStudents(connection);
            printStudents(connection);
        }
    }

    private static void addLessons(Connection connection) throws SQLException {
        LessonDao lessonDao = new LessonDaoImpl(connection);
        lessonDao.saveLesson(new Lesson(1, "Spring", Date.valueOf("2016-01-01")));
        lessonDao.saveLesson(new Lesson(2, "jdbc", Date.valueOf("2016-01-10")));
        lessonDao.saveLesson(new Lesson(3, "threads", Date.valueOf("2016-01-20")));
    }

    private static void addStudents(Connection connection) throws SQLException {
        StudentDao studentDao = new StudentDaoImpl(connection);
        studentDao.saveStudent(new Student(1, "Евгений", "Шипов"));
        studentDao.saveStudent(new Student(2, "Роман", "Орлов"));
        studentDao.saveStudent(new Student(3, "Владимир", "Папин"));
    }

    private static void printStudents(Connection connection) {
        StudentDao studentDao = new StudentDaoImpl(connection);
        List<Student> students = studentDao.findByName("Евгений");
        for (Student s : students) {
            System.out.println(s);
        }
        students = studentDao.findBySurName("Папин");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE Students" +
                "(" +
                "id int," +
                "name varchar(255)," +
                "surname varchar(255)," +
                "primary key(id)" +
                ");");

        connection.createStatement().executeUpdate("CREATE TABLE Lessons" +
                "(" +
                "id int," +
                "name varchar(255)," +
                "date date" +
                "primary key(id)" +
                ");");
        connection.createStatement().executeUpdate("CREATE TABLE StudentVisits" +
                "(" +
                "studentId int," +
                "lessonId int," +
                "primary key(studentId, lessonId)," +
                "foreign key(studentId) references Students(id)," +
                "foreign key(lessonId) references Lessons(id)," +
                ");");
    }
}
