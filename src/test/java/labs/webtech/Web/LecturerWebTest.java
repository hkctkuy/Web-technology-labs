package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class LecturerWebTest {

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
    void testLecturersPage() {
        String link = "http://localhost:8080/lecturers";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Преподователи", driver.getTitle());

        WebElement button = driver.findElement(By.id("lecturerLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Матанов Михаил Маркович", driver.getTitle());

        driver.quit();
    }

    @Test
    void testLecturerPage() {
        String link = "http://localhost:8080/lecturer?id=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Матанов Михаил Маркович", driver.getTitle());

        driver.get(link);
        List<WebElement> lecturers = driver.findElement(By.id("courseLink"))
                .findElements(By.tagName("li"));
        assertEquals(lecturers.size(), 5);

        String[] courses = {
                "Математический анализ I",
                "Математический анализ II",
                "Комплексный анализ",
                "Функциональный анализ",
                "Линейная алгебра"
        };

        for (int i = 0; i < 5; i++) {
            assertEquals(lecturers
                            .get(i).findElements(By.tagName("a"))
                            .get(0).findElement(By.tagName("span"))
                            .getText(),
                    courses[i]);
        }

        driver.quit();
    }
}
