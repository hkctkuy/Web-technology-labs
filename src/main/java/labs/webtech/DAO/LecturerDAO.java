package labs.webtech.DAO;

import labs.webtech.table.Course;
import labs.webtech.table.Lecturer;

import java.util.List;

public interface LecturerDAO extends TableDAO<Lecturer, Long> {

    void attachLecturerCourse(Lecturer lecturer, Course course);

    List<Course> getCourseList(Lecturer lecturer);
}
