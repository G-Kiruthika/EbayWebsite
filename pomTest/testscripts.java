package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pomPages.LoginPage;
import pomPages.SearchPage;
import pomPages.AddCartPage;
import java.time.Duration;

/**
 * TestScripts.java
 * TestNG-based automation for eBay core flows: Login, Search, Add to Cart.
 * Each test method corresponds to a test case and is traceable to requirements in the eBay functional knowledge base.
 * running_instance_id: yt765, pipeline_id: 3668
 */
public class TestScripts {
    private WebDriver driver;
    private LoginPage loginPage;
    private SearchPage searchPage;
    private AddCartPage addCartPage;

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver (ensure chromedriver is in PATH)
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        addCartPage = new AddCartPage(driver);
    }

    /**
     * Test Case 137: Valid Login
     * Verifies successful login with valid credentials and redirection to home page.
     */
    @Test(priority = 1)
    public void testValidLogin() {
        driver.get("https://signin.ebay.com/signin");
        Assert.assertEquals(driver.getTitle(), "Sign in or Register | eBay", "Login page title mismatch");
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.clickContinue();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.clickSignIn();
        // Wait for home page to load and verify
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box should be visible after successful login");
    }

    /**
     * Test Case 138: Search Product and Validate Results
     * Searches for 'fossil watch' and verifies relevant results are displayed.
     */
    @Test(priority = 2, dependsOnMethods = {"testValidLogin"})
    public void testSearchProduct() {
        Assert.assertTrue(searchPage.isSearchBoxVisible(), "Search box must be visible on home page");
        searchPage.enterSearchKeyword("fossil watch");
        searchPage.submitSearch();
        Assert.assertTrue(searchPage.areResultsRelevant("fossil watch"), "Search results should be relevant to 'fossil watch'");
    }

    /**
     * Test Case 139: Add Product to Cart and Validate Cart Count
     * Selects the first product from search results, adds to cart, and verifies cart count and confirmation.
     */
    @Test(priority = 3, dependsOnMethods = {"testSearchProduct"})
    public void testAddToCart() {
        addCartPage.selectProductFromResults(0);
        addCartPage.clickAddToCart();
        String confirmation = addCartPage.getAddToCartConfirmationMessage();
        Assert.assertNotNull(confirmation, "Confirmation message should be displayed after adding to cart");
        int cartCount = addCartPage.getCartItemCount();
        Assert.assertEquals(cartCount, 1, "Cart count should be updated to 1 after adding a product");
        addCartPage.goToCart();
        Assert.assertTrue(addCartPage.getCartItemsCountInCartPage() >= 1, "Product should be present in the cart");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
