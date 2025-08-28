package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Search {
    WebDriver driver;
    By searchBox = By.id("searchBox");
    By searchButton = By.id("searchBtn");

    public Search(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchTerm(String term) {
        driver.findElement(searchBox).sendKeys(term);
    }

    public void clickSearch() {
        driver.findElement(searchButton).click();
    }
}
