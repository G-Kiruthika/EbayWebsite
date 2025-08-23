package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * SearchPage encapsulates all search-related UI elements and actions for eBay's home page.
 * Traceability: Covers requirements for search box visibility, keyword entry, suggestion handling,
 * search execution, empty search validation, and result verification as per eBay functional knowledge base.
 */
public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Search box visible after login
    @FindBy(id = "gh-ac")
    private WebElement searchBox;

    // Search button (magnifier icon)
    @FindBy(id = "gh-btn")
    private WebElement searchButton;

    // Search suggestions dropdown (appears on typing partial term)
    @FindBy(css = "ul[role='listbox'] li")
    private List<WebElement> suggestionList;

    // Search result items
    @FindBy(css = "ul.srp-results > li.s-item")
    private List<WebElement> searchResults;

    // Empty search error/message (if any)
    @FindBy(css = "div#mainContent .srp-controls__control")
    private WebElement emptySearchMessage;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Enters a search keyword into the search box.
     * @param keyword The product keyword to search for.
     */
    public void enterSearchKeyword(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
    }

    /**
     * Waits for and returns the list of search suggestions.
     * @return List of suggestion WebElements.
     */
    public List<WebElement> getSearchSuggestions() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("ul[role='listbox'] li")));
        return suggestionList;
    }

    /**
     * Selects a suggestion from the dropdown by visible text.
     * @param suggestionText The suggestion to select.
     */
    public void selectSuggestion(String suggestionText) {
        List<WebElement> suggestions = getSearchSuggestions();
        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().trim().equalsIgnoreCase(suggestionText.trim())) {
                suggestion.click();
                return;
            }
        }
        throw new RuntimeException("Suggestion not found: " + suggestionText);
    }

    /**
     * Submits the search by pressing the search button or Enter key.
     * If keyword is empty, triggers empty search handling.
     */
    public void submitSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    /**
     * Checks if the empty search message is displayed (for empty search submissions).
     * @return true if empty search message is visible, false otherwise.
     */
    public boolean isEmptySearchMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptySearchMessage));
            return emptySearchMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for and returns the list of search result items.
     * @return List of result WebElements.
     */
    public List<WebElement> getSearchResults() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("ul.srp-results > li.s-item")));
        return searchResults;
    }

    /**
     * Verifies that all search results contain the expected keyword or suggestion.
     * @param expectedText The keyword or suggestion expected in results.
     * @return true if all results are relevant, false otherwise.
     */
    public boolean areResultsRelevant(String expectedText) {
        List<WebElement> results = getSearchResults();
        for (WebElement result : results) {
            String resultText = result.getText().toLowerCase();
            if (!resultText.contains(expectedText.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Utility: Checks if search box is visible (for post-login validation).
     * @return true if visible, false otherwise.
     */
    public boolean isSearchBoxVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            return searchBox.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
