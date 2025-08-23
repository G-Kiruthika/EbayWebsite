package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    private By searchBox = By.id("gh-ac"); // eBay search box
    private By searchButton = By.cssSelector("div.gh-search-button__wrap button"); // Search button
    private By productLinks = By.cssSelector("ul.srp-results li.s-item a.s-item__link"); // Product results list

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).clear();
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();
    }

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productLinks));
        driver.findElement(productLinks).click();
         // click first product
        System.out.println("Product Clicked");
    }
}
