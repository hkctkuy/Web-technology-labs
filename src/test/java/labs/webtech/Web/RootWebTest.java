package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RootWebTest {

    private final String rootTitle = "Главная страница";

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
    void testHeader() {
        String link = "http://localhost:8080/";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024,768));
        driver.get(link);

        WebElement button = driver.findElement(By.id("rootLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals(rootTitle, driver.getTitle());

        button = driver.findElement(By.id("coursesLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Учебный план", driver.getTitle());

        button = driver.findElement(By.id("lecturersLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Преподователи", driver.getTitle());

        button = driver.findElement(By.id("groupsLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Учебные группы", driver.getTitle());

        button = driver.findElement(By.id("audiencesLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Аудитории", driver.getTitle());

        driver.quit();
    }

    @Test
    void testMainPage() {
        String link = "http://localhost:8080/";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals(rootTitle, driver.getTitle());
        driver.quit();
    }
}
