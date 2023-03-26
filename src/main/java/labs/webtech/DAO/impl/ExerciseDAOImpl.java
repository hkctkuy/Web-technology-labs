package labs.webtech.DAO.impl;

import labs.webtech.DAO.AudienceDAO;
import labs.webtech.DAO.CourseDAO;
import labs.webtech.DAO.ExerciseDAO;
import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.LecturerDAO;
import labs.webtech.table.Audience;
import labs.webtech.table.Course;
import labs.webtech.table.Exercise;
import labs.webtech.table.Group;
import labs.webtech.table.Lecturer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExerciseDAOImpl extends TableDAOImpl<Exercise, Long> implements ExerciseDAO {

    public ExerciseDAOImpl() {
        super(Exercise.class);
    }

    @Autowired
    private AudienceDAO audienceDAO = new AudienceDAOImpl();
    @Autowired
    private CourseDAO courseDAO = new CourseDAOImpl();
    @Autowired
    private GroupDAO groupDAO = new GroupDAOImpl();
    @Autowired
    private LecturerDAO lecturerDAO = new LecturerDAOImpl();

    @SneakyThrows
    @Override
    public void addExercise(Course course, List<Group> groupList, Lecturer lecturer, Audience audience, Integer time) {
        if (course.getCoverage() == Course.Coverage.SPEC) {
            throw new Exception("Use another method for SPEC");
        }
        if (!audienceDAO.isFree(audience, time)) {
            throw new Exception("Audience is not free in that time");
        }
        if (!groupDAO.isFreeByList(groupList, time)) {
            throw new Exception("Groups are not free in that time");
        }
        if (groupDAO.sizeByList(groupList) > audience.getCapacity()) {
            throw new Exception("Audience is too small");
        }
        if (!lecturerDAO.isFree(lecturer, time)) {
            throw new Exception("Lecturer is not free in that time");
        }
        if (!lecturerDAO.isAttached(lecturer, course)) {
            throw new Exception("Invalid lecturer for that course");
        }
        if (time < 0 || time >= 30) {
            throw new Exception("Invalid time");
        }
        Exercise exercise = new Exercise(course);
        save(exercise);
        audienceDAO.bindToExercise(audience, exercise, time);
        groupDAO.bindToExerciseByList(groupList, exercise, time);
        lecturerDAO.bindToExercise(lecturer, exercise, time);
    }
}
