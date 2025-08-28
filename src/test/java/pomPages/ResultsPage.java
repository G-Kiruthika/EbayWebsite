package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class ResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    By searchBox = By.id("gh-ac");
    By searchButton = By.cssSelector("div.gh-search-button__wrap button");
    By productLinks = By.cssSelector("ul.srp-results li.s-item a.s-item__link");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String productName) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.clear();
        box.sendKeys(productName);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        btn.click();
    }

    public void clickFirstProduct() {
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productLinks));
        if (products.size() > 0) {
            WebElement firstProduct = products.get(0);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Retry once
                products = driver.findElements(productLinks);
                if (products.size() > 0) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(products.get(0))).click();
                    } catch (Exception ex) {
                        // Fallback to JS click
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", products.get(0));
                    }
                }
            }
        }
    }

    public int getResultsCount() {
        List<WebElement> products = driver.findElements(productLinks);
        return products.size();
    }
}
