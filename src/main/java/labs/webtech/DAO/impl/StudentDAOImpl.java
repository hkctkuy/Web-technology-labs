package labs.webtech.DAO.impl;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.StudentDAO;
import labs.webtech.table.*;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Student> getByYear(Integer year) {
        return getByGroupList(groupDAO.getByYear(year));
    }

    @Override
    public List<Student> getByStreamAndYear(Integer stream, Integer year) {
        return getByGroupList(groupDAO.getByStreamAndYear(stream, year));
    }

    @Override
    public List<Student> getByCourse(Course course) {
        if (course.getCoverage() != Course.Coverage.SPEC) {
            return getByGroupList(groupDAO.getByCourse(course));
        }
        try (Session session = sessionFactory.openSession()) {
            Query<SpecCourseDist> query = session
                    .createQuery("SELECT scd FROM SpecCourseDist scd WHERE scd.course = :course", SpecCourseDist.class)
                    .setParameter("course", course);
            List<SpecCourseDist> specCourseDistList = query.getResultList();
            if (specCourseDistList.size() == 0) {
                return null;
            }
            List<Student> studentList = new ArrayList<>();
            for (SpecCourseDist specCourseDist: specCourseDistList) {
                studentList.add(specCourseDist.getStudent());
            }
            return studentList;
        }
    }

    @SneakyThrows
    @Override
    public void attachStudentSpecCourse(Student student, Course course) {
        if (course.getCoverage() != Course.Coverage.SPEC) {
            throw new Exception("A single student can only be enrolled in a special course.");
        }
        if (course.getYear() > student.getGroup().getYear()) {
            throw new Exception("Pretty young student for that course");
        }
        SpecCourseDist specCourseDist = new SpecCourseDist(student, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(specCourseDist);
            session.getTransaction().commit();
        }
    }
}
