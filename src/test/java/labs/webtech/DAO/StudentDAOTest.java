package labs.webtech.DAO;

import labs.webtech.table.Group;
import labs.webtech.table.Student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class StudentDAOTest {

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
        assertEquals(studentList.size(), 5);
    }

    @Test
    void testGetByStream() {
        List<Student> studentList = studentDAO.getByStream(1);
        assertEquals(studentList.size(), 4);
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

    @BeforeEach
    void beforeEach() {
        // 1 stream, 1 year
        Group group = new Group(1L, 101, 1, 1);
        groupDAO.save(group);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Первов", "Петр", "Петрович", group));
        studentList.add(new Student(2L, "Второв", "Василий", "Васильевич", group));
        studentList.add(new Student(3L, "Трифонов", "Трифон", "Трифонович", group));
        studentDAO.saveCollection(studentList);
        // 2 stream, 1 year
        group = new Group(2L, 102, 2, 1);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student(4L, "Четвертак", "Чибис", "Чибисович", group));
        studentDAO.saveCollection(studentList);
        // 1 stream, 2 year
        group = new Group(3L, 201, 1, 2);
        groupDAO.save(group);
        studentList = new ArrayList<>();
        studentList.add(new Student(5L, "Аааев", "Ааай", "Аааевич", group));
        studentDAO.saveCollection(studentList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Group\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE \"Student\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Group_group_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Student_student_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
