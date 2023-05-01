package labs.webtech.Web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AudienceWebTest {

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
    void testAudiencesPage() {
        String link = "http://localhost:8080/audiences";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Аудитории", driver.getTitle());

        List<WebElement> audiences = driver.findElement(By.id("audienceLink"))
                .findElements(By.tagName("li"));
        assertEquals(audiences.size(), 4);

        String[] audienceList = {
                "526",
                "526-б",
                "666",
                "П1",
        };

        for (int i = 0; i < 4; i++) {
            assertEquals(audiences
                            .get(i).findElements(By.tagName("a"))
                            .get(0).findElement(By.tagName("span"))
                            .getText(),
                    audienceList[i]);
        }

        driver.quit();
    }

    @Test
    void testAudiencePage() {
        String link = "http://localhost:8080/audience?id=1";
        ChromeDriver driver = ChromeDriverRightVersion();
        driver.get(link);
        assertEquals("Аудитория 526", driver.getTitle());

        driver.quit();
    }
}
