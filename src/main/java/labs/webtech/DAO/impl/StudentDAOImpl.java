package labs.webtech.DAO.impl;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.StudentDAO;
import labs.webtech.table.Group;
import labs.webtech.table.Student;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl extends TableDAOImpl<Student, Long> implements StudentDAO {

    public StudentDAOImpl() {
        super(Student.class);
    }

    @Autowired
    private GroupDAO groupDAO = new GroupDAOImpl();

    @Override
    public List<Student> getByGroup(Group group) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session
                    .createQuery("SELECT s FROM Student s WHERE s.group = :group", Student.class)
                    .setParameter("group", group);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Student> getByGroupList(List<Group> groupList) {
        List<Student> studentList = null;
        if (groupList == null) {
            return null;
        }
        for (Group group: groupList) {
            if (studentList == null) {
                studentList = getByGroup(group);
            } else {
                studentList.addAll(getByGroup(group));
            }
        }
        return studentList;
    }

    @Override
    public List<Student> getByStream(Integer stream) {
        return getByGroupList(groupDAO.getByStream(stream));
    }

    @Override
    public List<Student> getByYear(Integer study_year) {
        return getByGroupList(groupDAO.getByYear(study_year));
    }

    @Override
    public List<Student> getByStreamAndYear(Integer stream, Integer study_year) {
        return getByGroupList(groupDAO.getByStreamAndYear(stream, study_year));
    }
}
