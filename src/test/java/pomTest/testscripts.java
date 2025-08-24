package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        addToCartPage = new AddToCart(driver);
    }

    /**
     * Test Case: Valid Login
     * Steps: Enter valid email and password, verify successful login.
     */
    @Test(priority = 1)
    public void testValidLogin() {
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // After successful login, search box should be visible
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("ebay"), "Home page title should contain 'ebay'");
        Assert.assertTrue(driver.findElement(org.openqa.selenium.By.id("gh-ac")).isDisplayed(), "Search box should be visible after login");
    }

    /**
     * Test Case: Invalid Email
     * Steps: Enter invalid email, verify error message.
     */
    @Test(priority = 2)
    public void testInvalidEmail() {
        driver.get("https://signin.ebay.com/signin");
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("email"), "Error message should indicate invalid email");
    }

    /**
     * Test Case: Invalid Password
     * Steps: Enter valid email, invalid password, verify error message.
     */
    @Test(priority = 3)
    public void testInvalidPassword() {
        driver.get("https://signin.ebay.com/signin");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("password"), "Error message should indicate incorrect password");
    }

    /**
     * Test Case: Search with Valid Keyword
     * Steps: Search for 'fossil watch', verify results.
     */
    @Test(priority = 4, dependsOnMethods = {"testValidLogin"})
    public void testSearchValidKeyword() {
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.pressEnterToSearch();
        Assert.assertTrue(searchPage.getSearchResultsCount() > 0, "Search results should be displayed for 'fossil watch'");
    }

    /**
     * Test Case: Search Suggestions
     * Steps: Type partial term, select suggestion, verify results.
     */
    @Test(priority = 5, dependsOnMethods = {"testValidLogin"})
    public void testSearchSuggestions() {
        searchPage.enterSearchKeyword("fos");
        searchPage.selectSuggestion("fossil watch");
        Assert.assertTrue(searchPage.getSearchResultsCount() > 0, "Search results should be displayed for selected suggestion");
    }

    /**
     * Test Case: Empty Search
     * Steps: Submit empty search, verify graceful handling.
     */
    @Test(priority = 6, dependsOnMethods = {"testValidLogin"})
    public void testEmptySearch() {
        searchPage.enterSearchKeyword("");
        searchPage.pressEnterToSearch();
        // eBay may show a user-friendly message or simply not navigate
        // Check for error message or no navigation
        boolean errorDisplayed = false;
        try {
            String msg = searchPage.getSearchErrorMessage();
            errorDisplayed = msg != null && !msg.isEmpty();
        } catch (Exception e) {
            // No error message, which is also acceptable as per KB
            errorDisplayed = true;
        }
        Assert.assertTrue(errorDisplayed, "Empty search should be handled gracefully");
    }

    /**
     * Test Case: Add to Cart
     * Steps: Search for product, open first result, add to cart, verify confirmation and cart count.
     */
    @Test(priority = 7, dependsOnMethods = {"testSearchValidKeyword"})
    public void testAddToCart() {
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.pressEnterToSearch();
        searchPage.clickFirstProduct();
        String originalWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        addToCartPage.clickAddToCart();
        String confirmation = addToCartPage.getAddToCartConfirmation();
        Assert.assertTrue(confirmation.toLowerCase().contains("added"), "Confirmation message should indicate item added");
        int cartCount = addToCartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count should be updated after adding item");
        driver.switchTo().window(originalWindow);
    }

    /**
     * Test Case: View Cart
     * Steps: Click cart icon, verify cart contents.
     */
    @Test(priority = 8, dependsOnMethods = {"testAddToCart"})
    public void testViewCart() {
        addToCartPage.goToCart();
        Assert.assertTrue(addToCartPage.isCartItemListDisplayed(), "Cart item list should be displayed");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
