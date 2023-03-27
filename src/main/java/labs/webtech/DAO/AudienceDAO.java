package labs.webtech.DAO;

import labs.webtech.table.Audience;
import labs.webtech.table.Exercise;

import java.util.List;

public interface AudienceDAO extends TableDAO<Audience, Long> {

    boolean isFree(Audience audience, Integer time);

    List<Audience> getFreeAudienceList(Integer time, Integer capacity);

    void bindToExercise(Audience audience, Exercise exercise, Integer time);
}
