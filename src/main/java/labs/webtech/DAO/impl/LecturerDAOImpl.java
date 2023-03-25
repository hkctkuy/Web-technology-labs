package labs.webtech.DAO.impl;

import labs.webtech.DAO.CourseDAO;
import labs.webtech.DAO.LecturerDAO;
import labs.webtech.table.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    @Override
    public List<Integer> getFreeTime(Lecturer lecturer) {
        List<Integer> freeTime = new ArrayList<>(IntStream.range(0, 30).boxed().toList());
        try (Session session = sessionFactory.openSession()) {
            Query<Integer> query = session
                    .createQuery("SELECT ls.time FROM LecturerSchedule ls WHERE ls.lecturer = :lecturer", Integer.class)
                    .setParameter("lecturer", lecturer);
            List<Integer> notFreeTime = query.getResultList();
            freeTime.removeAll(notFreeTime);
            return freeTime;
        }
    }
}
