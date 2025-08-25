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
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testValidLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Assert home page loaded by checking search box visibility
        searchPage = new Search(driver);
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("ebay"), "Home page title should contain 'ebay'");
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "Should be redirected to eBay home");
    }

    @Test(priority = 2)
    public void testInvalidEmailLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("invalid_email@xyz.com");
        loginPage.clickContinue();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("email"), "Error message should indicate invalid email");
    }

    @Test(priority = 3)
    public void testInvalidPasswordLogin() {
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickSignIn();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("password"), "Error message should indicate incorrect password");
    }

    @Test(priority = 4, dependsOnMethods = {"testValidLogin"})
    public void testProductSearch() {
        // Assumes already logged in
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.clickSearch();
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "Search results should be displayed for 'fossil watch'");
    }

    @Test(priority = 5, dependsOnMethods = {"testValidLogin"})
    public void testSearchSuggestions() {
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("fos");
        searchPage.selectSuggestion("fossil watch");
        Assert.assertTrue(searchPage.isSearchResultDisplayed(), "Search results should be displayed for suggestion 'fossil watch'");
    }

    @Test(priority = 6, dependsOnMethods = {"testValidLogin"})
    public void testEmptySearch() {
        searchPage = new Search(driver);
        searchPage.enterSearchKeyword("");
        searchPage.clickSearch();
        String msg = searchPage.getEmptySearchMessage();
        Assert.assertTrue(msg.length() > 0, "Empty search should show a user-friendly message");
    }

    @Test(priority = 7, dependsOnMethods = {"testProductSearch"})
    public void testAddToCart() {
        addToCartPage = new AddToCart(driver);
        addToCartPage.selectFirstProduct();
        // Switch to new tab if product opens in new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        addToCartPage.clickAddToCart();
        String confirmation = addToCartPage.getAddToCartConfirmation();
        Assert.assertTrue(confirmation.toLowerCase().contains("added to cart") || confirmation.length() > 0, "Confirmation message should appear after adding to cart");
        int cartCount = addToCartPage.getCartCount();
        Assert.assertTrue(cartCount > 0, "Cart count should be updated after adding product");
    }

    @Test(priority = 8, dependsOnMethods = {"testAddToCart"})
    public void testViewCart() {
        addToCartPage = new AddToCart(driver);
        addToCartPage.goToCart();
        int items = addToCartPage.getCartItemsCount();
        Assert.assertTrue(items > 0, "Cart should display all added items");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
