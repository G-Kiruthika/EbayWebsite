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

    @FindBy(css = ".ui-autocomplete.ui-front li")
    List<WebElement> suggestionList;

    @FindBy(id = "gh-btn")
    WebElement searchButton;

    @FindBy(css = ".srp-results .s-item")
    List<WebElement> searchResults;

    @FindBy(css = ".srp-controls__control--legacy .srp-controls__count-heading")
    WebElement resultsHeading;

    @FindBy(css = ".search-error-message, .srp-error-message")
    WebElement searchErrorMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    /**
     * Enter a search keyword in the search box.
     * @param keyword The keyword to search
     */
    public void enterSearchKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    /**
     * Press Enter to execute the search.
     */
    public void pressEnterToSearch() {
        searchBox.sendKeys(Keys.ENTER);
    }

    /**
     * Click the search button to execute the search.
     */
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    /**
     * Select a suggestion from the dropdown by visible text.
     * @param suggestionText The suggestion to select
     */
    public void selectSuggestion(String suggestionText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        for (WebElement suggestion : suggestionList) {
            if (suggestion.getText().equalsIgnoreCase(suggestionText)) {
                suggestion.click();
                break;
            }
        }
    }

    /**
     * Get the number of search results displayed.
     * @return Number of results
     */
    public int getSearchResultsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
        return searchResults.size();
    }

    /**
     * Get the error message displayed for empty or invalid search.
     * @return Error message text
     */
    public String getSearchErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(searchErrorMessage));
        return searchErrorMessage.getText();
    }

    /**
     * Click the first product in the search results.
     */
    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
        if (!searchResults.isEmpty()) {
            searchResults.get(0).findElement(By.cssSelector("a.s-item__link")).click();
        }
    }
}
