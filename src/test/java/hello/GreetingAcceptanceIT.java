package hello;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingAcceptanceIT {

    private static WebDriver driver;

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @BeforeClass
    public static void initWebDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        driver = new ChromeDriver(chromeOptions);
    }

    @AfterClass
    public static void closeWebDriver() {
        driver.quit();
    }

    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/");
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                String name = description.getTestClass().getSimpleName() + "_" + description.getMethodName();
                FileUtils.copyFile(source, new File("./" + name + ".png"));
            } catch (Exception exception) {
                System.err.println("Unable to take screenshot" + exception.getMessage());
            }
            super.failed(e, description);
        }
    };

    @Test
    public void showGreetingTest() throws Exception {
        GreetingPage page = new GreetingPage(driver, baseUrl);

        page.load();
        page.askGreeting();

        assertThat(page.getGreetingMessage(), is("Hello, world"));
    }
}
