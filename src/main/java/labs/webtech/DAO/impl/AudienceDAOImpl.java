package labs.webtech.DAO.impl;

import labs.webtech.DAO.AudienceDAO;
import labs.webtech.table.Audience;
import labs.webtech.table.AudienceSchedule;

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
    public List<Audience> getByTime(Integer time) {
        try (Session session = sessionFactory.openSession()) {
            // Get list of non-free audiences
            Query<AudienceSchedule> query = session
                    .createQuery("SELECT ad FROM AudienceSchedule ad WHERE ad.time = :time", AudienceSchedule.class)
                    .setParameter("time", time);
            List<AudienceSchedule> audienceScheduleList = query.getResultList().size() == 0 ? null : query.getResultList();
            List<Audience> audienceList = new ArrayList<>(getAll());
            if (audienceScheduleList == null) {
                return audienceList;
            }
            List<Audience> nonFreeAudienceList = new ArrayList<>();
            for (AudienceSchedule audienceSchedule: audienceScheduleList) {
                nonFreeAudienceList.add(audienceSchedule.getAudience());
            }
            audienceList.removeAll(nonFreeAudienceList);
            return audienceList;
         }
    }
}
