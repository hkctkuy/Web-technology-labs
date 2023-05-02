package labs.webtech.DAO;

import labs.webtech.table.*;

import java.util.List;

public interface ExerciseDAO extends TableDAO<Exercise, Long> {

    Exercise getExercise(Group group, Integer time);

    Audience getAudience(Exercise exercise);

    Lecturer getLecturer(Exercise exercise);

    void addExercise(Course course, List<Group> groupList, Lecturer lecturer, Audience audience, Integer time);

    void scheduleExercise(Course course, List<Group> groupList);

    void scheduleCourse(Course course);

    void generateSchedule();
}
