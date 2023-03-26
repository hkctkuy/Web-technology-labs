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
public class AudienceDAOTest {

    @Autowired
    private AudienceDAO audienceDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSize() {
        List<Audience> audienceList = new ArrayList<>(audienceDAO.getAll());
        assertEquals(audienceList.size(), 4);
    }

    @Test
    void testIsFree() {
        Audience audience = audienceDAO.getById(1L);
        assertNotNull(audience);
        assertFalse(audienceDAO.isFree(audience, 0));
        assertTrue(audienceDAO.isFree(audience, 1));
    }

    @Test
    void testGetFree() {
        List<Audience> audienceList = audienceDAO.getFreeAudience(0, 30);
        assertNotNull(audienceList);
        assertEquals(audienceList.size(), 3);
        audienceList = audienceDAO.getFreeAudience(0, 200);
        assertNotNull(audienceList);
        assertEquals(audienceList.size(), 1);
        audienceList = audienceDAO.getFreeAudience(1, 30);
        assertNotNull(audienceList);
        assertEquals(audienceList.size(), 4);
        audienceList = audienceDAO.getFreeAudience(0, 1000);
        assertNull(audienceList);
    }

    @BeforeEach
    void beforeEach() {
        Audience audience = new Audience("111", 36);
        audienceDAO.save(audience);
        List<Audience> audienceList = new ArrayList<>();
        audienceList.add(new Audience("526", 36));
        audienceList.add(new Audience("526-б", 36));
        audienceList.add(new Audience("П1", 200));
        audienceDAO.saveCollection(audienceList);
        // Create some exercise
        Course course = new Course("", Course.Coverage.STREAM, 1, 1);
        Exercise exercise = new Exercise(course);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(course);
            session.saveOrUpdate(exercise);
            session.getTransaction().commit();
        }
        audienceDAO.bindToExercise(audience, exercise, 0);
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
            session.createSQLQuery("ALTER SEQUENCE \"Audience_audience_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Course_course_id_seq\" RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Exercise_exercise_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
