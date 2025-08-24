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

    @FindBy(css = ".ui-menu-item")
    List<WebElement> searchSuggestions;

    @FindBy(css = ".srp-results .s-item")
    List<WebElement> searchResults;

    @FindBy(css = ".srp-controls__control--legacy")
    WebElement emptySearchMessage;

    public Search(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String term) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(term);
    }

    public void selectSuggestion(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchSuggestions));
        if (searchSuggestions.size() > index) {
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

    public boolean isEmptySearchHandled() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
