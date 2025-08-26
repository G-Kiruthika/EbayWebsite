package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Search {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(xpath = "//input[@placeholder='Search for anything']")
    private WebElement searchBox;

    @FindBy(xpath = "(//span[@class='suggestion-text']//span)[3]")
    private WebElement suggestionOption;

    @FindBy(xpath = "//div[@id='mainContent']")
    private WebElement pageContent;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    // Types keyword, waits for suggestion, returns suggestion text if present
    public String performSearch(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        try {
            wait.until(ExpectedConditions.visibilityOf(suggestionOption));
            return suggestionOption.getText();
        } catch (Exception e) {
            return null;
        }
    }

    // Clicks the suggestion option
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(suggestionOption));
        suggestionOption.click();
    }

    // Types keyword and presses ENTER, waits for results
    public void searchProduct(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(pageContent));
    }

    public String searchPageTitleCheck() {
        return driver.getTitle();
    }
}
