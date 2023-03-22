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
            Query<Lecturer_dist> query = session
                    .createQuery("SELECT ld FROM Lecturer_dist ld WHERE ld.course = :course", Lecturer_dist.class)
                    .setParameter("course", course);
            List<Lecturer_dist> lecturer_distList = query.getResultList();
            if (lecturer_distList.size() == 0) {
                return null;
            }
            List<Lecturer> lecturerList = new ArrayList<>();
            for (Lecturer_dist lecturer_dist: lecturer_distList) {
                lecturerList.add(lecturer_dist.getLecturer());
            }
            return lecturerList;
        }
    }
}
