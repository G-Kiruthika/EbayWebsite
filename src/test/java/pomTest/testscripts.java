package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.Login;
import pomPages.Search;
import pomPages.AddToCart;
import java.time.Duration;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    AddToCart addToCartPage;

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver (ensure chromedriver is in PATH)
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(priority = 1, description = "Verify eBay login page loads and title is correct")
    public void testLoginPageLoad() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load with correct title.");
    }

    @Test(priority = 2, description = "Login with valid credentials and verify search box is visible")
    public void testValidLogin() {
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        searchPage = new Search(driver);
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible after login.");
    }

    @Test(priority = 3, description = "Login with invalid email and verify error message")
    public void testInvalidEmailLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("email"), "Expected email error message not displayed.");
    }

    @Test(priority = 4, description = "Login with valid email and invalid password and verify error message")
    public void testInvalidPasswordLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("password"), "Expected password error message not displayed.");
    }

    @Test(priority = 5, description = "Search for a product and verify results are displayed")
    public void testProductSearch() {
        searchPage = new Search(driver);
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible.");
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.submitSearch();
        String header = searchPage.getResultsHeader();
        Assert.assertTrue(header.toLowerCase().contains("results"), "Product results not displayed as expected.");
    }

    @Test(priority = 6, description = "Search with partial term and select suggestion")
    public void testSearchSuggestions() {
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("fos");
        searchPage.selectSuggestion(0); // Select first suggestion
        String header = searchPage.getResultsHeader();
        Assert.assertTrue(header.toLowerCase().contains("results"), "Results not displayed for selected suggestion.");
    }

    @Test(priority = 7, description = "Submit empty search and verify user-friendly message")
    public void testEmptySearch() {
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("");
        searchPage.submitSearch();
        String msg = searchPage.getEmptySearchMessage();
        Assert.assertTrue(msg.length() > 0, "No user-friendly message for empty search.");
    }

    @Test(priority = 8, description = "Add product to cart and verify confirmation and cart count")
    public void testAddToCart() {
        addToCartPage = new AddToCart(driver);
        addToCartPage.selectProduct(0); // Select first product from results
        addToCartPage.clickAddToCart();
        String confirmation = addToCartPage.getAddToCartConfirmation();
        Assert.assertTrue(confirmation.toLowerCase().contains("added"), "Add to cart confirmation not displayed.");
        int count = addToCartPage.getCartCount();
        Assert.assertTrue(count > 0, "Cart count not updated after adding product.");
    }

    @Test(priority = 9, description = "Add another product and verify cart count increments")
    public void testAddAnotherProductToCart() {
        driver.navigate().back(); // Go back to search results
        addToCartPage.selectProduct(1); // Select second product
        addToCartPage.clickAddToCart();
        int count = addToCartPage.getCartCount();
        Assert.assertTrue(count > 1, "Cart count did not increment after adding another product.");
    }

    @Test(priority = 10, description = "View cart and verify all items are listed")
    public void testViewCart() {
        addToCartPage.clickCartIcon();
        int items = addToCartPage.getCartItemsCount();
        int count = addToCartPage.getCartCount();
        Assert.assertEquals(items, count, "Cart items count does not match cart icon count.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
