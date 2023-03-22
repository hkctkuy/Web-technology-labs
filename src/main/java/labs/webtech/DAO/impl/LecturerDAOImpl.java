package labs.webtech.DAO.impl;

import labs.webtech.DAO.LecturerDAO;
import labs.webtech.table.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LecturerDAOImpl extends TableDAOImpl<Lecturer, Long> implements LecturerDAO {


    public LecturerDAOImpl() {
        super(Lecturer.class);
    }


    @Override
    public void attachLecturerCourse(Lecturer lecturer, Course course) {
        Lecturer_dist lecturer_dist = new Lecturer_dist(lecturer, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(lecturer_dist);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Course> getCourseList(Lecturer lecturer) {
        try (Session session = sessionFactory.openSession()) {
            Query<Lecturer_dist> query = session
                    .createQuery("SELECT ld FROM Lecturer_dist ld WHERE ld.lecturer = :lecturer", Lecturer_dist.class)
                    .setParameter("lecturer", lecturer);
            List<Lecturer_dist> course_distList = query.getResultList();
            if (course_distList.size() == 0) {
                return null;
            }
            List<Course> courseList = new ArrayList<>();
            for (Lecturer_dist lecturer_dist: course_distList) {
                courseList.add(lecturer_dist.getCourse());
            }
            return courseList;
        }
    }
}
