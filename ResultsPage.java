package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResultsPage {
    WebDriver driver;
    By firstResult = By.cssSelector(".result-item:first-child");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstResultText() {
        return driver.findElement(firstResult).getText();
    }
}
