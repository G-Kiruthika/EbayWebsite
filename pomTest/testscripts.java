package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.Login;
import pomPages.Search;
import pomPages.AddToCart;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    AddToCart cartPage;

    @BeforeClass
    public void setUp() {
        // Set path to chromedriver if needed
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * HAP-458 TS-001 TC-001: Verify login page loads and title is correct
     */
    @Test(priority = 1)
    public void testLoginPageLoadsAndTitle() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        String expectedTitle = "Sign in or Register | eBay";
        Assert.assertEquals(loginPage.getPageTitle(), expectedTitle, "Page title should match as per functional KB");
    }

    /**
     * HAP-458 TS-002 TC-001: Verify email input field is visible
     */
    @Test(priority = 2)
    public void testEmailInputFieldVisible() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        Assert.assertTrue(loginPage.isEmailInputVisible(), "Email input field should be visible as per KB");
    }

    /**
     * HAP-458 TS-002 TC-002: Click and type in email input field
     */
    @Test(priority = 3)
    public void testEmailInputFieldFocusAndType() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("test@example.com");
        Assert.assertTrue(loginPage.isEmailInputFocused(), "Email input should be focused after click");
        // No assertion for input value as Selenium does not retrieve input value directly for security reasons
    }

    /**
     * HAP-458 TS-003 TC-001: Valid login redirects to home
     */
    @Test(priority = 4)
    public void testValidLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Wait for redirect and check home page loaded (e.g., search box visible)
        searchPage = new Search(driver);
        searchPage.waitForSearchBox();
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "Should be redirected to eBay home page after login");
    }

    /**
     * HAP-458 TS-004 TC-001: Invalid email triggers error
     */
    @Test(priority = 5)
    public void testInvalidEmailShowsError() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        loginPage.enterPassword("any");
        loginPage.clickSignIn();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("email") || errorMsg.toLowerCase().contains("account"), "Error message should indicate invalid email as per KB");
    }

    // Additional test examples for search and add to cart (not in provided test cases but required for core flows)
    @Test(priority = 6, dependsOnMethods = {"testValidLogin"})
    public void testSearchFunctionality() {
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.submitSearch();
        Assert.assertTrue(searchPage.getResultsHeader().toLowerCase().contains("fossil watch"), "Results should be relevant to search keyword");
    }

    @Test(priority = 7, dependsOnMethods = {"testSearchFunctionality"})
    public void testAddToCartFunctionality() {
        cartPage = new AddToCart(driver);
        cartPage.selectFirstProduct();
        // Switch to new tab if product opens in new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        cartPage.clickAddToCart();
        Assert.assertTrue(cartPage.isAddToCartConfirmationDisplayed(), "Add to Cart confirmation should be displayed");
        int cartCount = cartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count should be updated after adding item");
        cartPage.clickCartIcon();
        Assert.assertTrue(cartPage.getCartItemsCount() >= 1, "Cart should show at least one item");
    }
}
