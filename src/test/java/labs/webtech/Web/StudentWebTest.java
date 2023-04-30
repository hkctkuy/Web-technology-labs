package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class StudentWebTest {

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
    void testGroupsPage() {
        String link = "http://localhost:8080/groups";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Учебные группы", driver.getTitle());

        WebElement button = driver.findElement(By.id("streamLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("1 поток", driver.getTitle());

        driver.get(link);
        button = driver.findElement(By.id("groupLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("101 группа", driver.getTitle());

        driver.quit();
    }

    @Test
    void testGroupPage() {
        String link = "http://localhost:8080/group?id=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("101 группа", driver.getTitle());

        WebElement button = driver.findElement(By.id("streamLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("1 поток", driver.getTitle());

        driver.get(link);
        WebElement table = driver.findElement(By.id("studentLink"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        assertEquals(rows.size(), 2);

        assertEquals(rows
                        .get(0).findElements(By.tagName("td"))
                        .get(0).findElements(By.tagName("a"))
                        .get(0).findElement(By.tagName("span"))
                        .getText(),
                "Первов Петр Петрович");
        assertEquals(rows
                        .get(1).findElements(By.tagName("td"))
                        .get(0).findElements(By.tagName("a"))
                        .get(0).findElement(By.tagName("span"))
                        .getText(),
                "Перваков Павел Павлович");


        driver.quit();
    }

    @Test
    void testStreamPage() {
        String link = "http://localhost:8080/stream?stream=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("1 поток", driver.getTitle());

        WebElement table = driver.findElement(By.id("year"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        assertEquals(rows.size(), 3);

        WebElement button = driver.findElement(By.id("studentLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("Первов Петр Петрович", driver.getTitle());

        driver.get(link);
        button = driver.findElement(By.id("groupLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("101 группа", driver.getTitle());

        driver.quit();
    }

    @Test
    void testStudentPage() {
        String link = "http://localhost:8080/student?id=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Первов Петр Петрович", driver.getTitle());

        WebElement button = driver.findElement(By.id("groupLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("101 группа", driver.getTitle());

        button = driver.findElement(By.id("streamLink"));
        button.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        assertEquals("1 поток", driver.getTitle());

        driver.quit();
    }
}
