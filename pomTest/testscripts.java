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
    }

    @Test(priority = 1)
    public void testValidLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        Assert.assertTrue(loginPage.isAtLoginPage(), "Login page did not load correctly");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Wait for home page by checking search box
        searchPage = new Search(driver);
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible after login");
    }

    @Test(priority = 2)
    public void testInvalidEmailLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("email"), "Email error not displayed for invalid email");
    }

    @Test(priority = 3)
    public void testInvalidPasswordLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.toLowerCase().contains("password"), "Password error not displayed for invalid password");
    }

    @Test(priority = 4, dependsOnMethods = {"testValidLogin"})
    public void testProductSearchAndSuggestions() {
        searchPage = new Search(driver);
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible");
        searchPage.typePartialKeyword("fos");
        Assert.assertTrue(searchPage.isSuggestionDropdownVisible(), "Suggestions not shown for partial keyword");
        searchPage.selectSuggestion(0); // Select first suggestion
        Assert.assertTrue(searchPage.isResultsDisplayed(), "Results not displayed after selecting suggestion");
    }

    @Test(priority = 5, dependsOnMethods = {"testValidLogin"})
    public void testEmptySearchHandling() {
        searchPage = new Search(driver);
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box not visible");
        searchPage.searchForProduct("");
        Assert.assertTrue(searchPage.isEmptySearchHandled(), "Empty search not handled gracefully");
    }

    @Test(priority = 6, dependsOnMethods = {"testValidLogin"})
    public void testAddToCartFlow() {
        searchPage = new Search(driver);
        searchPage.searchForProduct("fossil watch");
        Assert.assertTrue(searchPage.isResultsDisplayed(), "Product results not displayed");
        cartPage = new AddToCart(driver);
        cartPage.selectProduct(0); // Select first product
        cartPage.addToCart();
        Assert.assertTrue(cartPage.isAddToCartConfirmationDisplayed(), "Add to cart confirmation not shown");
        int countAfterAdd = cartPage.getCartCount();
        Assert.assertTrue(countAfterAdd > 0, "Cart count not updated after adding product");
        cartPage.goToCart();
        int cartItems = cartPage.getCartItemsCount();
        Assert.assertTrue(cartItems > 0, "Cart does not show added items");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
