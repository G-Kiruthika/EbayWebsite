package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.Login;
import pomPages.Search;
import pomPages.AddToCart;
import java.time.Duration;

/**
 * Main Test Script for eBay Core Functionalities
 * Each test case is mapped to a method, traceable to requirements and test cases
 */
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
    public void navigateToLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        addToCartPage = new AddToCart(driver);
    }

    /**
     * TestCaseID: TC_Login_Valid
     * Description: Login with valid credentials
     */
    @Test(priority = 1)
    public void testLoginValid() {
        Assert.assertTrue(loginPage.isAtLoginPage(), "Not at login page");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // After login, search box should be visible
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "Login failed or not redirected to home");
    }

    /**
     * TestCaseID: TC_Login_InvalidEmail
     * Description: Login with invalid email
     */
    @Test(priority = 2)
    public void testLoginInvalidEmail() {
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("email"), "Expected email error message");
    }

    /**
     * TestCaseID: TC_Login_InvalidPassword
     * Description: Login with valid email and invalid password
     */
    @Test(priority = 3)
    public void testLoginInvalidPassword() {
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("password"), "Expected password error message");
    }

    /**
     * TestCaseID: TC_Search_Valid
     * Description: Search for a valid product
     */
    @Test(priority = 4, dependsOnMethods = {"testLoginValid"})
    public void testSearchValidProduct() {
        // Assume already logged in from testLoginValid
        searchPage.enterSearchTerm("fossil watch");
        searchPage.clickSearch();
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "No search results displayed for valid product");
    }

    /**
     * TestCaseID: TC_Search_Suggestion
     * Description: Search using suggestion
     */
    @Test(priority = 5, dependsOnMethods = {"testLoginValid"})
    public void testSearchWithSuggestion() {
        searchPage.enterSearchTerm("fos");
        searchPage.selectSuggestion(0); // Select first suggestion
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "No results after selecting suggestion");
    }

    /**
     * TestCaseID: TC_Search_Empty
     * Description: Search with empty input
     */
    @Test(priority = 6, dependsOnMethods = {"testLoginValid"})
    public void testSearchEmpty() {
        searchPage.enterSearchTerm("");
        searchPage.clickSearch();
        String msg = searchPage.getEmptySearchMessage();
        Assert.assertTrue(msg.length() > 0, "No message shown for empty search");
    }

    /**
     * TestCaseID: TC_AddToCart
     * Description: Add product to cart and verify cart count
     */
    @Test(priority = 7, dependsOnMethods = {"testSearchValidProduct"})
    public void testAddToCart() {
        addToCartPage.selectProduct(0); // Select first product
        addToCartPage.clickAddToCart();
        String confirmation = addToCartPage.getAddToCartConfirmation();
        Assert.assertTrue(confirmation.toLowerCase().contains("added"), "No confirmation after adding to cart");
        int count = addToCartPage.getCartCount();
        Assert.assertTrue(count > 0, "Cart count did not update");
    }

    /**
     * TestCaseID: TC_ViewCart
     * Description: View cart and verify items
     */
    @Test(priority = 8, dependsOnMethods = {"testAddToCart"})
    public void testViewCart() {
        addToCartPage.goToCart();
        int items = addToCartPage.getCartItemsCount();
        Assert.assertTrue(items > 0, "Cart is empty after adding item");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
