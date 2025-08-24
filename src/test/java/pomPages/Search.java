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

    @FindBy(css = "li.srp-suggestion")
    List<WebElement> suggestionList;

    @FindBy(id = "gh-btn")
    WebElement searchButton;

    @FindBy(css = ".srp-controls__count-heading")
    WebElement resultsHeader;

    @FindBy(css = ".srp-controls__control--legacy .srp-controls__count-heading")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Verify search box is visible
    public boolean isSearchBoxVisible() {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        return searchBox.isDisplayed();
    }

    // Enter search keyword
    public void enterSearchKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    // Wait for and select a suggestion
    public void selectSuggestion(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        if (suggestionList.size() > index) {
            suggestionList.get(index).click();
        }
    }

    // Click search button
    public void clickSearch() {
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
