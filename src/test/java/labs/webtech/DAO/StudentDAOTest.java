package labs.webtech.DAO;

import labs.webtech.table.Course;
import labs.webtech.table.Group;
import labs.webtech.table.Student;

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
public class StudentDAOTest {

    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testGetByGroup() {
        Group group = groupDAO.getById(1L);
        List<Student> studentList = studentDAO.getByGroup(group);
        assertEquals(studentList.size(), 3);
    }

    @Test
    void testGetByGroupList() {
        List<Group> groupList = new ArrayList<>(groupDAO.getAll());
        assertEquals(groupList.size(), 3);
        List<Student> studentList = studentDAO.getByGroupList(groupList);
        assertEquals(studentList.size(), 6);
    }

    @Test
    void testGetByStream() {
        List<Student> studentList = studentDAO.getByStream(1);
        assertEquals(studentList.size(), 5);
    }

    @Test
    void testGetByYear() {
        List<Student> studentList = studentDAO.getByYear(1);
        assertEquals(studentList.size(), 4);
    }

    @Test
    void testGetByStreamAndYear() {
        List<Student> studentList = studentDAO.getByStreamAndYear(1, 1);
        assertEquals(studentList.size(), 3);
        studentList = studentDAO.getByStreamAndYear(2, 2);
        assertNull(studentList);
    }

    @Test
    void testGetByCourse() {
        // linal
        Course course = courseDAO.getById(1L);
        assertNotNull(course);
        List<Student> studentList = studentDAO.getByCourse(course);
        assertNotNull(studentList);
        assertEquals(studentList.size(), 4);
        // comlan
        course = courseDAO.getById(2L);
        assertNotNull(course);
        studentList = studentDAO.getByCourse(course);
        assertNull(studentList);
        // compil
        course = courseDAO.getById(3L);
        assertNotNull(course);
        studentList = studentDAO.getByCourse(course);
        assertNotNull(studentList);
        assertEquals(studentList.size(), 1);
    }

    @BeforeEach
    void beforeEach() {
        // Courses
        Course linal = new Course("Линейная алгебра", Course.Coverage.STREAM, 2, 1);
        Course complan = new Course("Комплексный анализ", Course.Coverage.STREAM, 1, 2);
        Course compil = new Course("Комиляторные технологии", Course.Coverage.SPEC, 1, 0);
        courseDAO.save(linal);
        courseDAO.save(complan);
        courseDAO.save(compil);
        // 1 stream, 1 year
        Group group = new Group(101, 1, 1);
        groupDAO.save(group);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Первов", "Петр", "Петрович", group));
        studentList.add(new Student("Второв", "Василий", "Васильевич", group));
        studentList.add(new Student("Трифонов", "Трифон", "Трифонович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        // 2 stream, 1 year
        group = new Group(102, 2, 1);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student("Четвертак", "Чибис", "Чибисович", group));
        studentDAO.saveCollection(studentList);
        groupDAO.attachGroupCourse(group, linal);
        // 1 stream, 2 year
        group = new Group(201, 1, 2);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student("Аааев", "Ааай", "Аааевич", group));
        studentDAO.saveCollection(studentList);
        // Spec course
        Student student = new Student("Спецов", "Степан", "Степанович", group);
        studentDAO.save(student);
        studentDAO.attachStudentSpecCourse(student, compil);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Course\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Group\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Student\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Course_course_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Group_group_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Student_student_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
