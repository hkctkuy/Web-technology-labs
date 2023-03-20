package labs.webtech.DAO;

import labs.webtech.table.Audience;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(audienceList.size(), 3);
    }

    @BeforeEach
    void beforeEach() {
        // 1 stream, 1 year;
        List<Audience> audienceList = new ArrayList<>();
        audienceList.add(new Audience(1L, "526", 36));
        audienceList.add(new Audience(2L, "526-б", 36));
        audienceList.add(new Audience(3L, "П1", 200));
        audienceDAO.saveCollection(audienceList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE \"Audience\" RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE \"Audience_audience_id_seq\" RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
