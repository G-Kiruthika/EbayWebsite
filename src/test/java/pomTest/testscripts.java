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
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeEachTest() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        addToCartPage = new AddToCart(driver);
    }

    @Test(description = "Valid Login redirects to home page")
    public void testValidLogin() {
        Assert.assertTrue(loginPage.isAtLoginPage(), "Login page did not load correctly");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Home page search box should be visible
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("ebay"), "Did not redirect to eBay home page after login");
    }

    @Test(description = "Invalid email shows error message")
    public void testInvalidEmailLogin() {
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("email"), "Error message for invalid email not displayed");
    }

    @Test(description = "Valid email, invalid password shows error message")
    public void testInvalidPasswordLogin() {
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("password"), "Error message for invalid password not displayed");
    }

    @Test(description = "Search for a valid product keyword")
    public void testProductSearch() {
        // Login first
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Search
        searchPage.enterSearchTerm("fossil watch");
        searchPage.clickSearch();
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "Search results not displayed for valid keyword");
    }

    @Test(description = "Search suggestions appear for partial term")
    public void testSearchSuggestions() {
        // Login first
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Enter partial term
        searchPage.enterSearchTerm("fos");
        // Wait and select first suggestion
        searchPage.selectSuggestion(0);
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "Search results not displayed after selecting suggestion");
    }

    @Test(description = "Empty search input is handled gracefully")
    public void testEmptySearch() {
        // Login first
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Empty search
        searchPage.enterSearchTerm("");
        searchPage.clickSearch();
        Assert.assertTrue(searchPage.isEmptySearchHandled(), "Empty search was not handled gracefully");
    }

    @Test(description = "Add product to cart and verify confirmation and cart count")
    public void testAddToCart() {
        // Login first
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Search
        searchPage.enterSearchTerm("fossil watch");
        searchPage.clickSearch();
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "Search results not displayed");
        // Select first product
        addToCartPage.selectProduct(0);
        // Switch to new tab if opened
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        addToCartPage.clickAddToCart();
        Assert.assertTrue(addToCartPage.isAddToCartConfirmationDisplayed(), "Add to cart confirmation not displayed");
        int cartCount = addToCartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count did not update after adding product");
        // Go to cart and verify item
        addToCartPage.goToCart();
        Assert.assertTrue(addToCartPage.getCartItemsCount() >= 1, "Cart does not contain added item");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
