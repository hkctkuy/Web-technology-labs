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
public class ExerciseDAOTest {

    @Autowired
    private AudienceDAO audienceDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private ExerciseDAO exerciseDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private LecturerDAO lecturerDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testAddExercise() {
        // Check audiences
        Audience audience = audienceDAO.getById(1L);
        assertNotNull(audience);
        assertFalse(audienceDAO.isFree(audience, 0));
        assertFalse(audienceDAO.isFree(audience, 1));
        assertTrue(audienceDAO.isFree(audience, 2));
        audience = audienceDAO.getById(2L);
        assertNotNull(audience);
        assertTrue(audienceDAO.isFree(audience, 0));
        assertFalse(audienceDAO.isFree(audience, 1));
        assertTrue(audienceDAO.isFree(audience, 2));
        // Check groups
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertNotNull(groupList);
        assertFalse(groupDAO.isFreeByList(groupList, 0));
        assertFalse(groupDAO.isFreeByList(groupList, 1));
        assertTrue(groupDAO.isFreeByList(groupList, 2));
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 28);
        // Check lecturers
        Lecturer lecturer = lecturerDAO.getById(1L);
        assertNotNull(lecturer);
        assertTrue(lecturerDAO.isFree(lecturer, 0));
        assertFalse(lecturerDAO.isFree(lecturer, 1));
        assertTrue(lecturerDAO.isFree(lecturer, 2));
        lecturer = lecturerDAO.getById(2L);
        assertNotNull(lecturer);
        assertFalse(lecturerDAO.isFree(lecturer, 0));
        assertFalse(lecturerDAO.isFree(lecturer, 1));
        assertTrue(lecturerDAO.isFree(lecturer, 2));
    }

    @Test
    void testScheduleExercise() {
        Course course = new Course("", Course.Coverage.STREAM, 2, 1);
        courseDAO.save(course);
        Lecturer lecturer = new Lecturer("", "", "");
        lecturerDAO.save(lecturer);
        lecturerDAO.attachLecturerCourse(lecturer, course);
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertEquals(lecturerDAO.getFreeTime(lecturer).size(), 30);
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 28);
        exerciseDAO.scheduleExercise(course, groupList);
        assertEquals(lecturerDAO.getFreeTime(lecturer).size(), 28);
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 26);
    }

    @Test
    void testScheduleCourse() {
        // Add stream course
        Course course = new Course("", Course.Coverage.STREAM, 2, 1);
        courseDAO.save(course);
        // Add lecturer
        Lecturer lecturer = new Lecturer("", "", "");
        lecturerDAO.save(lecturer);
        lecturerDAO.attachLecturerCourse(lecturer, course);
        // Add group
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        Group group = groupDAO.getById(1L);
        groupDAO.attachGroupCourse(group, course);

        assertEquals(lecturerDAO.getFreeTime(lecturer).size(), 30);
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 28);
        assertEquals(groupDAO.getFreeTime(group).size(), 28);
        exerciseDAO.scheduleCourse(course);
        assertEquals(lecturerDAO.getFreeTime(lecturer).size(), 28);
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 26);
        group = groupDAO.getById(2L);
        assertEquals(groupDAO.getFreeTime(group).size(), 28);

        // Add group course
        course = new Course("_", Course.Coverage.GROUP, 2, 1);
        courseDAO.save(course);
        lecturerDAO.attachLecturerCourse(lecturer, course);
        groupDAO.attachGroupCourse(group, course);
        exerciseDAO.scheduleCourse(course);
        assertEquals(lecturerDAO.getFreeTime(lecturer).size(), 26);
        assertEquals(groupDAO.getFreeTimeByList(groupList).size(), 24);
        assertEquals(groupDAO.getFreeTime(group).size(), 26);
    }

    @BeforeEach
    void beforeEach() {
        // Add courses
        Course linal = new Course("Линейная алгебра", Course.Coverage.STREAM, 2, 1);
        Course matan = new Course("Комплексный анализ", Course.Coverage.STREAM, 1, 2);
        courseDAO.save(linal);
        courseDAO.save(matan);
        // Add lecturers
        Lecturer linaler = new Lecturer("Линейный", "Леонид", "Леонтьевич");
        Lecturer mataner = new Lecturer("Матанов", "Михаил", "Маркович");
        lecturerDAO.save(linaler);
        lecturerDAO.save(mataner);
        lecturerDAO.attachLecturerCourse(linaler, linal);
        lecturerDAO.attachLecturerCourse(mataner, matan);
        lecturerDAO.attachLecturerCourse(mataner, linal);
        // Add groups
        List<Group> groupList = new ArrayList<>();
        Group group = new Group(101, 1, 1);
        groupList.add(group);
        groupDAO.save(group);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Первов", "Петр", "Петрович", group));
        studentList.add(new Student("Второв", "Василий", "Васильевич", group));
        studentList.add(new Student("Трифонов", "Трифон", "Трифонович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        group = new Group(102, 2, 1);
        groupList.add(group);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student("Четвертак", "Чибис", "Чибисович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        // Add audiences
        Audience audience1 = new Audience("111", 36);
        audienceDAO.save(audience1);
        Audience audience2 = new Audience("П1", 200);
        audienceDAO.save(audience2);
        // Add exercise
        exerciseDAO.addExercise(matan, groupList, mataner, audience1, 0);
        groupList.remove(group);
        exerciseDAO.addExercise(linal, groupList, linaler, audience1, 1);
        groupList = new ArrayList<>();
        groupList.add(group);
        exerciseDAO.addExercise(linal, groupList, mataner, audience2, 1);
    }

    @BeforeAll
    @AfterEach
    void beforeAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Audience\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"AudienceSchedule\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Course\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Exercise\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Group\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"GroupSchedule\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Lecturer\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"LecturerDist\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"LecturerSchedule\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Student\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Audience_audience_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Course_course_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Exercise_exercise_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Group_group_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Lecturer_lecturer_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Student_student_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
