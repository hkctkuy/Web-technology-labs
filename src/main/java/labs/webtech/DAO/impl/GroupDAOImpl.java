package labs.webtech.DAO.impl;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.table.*;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    public List<Group> getByYear(Integer year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session
                    .createQuery("SELECT g FROM Group g WHERE g.year = :year", Group.class)
                    .setParameter("year", year);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Group> getByStreamAndYear(Integer stream, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            Query<Group> query = session
                    .createQuery("SELECT g FROM Group g WHERE g.stream = :stream and g.year = :year", Group.class)
                    .setParameter("stream", stream)
                    .setParameter("year", year);
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Group> getByCourse(@NonNull Course course) {
        if (course.getCoverage() == Course.Coverage.SPEC) {
            return null;
        }
        try (Session session = sessionFactory.openSession()) {
            Query<CourseDist> query = session
                    .createQuery("SELECT cd FROM CourseDist cd WHERE cd.course = :course", CourseDist.class)
                    .setParameter("course", course);
            List<CourseDist> courseDistList = query.getResultList();
            if (courseDistList.size() == 0) {
                return null;
            }
            List<Group> groupList = new ArrayList<>();
            for (CourseDist courseDist: courseDistList) {
                groupList.add(courseDist.getGroup());
            }
            return groupList;
        }
    }


    @SneakyThrows
    @Override
    public void attachGroupCourse(Group group, @NonNull Course course) {
        if (course.getCoverage() == Course.Coverage.SPEC) {
            throw new Exception("It is not possible to register an entire group for a special course");
        }
        if (course.getYear() > group.getYear()) {
            throw new Exception("Pretty young group for that course");
        }
                CourseDist courseDist = new CourseDist(group, course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(courseDist);
            session.getTransaction().commit();
        }
    }

    @Override
    public int groupSize(Group group) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session
                    .createQuery("SELECT s FROM Student s WHERE s.group = :group", Student.class)
                    .setParameter("group", group);
            List<Student> studentList = query.getResultList();
            return studentList == null ? 0 : studentList.size();
        }
    }

    @Override
    public int sizeByList(List<Group> groupList) {
        int size = 0;
        if (groupList == null) {
            return size;
        }
        for (Group group: groupList) {
            size += groupSize(group);
        }
        return size;
    }

    @Override
    public boolean isFree(Group group, Integer time) {
        try (Session session = sessionFactory.openSession()) {
            Query<GroupSchedule> query = session
                    .createQuery("SELECT gs FROM GroupSchedule gs WHERE gs.time = :time AND gs.group = :group", GroupSchedule.class)
                    .setParameter("group", group)
                    .setParameter("time", time);
            return query.getResultList().size() == 0;
        }
    }

    @Override
    public boolean isFreeByList(@NonNull List<Group> groupList, Integer time) {
        for (Group group: groupList) {
            if (!isFree(group, time)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Integer> getFreeTime(Group group) {
        List<Integer> freeTime = new ArrayList<>(IntStream.range(0, 30).boxed().toList());
        try (Session session = sessionFactory.openSession()) {
            Query<Integer> query = session
                    .createQuery("SELECT gs.time FROM GroupSchedule gs WHERE gs.group = :group", Integer.class)
                    .setParameter("group", group);
            List<Integer> notFreeTime = query.getResultList();
            freeTime.removeAll(notFreeTime);
            return freeTime;
        }
    }

    @Override
    public List<Integer> getFreeTimeByList(@NonNull List<Group> groupList) {
        List<Integer> freeTime = new ArrayList<>(IntStream.range(0, 30).boxed().toList());
        for (Group group: groupList) {
            freeTime.retainAll(getFreeTime(group));
        }
        return freeTime;
    }

    @Override
    public void bindToExercise(Group group, Exercise exercise, Integer time) {
        try (Session session = sessionFactory.openSession()) {
            GroupSchedule groupSchedule = new GroupSchedule(group, exercise, time);
            session.beginTransaction();
            session.saveOrUpdate(groupSchedule);
            session.getTransaction().commit();
        }
    }

    @Override
    public void bindToExerciseByList(@NonNull List<Group> groupList, Exercise exercise, Integer time) {
        for (Group group: groupList) {
            bindToExercise(group, exercise, time);
        }
    }
}
