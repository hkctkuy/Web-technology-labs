package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ExerciseWebTest {

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
    void testExercisesPage() {
        String link = "http://localhost:8080/exercises";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Расписание", driver.getTitle());

        WebElement button = driver.findElement(By.id("courseLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Линейная алгебра", driver.getTitle());

        driver.get(link);
        button = driver.findElement(By.id("lecturerLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Матанов Михаил Маркович", driver.getTitle());

        driver.get(link);
        button = driver.findElement(By.id("audienceLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Аудитория П1", driver.getTitle());

        driver.quit();
    }
}
