package labs.webtech.DAO;

import labs.webtech.table.Course;
import labs.webtech.table.Group;

import java.util.List;

public interface GroupDAO extends TableDAO<Group, Long> {

    List<Group> getByStream(Integer stream);

    List<Group> getByYear(Integer study_year);

    List<Group> getByStreamAndYear(Integer stream, Integer study_year);

    List<Group> getByCourse(Course course);

    void attachGroupCourse(Group group, Course course);

    int groupSize(Group group);

}
