package labs.webtech.DAO.impl;

import labs.webtech.DAO.AudienceDAO;
import labs.webtech.table.Audience;
import labs.webtech.table.Audience_dist;

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
            Query<Audience_dist> query = session
                    .createQuery("SELECT ad FROM Audience_dist ad WHERE ad.time = :time", Audience_dist.class)
                    .setParameter("time", time);
            List<Audience_dist> Audience_distList = query.getResultList().size() == 0 ? null : query.getResultList();
            List<Audience> AudienceList = new ArrayList<>(getAll());
            if (Audience_distList == null) {
                return AudienceList;
            }
            List<Audience> nonFreeAudienceList = new ArrayList<>();
            for (Audience_dist audience_dist: Audience_distList) {
                nonFreeAudienceList.add(audience_dist.getAudience());
            }
            AudienceList.removeAll(nonFreeAudienceList);
            return AudienceList;
         }
    }
}
