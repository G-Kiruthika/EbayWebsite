package pomTest;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pomPages.Login;
import pomPages.Search;
import pomPages.ResultsPage;
import pomPages.AddToCart;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class testscripts {
    private WebDriver driver;
    private Login loginPage;
    private Search searchPage;
    private ResultsPage resultsPage;
    private AddToCart addToCartPage;
    private Properties config;

    private String baseUrl;
    private String email;
    private String password;

    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        config = new Properties();
        config.load(new FileInputStream("src/test/resources/config.properties"));
        baseUrl = config.getProperty("baseUrl", "https://signin.ebay.com/signin");
        email = config.getProperty("email");
        password = config.getProperty("password");
        driver.get(baseUrl);
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        resultsPage = new ResultsPage(driver);
        addToCartPage = new AddToCart(driver);
    }

    @Test(priority = 1)
    public void loginToAccountPositive() {
        // Test Case: Login with valid credentials
        loginPage.enterEmail(email);
        loginPage.clickContinue();
        loginPage.enterPassword(password);
        loginPage.clickSignIn();
        // Assert: Home page/profile icon visible (by checking page title contains 'eBay')
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("ebay"), "Login failed or homepage not loaded. Title: " + title);
    }

    @Test(priority = 2, dependsOnMethods = {"loginToAccountPositive"})
    public void searchProduct() {
        // Test Case: Search for a product
        String keyword = config.getProperty("searchKeyword", "titan watch");
        searchPage.searchProduct(keyword);
        // Assert: Results page loaded and at least one result present
        Assert.assertTrue(resultsPage.getResultsCount() > 0, "No search results found for: " + keyword);
    }

    @Test(priority = 3, dependsOnMethods = {"searchProduct"})
    public void addToCartProduct() {
        // Test Case: Add first product to cart and verify
        int cartCountBefore = addToCartPage.getCartCount();
        resultsPage.clickFirstProduct();
        // Switch to new tab
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        addToCartPage.clickAddToCart();
        Assert.assertTrue(addToCartPage.isProductAddedMessageDisplayed(), "Product added message not displayed.");
        int cartCountAfter = addToCartPage.getCartCount();
        Assert.assertTrue(cartCountAfter > cartCountBefore, "Cart count did not increment after adding product.");
        addToCartPage.clickSeeInCart();
        Assert.assertTrue(addToCartPage.goToCart(), "Cart page not loaded after clicking cart icon.");
        // Sign out
        addToCartPage.clickSignOut();
        // Assert: Redirected to sign-in page
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("sign in") || driver.getCurrentUrl().contains("signin"), "Sign out failed or not redirected to sign-in page.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
