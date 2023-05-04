package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CourseWebTest {

    private ChromeDriver ChromeDriverRightVersion() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("start-maximized"); // open Browser in maximized mode
        //options.addArguments("disable-infobars"); // disabling infobars
        //options.addArguments("--disable-extensions"); // disabling extensions
        //options.addArguments("--disable-gpu"); // applicable to windows os only
        //options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    @Test
    void testCoursesPage() {
        String link = "http://localhost:8080/courses";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Учебный план", driver.getTitle());

        WebElement button = driver.findElement(By.id("courseLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Линейная алгебра", driver.getTitle());

        driver.get(link);
        button = driver.findElement(By.id("specCourseLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Комиляторные технологии", driver.getTitle());

        driver.quit();
    }

    @Test
    void testCoursePage() {
        String link = "http://localhost:8080/course?id=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Линейная алгебра", driver.getTitle());

        List<WebElement> lecturers = driver.findElement(By.id("lecturerLink"))
                .findElements(By.tagName("li"));
        assertEquals(lecturers.size(), 2);

        assertEquals(lecturers
                        .get(0).findElements(By.tagName("a"))
                        .get(0).findElement(By.tagName("span"))
                        .getText(),
                "Матанов Михаил Маркович");
        assertEquals(lecturers
                        .get(1).findElements(By.tagName("a"))
                        .get(0).findElement(By.tagName("span"))
                        .getText(),
                "Линейный Леонид Леонтьевич");

        driver.quit();
    }
}
