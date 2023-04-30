package labs.webtech.DAO;

import labs.webtech.table.Course;
import labs.webtech.table.Lecturer;

import java.util.List;

public interface CourseDAO extends TableDAO<Course, Long> {

    List<Lecturer> getLecturerList(Course course);

    List<Course> getByYear(Integer year);

    List<Course> getByYearWithoutSpec(Integer year);

    List<Course> getSpec();
}
