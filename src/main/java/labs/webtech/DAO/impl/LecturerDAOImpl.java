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
        LecturerDist lecturerDist = new LecturerDist(lecturer, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(lecturerDist);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Course> getCourseList(Lecturer lecturer) {
        try (Session session = sessionFactory.openSession()) {
            Query<LecturerDist> query = session
                    .createQuery("SELECT ld FROM LecturerDist ld WHERE ld.lecturer = :lecturer", LecturerDist.class)
                    .setParameter("lecturer", lecturer);
            List<LecturerDist> courseDistList = query.getResultList();
            if (courseDistList.size() == 0) {
                return null;
            }
            List<Course> courseList = new ArrayList<>();
            for (LecturerDist lecturerDist: courseDistList) {
                courseList.add(lecturerDist.getCourse());
            }
            return courseList;
        }
    }
}
