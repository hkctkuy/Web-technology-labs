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
    public List<Student> getByYear(Integer study_year) {
        return getByGroupList(groupDAO.getByYear(study_year));
    }

    @Override
    public List<Student> getByStreamAndYear(Integer stream, Integer study_year) {
        return getByGroupList(groupDAO.getByStreamAndYear(stream, study_year));
    }

    @Override
    public List<Student> getByCourse(Course course) {
        if (course.getCoverage() != Course.Coverage.SPEC) {
            return getByGroupList(groupDAO.getByCourse(course));
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Spec_course_dist> query = session
                    .createQuery("SELECT scd FROM Spec_course_dist scd WHERE scd.course = :course", Spec_course_dist.class)
                    .setParameter("course", course);
            List<Spec_course_dist> spec_course_distList = query.getResultList();
            if (spec_course_distList.size() == 0) {
                return null;
            }
            List<Student> studentList = new ArrayList<>();
            for (Spec_course_dist spec_course_dist: spec_course_distList) {
                studentList.add(spec_course_dist.getStudent());
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
        if (course.getStudy_year() > student.getGroup().getStudy_year()) {
            throw new Exception("Pretty young student for that course");
        }
        Spec_course_dist spec_Spec_course_dist = new Spec_course_dist(student, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(spec_Spec_course_dist);
            session.getTransaction().commit();
        }
    }
}
