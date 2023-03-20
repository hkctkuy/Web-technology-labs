package labs.webtech.DAO;

import labs.webtech.table.Audience;

import java.util.List;

public interface AudienceDAO extends TableDAO<Audience, Long> {

    List<Audience> getByTime(Integer time);
}
