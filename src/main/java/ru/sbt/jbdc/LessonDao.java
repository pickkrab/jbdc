package ru.sbt.jbdc;

import java.util.List;

public interface LessonDao {
    void saveLesson(Lesson lesson);

    Lesson findById(int id);

    List<Lesson> findByName(String name);
}
