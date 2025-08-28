package pomTest;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pomPages.Login;
import pomPages.Search;
import pomPages.ResultsPage;
import pomPages.AddToCart;
import java.util.Set;
import java.util.Iterator;
import static org.testng.Assert.*;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    ResultsPage resultsPage;
    AddToCart addToCartPage;

    // Test data (should be externalized in real projects)
    String email = "testuser@example.com";
    String password = "TestPassword123";
    String searchKeyword = "titan watch";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://signin.ebay.com/signin");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        resultsPage = new ResultsPage(driver);
        addToCartPage = new AddToCart(driver);
    }

    @Test(priority = 1)
    public void loginToAccountPositive() {
        // Step 1: Login
        loginPage.enterEmail(email);
        loginPage.clickContinue();
        loginPage.enterPassword(password);
        loginPage.clickSignIn();
        // Assert: login success by checking page title or profile icon
        String title = driver.getTitle();
        assertTrue(title.toLowerCase().contains("ebay"), "Login failed or homepage not loaded");
    }

    @Test(priority = 2, dependsOnMethods = {"loginToAccountPositive"})
    public void searchProduct() {
        // Step 2: Search for a product
        searchPage.searchProduct(searchKeyword);
        // Assert: main content visible and results present
        int count = resultsPage.getResultsCount();
        assertTrue(count > 0, "No search results found for: " + searchKeyword);
    }

    @Test(priority = 3, dependsOnMethods = {"searchProduct"})
    public void addToCartProduct() {
        // Step 3: Click first product in results
        resultsPage.clickFirstProduct();
        // Switch to new tab
        String originalHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        // Step 4: Add to cart
        String beforeCount = addToCartPage.getCartCount();
        addToCartPage.clickAddToCart();
        assertTrue(addToCartPage.isProductAddedMessageDisplayed(), "Product not added to cart confirmation not displayed");
        String afterCount = addToCartPage.getCartCount();
        assertNotEquals(beforeCount, afterCount, "Cart count did not increment after adding product");
        // Step 5: See in cart
        addToCartPage.clickSeeInCart();
        assertTrue(addToCartPage.goToCart(), "Cart page not displayed");
        // Step 6: Sign out
        addToCartPage.clickSignOut();
        // Assert: redirected to sign-in page
        assertTrue(driver.getTitle().toLowerCase().contains("sign in"), "Not redirected to sign-in page after sign out");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
