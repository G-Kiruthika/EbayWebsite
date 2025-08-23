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

    @FindBy(css = "ul[role='listbox'] li")
    List<WebElement> suggestionList;

    @FindBy(id = "gh-btn")
    WebElement searchButton;

    @FindBy(css = "div.srp-controls__count-heading")
    WebElement resultsHeader;

    @FindBy(css = "div.srp-controls__control--legacy")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Wait for search box to be visible
    public void waitForSearchBox() {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
    }

    // Enter search keyword
    public void enterSearchKeyword(String keyword) {
        waitForSearchBox();
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    // Get search suggestions
    public List<WebElement> getSuggestions() {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        return suggestionList;
    }

    // Select a suggestion by index
    public void selectSuggestion(int index) {
        List<WebElement> suggestions = getSuggestions();
        if (index < suggestions.size()) {
            suggestions.get(index).click();
        }
    }

    // Click search button
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    // Submit search by pressing Enter
    public void submitSearch() {
        searchBox.submit();
    }

    // Get results header text
    public String getResultsHeader() {
        wait.until(ExpectedConditions.visibilityOf(resultsHeader));
        return resultsHeader.getText();
    }

    // Get empty search message (if any)
    public String getEmptySearchMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
            return emptySearchMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
