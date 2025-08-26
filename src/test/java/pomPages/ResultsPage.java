package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class ResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 5;

    @FindBy(id = "gh-ac")
    private WebElement searchBox;

    @FindBy(css = "div.gh-search-button__wrap button")
    private WebElement searchButton;

    @FindBy(css = "ul.srp-results li.s-item a.s-item__link")
    private List<WebElement> productLinks;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    // Clicks the first product link, with retry for StaleElementReferenceException
    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        WebElement firstProduct = productLinks.get(0);
        int attempts = 0;
        while (attempts < 3) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(firstProduct));
                firstProduct.click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                firstProduct = productLinks.get(0);
            } catch (Exception e) {
                // fallback to JS click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstProduct);
                return;
            }
        }
    }

    public int getResultsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        return productLinks.size();
    }
}
