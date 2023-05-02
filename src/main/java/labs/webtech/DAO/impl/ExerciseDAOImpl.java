package labs.webtech.DAO.impl;

import labs.webtech.DAO.AudienceDAO;
import labs.webtech.DAO.CourseDAO;
import labs.webtech.DAO.ExerciseDAO;
import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.LecturerDAO;
import labs.webtech.table.*;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    @Override
    public Exercise getExercise(Group group, Integer time) {
        try (Session session = sessionFactory.openSession()) {
            Query<GroupSchedule> query = session
                    .createQuery("SELECT gs FROM GroupSchedule gs " +
                            "WHERE gs.group = :group AND gs.time = :time",
                            GroupSchedule.class)
                    .setParameter("group", group)
                    .setParameter("time", time);
            List<GroupSchedule> GroupScheduleList = query.getResultList();
            if (GroupScheduleList.size() == 0) {
                return null;
            }
            return GroupScheduleList.get(0).getExercise();
        }
    }

    @Override
    public Audience getAudience(Exercise exercise) {
        try (Session session = sessionFactory.openSession()) {
            Query<AudienceSchedule> query = session
                    .createQuery("SELECT asch FROM AudienceSchedule asch " +
                                    "WHERE asch.exercise = :exercise",
                            AudienceSchedule.class)
                    .setParameter("exercise", exercise);
            List<AudienceSchedule> AudienceScheduleList = query.getResultList();
            if (AudienceScheduleList.size() == 0) {
                return null;
            }
            return AudienceScheduleList.get(0).getAudience();
        }
    }

    @Override
    public Lecturer getLecturer(Exercise exercise) {
        try (Session session = sessionFactory.openSession()) {
            Query<LecturerSchedule> query = session
                    .createQuery("SELECT ls FROM LecturerSchedule ls " +
                                    "WHERE ls.exercise = :exercise",
                            LecturerSchedule.class)
                    .setParameter("exercise", exercise);
            List<LecturerSchedule> LecturerScheduleList = query.getResultList();
            if (LecturerScheduleList.size() == 0) {
                return null;
            }
            return LecturerScheduleList.get(0).getLecturer();
        }
    }

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

    @SneakyThrows
    @Override
    public void scheduleExercise(Course course, List<Group> groupList) {
        int depth = course.getDepth();
        int size = groupDAO.sizeByList(groupList);
        List<Integer> freeTime = groupDAO.getFreeTimeByList(groupList);
        List<Lecturer> lecturerList = courseDAO.getLecturerList(course);
        for (Lecturer lecturer: lecturerList) {
            List<Integer> lecturerFreeTime = lecturerDAO.getFreeTime(lecturer);
            lecturerFreeTime.retainAll(freeTime);
            if (lecturerFreeTime.size() < depth) {
                lecturerList.remove(lecturer);
            }
            // find try to audiences
            Audience[] audienceArray = new Audience[depth];
            Integer[] timeArray = new Integer[depth];
            int count = 0;
            for (Integer time: lecturerFreeTime) {
                if (count >= depth) {
                    break;
                }
                List<Audience> audienceList = audienceDAO.getFreeAudienceList(time, size);
                if (audienceList.size() == 0) {
                    continue;
                }
                audienceArray[count] = audienceList.get(0);
                timeArray[count] = time;
                count++;
            }
            if (count < depth) {
                continue;
            }
            for (int i = 0; i < depth; i++) {
                addExercise(course, groupList, lecturer, audienceArray[i], timeArray[i]);
            }
            return;
        }
        throw new Exception("Can not find lecturer");
    }

    @SneakyThrows
    @Override
    public void scheduleCourse(Course course) {
        List<Group> groupList = groupDAO.getByCourse(course);
        if (course.getCoverage() == Course.Coverage.STREAM) {
            for (int stream = 1; stream <= 3; stream++) {
                List<Group> streamGroupList = groupDAO.getByStreamAndYear(stream, course.getYear());
                if (streamGroupList == null) {
                    continue;
                }
                streamGroupList.retainAll(groupList);
                if (streamGroupList.size() == 0) {
                    continue;
                }
                scheduleExercise(course, streamGroupList);
            }
        } else if (course.getCoverage() == Course.Coverage.GROUP) {
            for (Group group: groupList) {
                List<Group> oneGroupList = new ArrayList<>();
                oneGroupList.add(group);
                scheduleExercise(course, oneGroupList);
            }
        } else {
            throw new Exception("Spec course scheduling is not supported yet");
        }
    }

    @Override
    public void generateSchedule() {
        List<Course> courseList = new ArrayList<>(courseDAO.getAll());
        for (Course course: courseList) {
            scheduleCourse(course);
        }
    }
}
