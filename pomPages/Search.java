package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for eBay Search Functionality
 * Covers: Search box, suggestions, search execution, empty search handling
 * Traceable to: Search test cases, eBay functional KB
 */
public class Search {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "gh-ac")
    WebElement searchBox;

    @FindBy(css = "li.srp-suggestion")
    List<WebElement> searchSuggestions;

    @FindBy(id = "gh-btn")
    WebElement searchButton;

    @FindBy(css = ".srp-controls__count-heading, .srp-results .s-item")
    List<WebElement> searchResults;

    @FindBy(css = ".srp-controls__control--legacy .srp-controls__control__count")
    WebElement resultCount;

    @FindBy(css = ".srp-controls__control--legacy .srp-controls__control__count, .no-results-message")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String term) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(term);
    }

    public void selectSuggestion(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchSuggestions));
        if (index < searchSuggestions.size()) {
            searchSuggestions.get(index).click();
        }
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public boolean isSearchResultDisplayed() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
        return searchResults.size() > 0;
    }

    public String getEmptySearchMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
        return emptySearchMessage.getText();
    }
}
