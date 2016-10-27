package ru.sbt.jbdc;

import java.util.List;

public interface StudentDao {
    void saveStudent(Student student);

    Student findById(int id);

    List<Student> findByName(String name);

    List<Student> findBySurName(String surname);
}
