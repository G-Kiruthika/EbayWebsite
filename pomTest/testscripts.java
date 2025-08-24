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
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        cartPage = new AddToCart(driver);
    }

    @Test(priority = 1)
    public void testValidLogin() {
        // Test Case: Valid login redirects to home
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load correctly");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // After login, search box should be visible
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible after login");
    }

    @Test(priority = 2)
    public void testInvalidEmailLogin() {
        driver.get("https://signin.ebay.com/signin");
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load correctly");
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("email"), "Error message for invalid email not shown");
    }

    @Test(priority = 3)
    public void testInvalidPasswordLogin() {
        driver.get("https://signin.ebay.com/signin");
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load correctly");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("password"), "Error message for invalid password not shown");
    }

    @Test(priority = 4)
    public void testSearchWithKeyword() {
        // Assumes already logged in
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible");
        searchPage.searchForProduct("fossil watch");
        String header = searchPage.getResultsHeader();
        Assert.assertTrue(header.toLowerCase().contains("fossil watch"), "Search results do not match keyword");
    }

    @Test(priority = 5)
    public void testSearchSuggestions() {
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible");
        searchPage.typePartialKeyword("fos");
        // Select suggestion (simulate picking the first suggestion)
        searchPage.selectSuggestion("fossil watch");
        String header = searchPage.getResultsHeader();
        Assert.assertTrue(header.toLowerCase().contains("fossil watch"), "Suggestion selection did not show correct results");
    }

    @Test(priority = 6)
    public void testEmptySearch() {
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible");
        searchPage.searchForProduct("");
        String msg = searchPage.getEmptySearchMessage();
        Assert.assertTrue(msg.length() > 0, "Empty search did not show a user-friendly message");
    }

    @Test(priority = 7)
    public void testAddToCartFlow() {
        searchPage.searchForProduct("fossil watch");
        cartPage.selectFirstProduct();
        cartPage.clickAddToCart();
        String confirmation = cartPage.getAddToCartConfirmation();
        Assert.assertTrue(confirmation.toLowerCase().contains("added to cart"), "Add to cart confirmation not shown");
        int count = cartPage.getCartCount();
        Assert.assertTrue(count > 0, "Cart count did not update after adding item");
        cartPage.goToCart();
        int cartItems = cartPage.getCartItemsCount();
        Assert.assertTrue(cartItems >= 1, "Cart does not show added items");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
