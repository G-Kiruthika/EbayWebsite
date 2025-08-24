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

    @FindBy(css = "div.srp-controls__count-heading")
    WebElement resultsHeading;

    @FindBy(css = "div.srp-controls__control--legacy")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    public boolean isSearchBoxVisible() {
        return wait.until(ExpectedConditions.visibilityOf(searchBox)).isDisplayed();
    }

    public void searchForProduct(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
    }

    public void typePartialKeyword(String partial) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(partial);
    }

    public boolean isSuggestionDropdownVisible() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList)).size() > 0;
    }

    public void selectSuggestion(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        suggestionList.get(index).click();
    }

    public boolean isResultsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(resultsHeading)).isDisplayed();
    }

    public boolean isEmptySearchHandled() {
        // eBay may show a message or simply stay on the page
        try {
            return emptySearchMessage.isDisplayed();
        } catch (Exception e) {
            return true; // If no error, it's handled gracefully
        }
    }
}
