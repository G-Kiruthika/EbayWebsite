package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
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
    //private By productLinks = By.xpath("//ul[@class='srp-results srp-grid clearfix']//li[1]//a"); // Product results list
    private By productLinks = By.cssSelector("ul.srp-results li.s-item a.s-item__link");
    
    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).clear();
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();
    }

    public void clickFirstProduct() {
        // wait for at least one product to be clickable
        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("ul.srp-results li.s-item a.s-item__link")
        ));

        // retry click up to 3 times in case of stale element
        for (int i = 0; i < 3; i++) {
            try {
                firstProduct.click();
                System.out.println("Product Clicked");
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element, retrying... " + (i+1));
                firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("ul.srp-results li.s-item a.s-item__link")
                ));
            } catch (Exception e) {
                System.out.println("Normal click failed, trying JS click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstProduct);
                System.out.println("Product Clicked with JS");
                return;
            }
        }
    }

}
