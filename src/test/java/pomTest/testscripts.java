package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.Login;
import pomPages.Search;
import pomPages.AddToCart;
import org.openqa.selenium.WebElement;
import java.util.List;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    AddToCart addToCartPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void initPages() {
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        addToCartPage = new AddToCart(driver);
    }

    @Test(description = "Valid Login redirects to home page")
    public void testValidLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        Assert.assertTrue(driver.getTitle().contains("eBay"), "Login did not redirect to home page as expected.");
    }

    @Test(description = "Invalid email triggers error message")
    public void testInvalidEmailLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("invalid"), "Error message for invalid email not shown as expected.");
    }

    @Test(description = "Valid email and invalid password triggers error message")
    public void testInvalidPasswordLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("incorrect"), "Error message for invalid password not shown as expected.");
    }

    @Test(description = "Search for product by keyword")
    public void testProductSearch() {
        // Assume already logged in
        driver.get("https://www.ebay.com/");
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.clickSearch();
        List<WebElement> results = searchPage.getProductResults();
        Assert.assertTrue(results.size() > 0, "No product results found for valid keyword.");
        String countText = searchPage.getResultsCountText();
        Assert.assertTrue(countText.toLowerCase().contains("results"), "Results count not displayed as expected.");
    }

    @Test(description = "Search suggestions appear for partial keyword")
    public void testSearchSuggestions() {
        driver.get("https://www.ebay.com/");
        searchPage.enterSearchKeyword("fos");
        List<WebElement> suggestions = searchPage.getSuggestions();
        Assert.assertTrue(suggestions.size() > 0, "No search suggestions displayed for partial keyword.");
        // Select first suggestion and verify results
        searchPage.selectSuggestion(0);
        List<WebElement> results = searchPage.getProductResults();
        Assert.assertTrue(results.size() > 0, "No results displayed after selecting suggestion.");
    }

    @Test(description = "Empty search handled gracefully")
    public void testEmptySearch() {
        driver.get("https://www.ebay.com/");
        searchPage.enterSearchKeyword("");
        searchPage.clickSearch();
        String msg = searchPage.getEmptySearchMessage();
        Assert.assertTrue(msg.length() > 0, "Empty search did not show user-friendly message.");
    }

    @Test(description = "Add product to cart and verify cart count and confirmation")
    public void testAddToCart() {
        driver.get("https://www.ebay.com/");
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.clickSearch();
        addToCartPage.selectProduct(0);
        addToCartPage.clickAddToCart();
        String confirmation = addToCartPage.getConfirmationMessage();
        Assert.assertTrue(confirmation.toLowerCase().contains("added"), "Confirmation message not displayed after adding to cart.");
        int cartCount = addToCartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count did not update after adding product.");
    }

    @Test(description = "View cart displays all added items")
    public void testViewCart() {
        addToCartPage.clickCartIcon();
        List<WebElement> items = addToCartPage.getCartItems();
        Assert.assertTrue(items.size() > 0, "Cart does not display added items.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
