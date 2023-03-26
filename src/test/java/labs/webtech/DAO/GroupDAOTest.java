package labs.webtech.DAO;

import labs.webtech.table.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class GroupDAOTest {

    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testGroupSize() {
        Group group = groupDAO.getById(1L);
        assertNotNull(group);
        assertEquals(groupDAO.groupSize(group), 3);
        // Empty group
        group = new Group(111, 1, 1);
        groupDAO.save(group);
        assertEquals(groupDAO.groupSize(group), 0);
    }

    @Test
    void testSizeByList() {
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertNotNull(groupList);
        int size = groupDAO.sizeByList(groupList);
        assertEquals(size, 5);
    }

    @Test
    void testIsFree() {
        Group group = groupDAO.getById(3L);
        assertNotNull(group);
        assertFalse(groupDAO.isFree(group, 0));
        assertFalse(groupDAO.isFree(group, 1));
        assertTrue(groupDAO.isFree(group, 2));
    }

    @Test
    void testIsFreeByList() {
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertNotNull(groupList);
        assertFalse(groupDAO.isFreeByList(groupList, 0));
        assertFalse(groupDAO.isFreeByList(groupList, 1));
        assertTrue(groupDAO.isFreeByList(groupList, 2));
    }

    @Test
    void testGetFreeTime() {
        Group group = groupDAO.getById(1L);
        assertNotNull(group);
        List<Integer> freeTime = groupDAO.getFreeTime(group);
        assertEquals(freeTime.size(), 29);

        group = groupDAO.getById(3L);
        assertNotNull(group);
        freeTime = groupDAO.getFreeTime(group);
        assertEquals(freeTime.size(), 28);
    }

    @Test
    void testGetFreeTimeByList() {
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertNotNull(groupList);
        List<Integer> freeTime = groupDAO.getFreeTimeByList(groupList);
        assertEquals(freeTime.size(), 28);
    }

    @BeforeEach
    void beforeEach() {
        // Courses
        Course linal = new Course("Линейная алгебра", Course.Coverage.STREAM, 2, 1);
        Course complan = new Course("Комплексный анализ", Course.Coverage.STREAM, 1, 2);
        courseDAO.save(linal);
        courseDAO.save(complan);
        List<Group> groupList = new ArrayList<>();
        // 1 stream, 1 year
        Group group = new Group(101, 1, 1);
        groupList.add(group);
        groupDAO.save(group);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Первов", "Петр", "Петрович", group));
        studentList.add(new Student("Второв", "Василий", "Васильевич", group));
        studentList.add(new Student("Трифонов", "Трифон", "Трифонович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        // 2 stream, 1 year
        group = new Group(102, 2, 1);
        groupList.add(group);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student("Четвертак", "Чибис", "Чибисович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        // 1 stream, 2 year
        group = new Group(201, 1, 2);
        groupList.add(group);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student("Аааев", "Ааай", "Аааевич", group));
        studentDAO.saveCollection(studentList);
        // Create some exercise
        Exercise exercise = new Exercise(linal);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(exercise);
            session.getTransaction().commit();
        }
        groupDAO.bindToExercise(group, exercise, 0);
        groupDAO.bindToExerciseByList(groupList, exercise, 1);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Course\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Exercise\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Group\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"GroupSchedule\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Student\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Course_course_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Exercise_exercise_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Group_group_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Student_student_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
