package labs.webtech.DAO;

import labs.webtech.table.Course;
import labs.webtech.table.Exercise;
import labs.webtech.table.Lecturer;

import java.util.List;

public interface LecturerDAO extends TableDAO<Lecturer, Long> {

    void attachLecturerCourse(Lecturer lecturer, Course course);

    List<Course> getCourseList(Lecturer lecturer);

    boolean isFree(Lecturer lecturer, Integer time);

    List<Integer> getFreeTime(Lecturer lecturer);

    void bindToExercise(Lecturer lecturer, Exercise exercise, Integer time);
}
