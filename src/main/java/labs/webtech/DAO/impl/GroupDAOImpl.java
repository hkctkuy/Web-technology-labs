package labs.webtech.DAO.impl;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.table.Group;

import labs.webtech.table.Group;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
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
}
