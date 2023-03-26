package labs.webtech.DAO;

import labs.webtech.table.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class LecturerDAOTest {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private LecturerDAO lecturerDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testIsAttached() {
        Lecturer lecturer = lecturerDAO.getById(1L);
        assertNotNull(lecturer);
        Course course = courseDAO.getById(1L);
        assertNotNull(course);
        assertTrue(lecturerDAO.isAttached(lecturer, course));
        course = courseDAO.getById(2L);
        assertNotNull(course);
        assertFalse(lecturerDAO.isAttached(lecturer, course));
    }

    @Test
    void testGetCourseList() {
        Lecturer lecturer = lecturerDAO.getById(1L);
        assertNotNull(lecturer);
        List<Course> courseList = lecturerDAO.getCourseList(lecturer);
        assertNotNull(courseList);
        assertEquals(courseList.size(), 1);
        lecturer = lecturerDAO.getById(2L);
        assertNotNull(lecturer);
        courseList = lecturerDAO.getCourseList(lecturer);
        assertNotNull(courseList);
        assertEquals(courseList.size(), 2);
    }

    @Test
    void testGetLecturerList() {
        Course course = courseDAO.getById(1L);
        assertNotNull(course);
        List<Lecturer> lecturerList = courseDAO.getLecturerList(course);
        assertNotNull(lecturerList);
        assertEquals(lecturerList.size(), 2);
        course = courseDAO.getById(2L);
        assertNotNull(course);
        lecturerList = courseDAO.getLecturerList(course);
        assertNotNull(lecturerList);
        assertEquals(lecturerList.size(), 1);
    }

    @Test
    void testIsFree() {
        Lecturer lecturer = lecturerDAO.getById(1L);
        assertNotNull(lecturer);
        assertFalse(lecturerDAO.isFree(lecturer, 0));
        assertTrue(lecturerDAO.isFree(lecturer, 1));
    }

    @Test
    void testGetFreeTime() {
        Lecturer lecturer = lecturerDAO.getById(1L);
        assertNotNull(lecturer);
        List<Integer> freeTime = lecturerDAO.getFreeTime(lecturer);
        assertEquals(freeTime.size(), 29);
        lecturer = lecturerDAO.getById(2L);
        assertNotNull(lecturer);
        freeTime = lecturerDAO.getFreeTime(lecturer);
        assertEquals(freeTime.size(), 30);
    }

    @BeforeEach
    void beforeEach() {
        Course linal = new Course("Линейная алгебра", Course.Coverage.STREAM, 2, 1);
        Course matan = new Course("Комплексный анализ", Course.Coverage.STREAM, 1, 2);
        courseDAO.save(linal);
        courseDAO.save(matan);
        Lecturer linaler = new Lecturer("Линейный", "Леонид", "Леонтьевич");
        Lecturer mataner = new Lecturer("Матанов", "Михаил", "Маркович");
        lecturerDAO.save(linaler);
        lecturerDAO.save(mataner);
        lecturerDAO.attachLecturerCourse(linaler, linal);
        lecturerDAO.attachLecturerCourse(mataner, matan);
        lecturerDAO.attachLecturerCourse(mataner, linal);
        // Create some exercise
        Course course = new Course("", Course.Coverage.STREAM, 1, 1);
        Exercise exercise = new Exercise(course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(course);
            session.saveOrUpdate(exercise);
            session.getTransaction().commit();
        }
        lecturerDAO.bindToExercise(linaler, exercise, 0);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Course\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Exercise\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Lecturer\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"LecturerSchedule\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Course_course_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Exercise_exercise_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Lecturer_lecturer_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
