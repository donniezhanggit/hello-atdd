package hello;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GreetingPage {

    private WebDriver driver;

    private URL base;

    public GreetingPage(WebDriver driver, URL base) {
        this.driver = driver;
        this.base = base;
    }

    public void load() throws Exception {
        driver.get(base.toString());
    }

    public void askGreeting() throws Exception {
        final WebElement greetingButton = driver.findElement(By.id("ask-greeting"));
        greetingButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("greeting-content")));
    }

    public String getGreetingMessage() throws Exception {
        return driver.findElement(By.id("greeting-content")).getText();
    }
}
