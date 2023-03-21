package labs.webtech.DAO;

import labs.webtech.table.Student;
import labs.webtech.table.Group;
import java.util.List;

public interface StudentDAO extends TableDAO<Student, Long> {

    List<Student> getByGroup(Group group);

    List<Student> getByGroupList(List<Group> groupList);

    List<Student> getByStream(Integer stream);

    List<Student> getByYear(Integer study_year);

    List<Student> getByStreamAndYear(Integer stream, Integer study_year);
}
