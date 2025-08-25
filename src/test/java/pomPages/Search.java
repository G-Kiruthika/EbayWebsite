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

    @FindBy(css = ".ui-autocomplete li")
    List<WebElement> suggestionList;

    @FindBy(css = ".srp-results .s-item")
    List<WebElement> productResults;

    @FindBy(css = ".srp-controls__count-heading")
    WebElement resultsCount;

    @FindBy(css = ".srp-error-message")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Enter search keyword
    public void enterSearchKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    // Click search button
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    // Get suggestions for partial keyword
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

    // Get product results
    public List<WebElement> getProductResults() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productResults));
        return productResults;
    }

    // Get results count text
    public String getResultsCountText() {
        wait.until(ExpectedConditions.visibilityOf(resultsCount));
        return resultsCount.getText();
    }

    // Get empty search message
    public String getEmptySearchMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
        return emptySearchMessage.getText();
    }
}
