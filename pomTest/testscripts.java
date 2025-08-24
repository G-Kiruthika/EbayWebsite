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

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    AddToCart addToCartPage;

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
     * Test Case HAP-458 TS-001 TC-001
     * Steps: Launch browser, navigate to login, wait for load, verify title
     */
    @Test(priority = 1)
    public void testLoginPageTitle() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.waitForLoginPageToLoad();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Sign in or Register | eBay", "Page title is incorrect.");
    }

    /**
     * Test Case HAP-458 TS-002 TC-001
     * Steps: Launch browser, navigate to login, wait for load, locate email input, verify visibility, focus, type sample email
     */
    @Test(priority = 2)
    public void testEmailInputField() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.waitForLoginPageToLoad();
        Assert.assertTrue(loginPage.isEmailInputVisible(), "Email input field is not visible.");
        loginPage.focusEmailInput();
        loginPage.enterEmail("test@example.com");
        // Verify input is accepted
        Assert.assertEquals(loginPage.emailInput.getAttribute("value"), "test@example.com", "Email input not accepted.");
    }

    /**
     * Test Case HAP-458 TS-003 TC-001
     * Steps: Launch browser, navigate to login, wait for load, enter valid email/password, sign in, verify redirect
     */
    @Test(priority = 3)
    public void testValidLoginRedirectsToHome() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.waitForLoginPageToLoad();
        loginPage.enterEmail("qetestascend@gmail.com");
        // Some eBay flows require clicking Continue, some show password directly
        try {
            loginPage.clickContinue();
        } catch (Exception ignored) {}
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Wait for redirect
        for (int i = 0; i < 10; i++) {
            if (driver.getCurrentUrl().startsWith("https://www.ebay.com")) break;
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https://www.ebay.com"), "User was not redirected to eBay home page.");
    }

    // Example: Search and Add to Cart (not in provided test cases, but for completeness)
    @Test(priority = 4, enabled = false)
    public void testSearchAndAddToCart() {
        // Assumes user is already logged in
        searchPage = new Search(driver);
        searchPage.waitForSearchBox();
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.submitSearch();
        searchPage.waitForSearchResults();
        addToCartPage = new AddToCart(driver);
        addToCartPage.selectFirstProduct();
        addToCartPage.clickAddToCart();
        Assert.assertTrue(addToCartPage.isAddToCartConfirmationDisplayed(), "Add to Cart confirmation not displayed.");
        int cartCount = addToCartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count did not update.");
        addToCartPage.goToCart();
        Assert.assertTrue(addToCartPage.isCartContentsDisplayed(), "Cart contents not displayed.");
    }
}
