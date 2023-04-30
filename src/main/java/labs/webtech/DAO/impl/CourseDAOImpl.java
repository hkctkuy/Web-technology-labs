package labs.webtech.DAO.impl;

import labs.webtech.DAO.CourseDAO;
import labs.webtech.table.*;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseDAOImpl extends TableDAOImpl<Course, Long> implements CourseDAO {


    public CourseDAOImpl() {
        super(Course.class);
    }


    @Override
    public List<Lecturer> getLecturerList(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Query<LecturerDist> query = session
                    .createQuery("SELECT ld FROM LecturerDist ld WHERE ld.course = :course", LecturerDist.class)
                    .setParameter("course", course);
            List<LecturerDist> lecturerDistList = query.getResultList();
            if (lecturerDistList.size() == 0) {
                return null;
            }
            List<Lecturer> lecturerList = new ArrayList<>();
            for (LecturerDist lecturerDist: lecturerDistList) {
                lecturerList.add(lecturerDist.getLecturer());
            }
            return lecturerList;
        }
    }

    @Override
    public List<Course> getByYear(Integer year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Course> query = session
                    .createQuery("SELECT c FROM Course c " +
                            "WHERE c.year = :year",
                            Course.class)
                    .setParameter("year", year);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Course> getByYearWithoutSpec(Integer year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Course> query = session
                    .createQuery("SELECT c FROM Course c " +
                            "WHERE c.year = :year AND c.coverage != :coverage",
                            Course.class)
                    .setParameter("year", year)
                    .setParameter("coverage", Course.Coverage.SPEC);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Course> getSpec() {
        try (Session session = sessionFactory.openSession()) {
            Query<Course> query = session
                    .createQuery("SELECT c FROM Course c " +
                            "WHERE c.coverage = :coverage",
                            Course.class)
                    .setParameter("coverage", Course.Coverage.SPEC);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}
