package labs.webtech.DAO.impl;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.table.Course;
import labs.webtech.table.Course_dist;
import labs.webtech.table.Group;
import labs.webtech.table.Student;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDAOImpl extends TableDAOImpl<Group, Long> implements GroupDAO {

    public GroupDAOImpl(){
        super(Group.class);
    }

    @Override
    public List<Group> getByStream(Integer stream) {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session
                    .createQuery("SELECT g FROM Group g WHERE g.stream = :stream", Group.class)
                    .setParameter("stream", stream);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Group> getByYear(Integer study_year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session
                    .createQuery("SELECT g FROM Group g WHERE g.study_year = :study_year", Group.class)
                    .setParameter("study_year", study_year);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Group> getByStreamAndYear(Integer stream, Integer study_year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session
                    .createQuery("SELECT g FROM Group g WHERE g.stream = :stream and g.study_year = :study_year", Group.class)
                    .setParameter("stream", stream)
                    .setParameter("study_year", study_year);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Group> getByCourse(Course course) {
        if (course.getCoverage() == Course.Coverage.SPEC) {
            return null;
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Course_dist> query = session
                    .createQuery("SELECT cd FROM Course_dist cd WHERE cd.course = :course", Course_dist.class)
                    .setParameter("course", course);
            List<Course_dist> course_distList = query.getResultList();
            if (course_distList.size() == 0) {
                return null;
            }
            List<Group> groupList = new ArrayList<>();
            for (Course_dist course_dist: course_distList) {
                groupList.add(course_dist.getGroup());
            }
            return groupList;
        }
    }


    @SneakyThrows
    @Override
    public void attachGroupCourse(Group group, Course course) {
        if (course.getCoverage() == Course.Coverage.SPEC) {
            throw new Exception("It is not possible to register an entire group for a special course");
        }
        if (course.getStudy_year() > group.getStudy_year()) {
            throw new Exception("Pretty young group for that course");
        }
                Course_dist course_dist = new Course_dist(group, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(course_dist);
            session.getTransaction().commit();
        }
    }

    @Override
    public int groupSize(Group group) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session
                    .createQuery("SELECT s FROM Student s WHERE s.group = :group", Student.class)
                    .setParameter("group", group);
            List<Student> studentList = query.getResultList();
            return studentList == null ? 0 : studentList.size();
        }
    }

}
