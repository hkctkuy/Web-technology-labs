package labs.webtech.DAO;

import labs.webtech.table.*;

import java.util.List;

public interface ExerciseDAO extends TableDAO<Exercise, Long> {

    void addExercise(Course course, List<Group> groupList, Lecturer lecturer, Audience audience, Integer time);
}
