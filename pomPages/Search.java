package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

    @FindBy(css = ".srp-controls__count-heading")
    WebElement resultsHeader;

    @FindBy(css = ".srp-error-message")
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

    // Enter search keyword and press Enter
    public void searchForProduct(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
    }

    // Type partial keyword to trigger suggestions
    public void typePartialKeyword(String partial) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(partial);
    }

    // Select suggestion by visible text
    public void selectSuggestion(String suggestionText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        for (WebElement suggestion : suggestionList) {
            if (suggestion.getText().equalsIgnoreCase(suggestionText)) {
                suggestion.click();
                break;
            }
        }
    }

    // Get search result header text
    public String getResultsHeader() {
        wait.until(ExpectedConditions.visibilityOf(resultsHeader));
        return resultsHeader.getText();
    }

    // Handle empty search
    public String getEmptySearchMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
        return emptySearchMessage.getText();
    }
}
