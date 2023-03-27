package labs.webtech.DAO.impl;

import labs.webtech.DAO.AudienceDAO;
import labs.webtech.table.Audience;
import labs.webtech.table.AudienceSchedule;

import labs.webtech.table.Exercise;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AudienceDAOImpl extends TableDAOImpl<Audience, Long> implements AudienceDAO {

    public AudienceDAOImpl(){
        super(Audience.class);
    }

    @Override
    public boolean isFree(Audience audience, Integer time) {
        try (Session session = sessionFactory.openSession()) {
            Query<AudienceSchedule> query = session
                    .createQuery("SELECT asch FROM AudienceSchedule asch WHERE asch.time = :time AND asch.audience = :audience", AudienceSchedule.class)
                    .setParameter("audience", audience)
                    .setParameter("time", time);
            return query.getResultList().size() == 0;
        }
    }

    @Override
    public List<Audience> getFreeAudienceList(Integer time, Integer capacity) {
        try (Session session = sessionFactory.openSession()) {
            // Get audiences with the specified capacity
            Query<Audience> query = session
                    .createQuery("SELECT a FROM Audience a WHERE a.capacity >= :capacity", Audience.class)
                    .setParameter("capacity", capacity);
            List<Audience> audienceList = query.getResultList();
            if (query.getResultList().size() == 0) {
                return null;
            }
            // Remove all audiences not free at the specified time
            List<Audience> notFreeAudienceList = new ArrayList<>();
            for (Audience audience: audienceList) {
                Query<AudienceSchedule> q = session
                        .createQuery("SELECT asch FROM AudienceSchedule asch WHERE asch.time = :time AND asch.audience = :audience", AudienceSchedule.class)
                        .setParameter("audience", audience)
                        .setParameter("time", time);
                if (q.getResultList().size() != 0) {
                    notFreeAudienceList.add(audience);
                }
            }
            audienceList.removeAll(notFreeAudienceList);
            return audienceList;
        }
    }

    @Override
    public void bindToExercise(Audience audience, Exercise exercise, Integer time) {
        try (Session session = sessionFactory.openSession()) {
            AudienceSchedule audienceSchedule = new AudienceSchedule(audience, exercise, time);
            session.beginTransaction();
            session.saveOrUpdate(audienceSchedule);
            session.getTransaction().commit();
        }
    }
}
