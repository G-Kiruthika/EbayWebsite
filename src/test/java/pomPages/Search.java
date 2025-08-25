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
    List<WebElement> suggestionList;

    @FindBy(css = "div.srp-controls__control")
    WebElement searchResultSection;

    @FindBy(css = "div.srp-controls__control--legacy")
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

    // Select suggestion by visible text
    public void selectSuggestion(String suggestionText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionList));
        for (WebElement suggestion : suggestionList) {
            if (suggestion.getText().equalsIgnoreCase(suggestionText)) {
                suggestion.click();
                return;
            }
        }
    }

    // Click search button
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    // Check if search results are displayed
    public boolean isSearchResultDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResultSection));
            return searchResultSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
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
