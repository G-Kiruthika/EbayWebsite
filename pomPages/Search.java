package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class Search {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "gh-ac")
    WebElement searchBox;

    @FindBy(id = "gh-btn")
    WebElement searchButton;

    @FindBy(css = "ul[role='listbox'] li")
    List<WebElement> searchSuggestions;

    @FindBy(css = "div.srp-controls__control")
    WebElement searchResultsContainer;

    @FindBy(css = "div.srp-controls__control--legacy")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

    // Wait for search box to be visible
    public void waitForSearchBox() {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
    }

    // Enter search keyword
    public void enterSearchKeyword(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    // Get search suggestions
    public List<WebElement> getSearchSuggestions() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchSuggestions));
        return searchSuggestions;
    }

    // Select a suggestion by index
    public void selectSuggestion(int index) {
        List<WebElement> suggestions = getSearchSuggestions();
        if (index >= 0 && index < suggestions.size()) {
            suggestions.get(index).click();
        }
    }

    // Click search button
    public void clickSearchButton() {
        searchButton.click();
    }

    // Submit search by pressing Enter
    public void submitSearch() {
        searchBox.submit();
    }

    // Wait for search results
    public void waitForSearchResults() {
        wait.until(ExpectedConditions.visibilityOf(searchResultsContainer));
    }

    // Check for empty search message
    public boolean isEmptySearchMessageDisplayed() {
        try {
            return emptySearchMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
