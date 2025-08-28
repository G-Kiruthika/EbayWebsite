import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pom.Login;
import pom.Search;
import pom.ResultsPage;
import pom.AddToCart;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    Search searchPage;
    ResultsPage resultsPage;
    AddToCart addToCartPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://example.com");
        loginPage = new Login(driver);
        searchPage = new Search(driver);
        resultsPage = new ResultsPage(driver);
        addToCartPage = new AddToCart(driver);
    }

    // Login Test Cases
    @Test(priority=1)
    public void testValidLogin() {
        // Test valid login
        loginPage.enterUsername("user1");
        loginPage.enterPassword("pass1");
        loginPage.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed");
    }

    @Test(priority=2)
    public void testInvalidLogin() {
        // Test invalid login
        loginPage.enterUsername("invalid");
        loginPage.enterPassword("wrong");
        loginPage.clickLogin();
        Assert.assertTrue(driver.getPageSource().contains("Invalid credentials"), "Error message not shown");
    }

    // Search Test Cases
    @Test(priority=3)
    public void testSearchProduct() {
        // Test searching for a product
        searchPage.enterSearchTerm("Laptop");
        searchPage.clickSearch();
        Assert.assertTrue(resultsPage.getFirstResultText().contains("Laptop"), "Search result mismatch");
    }

    // Add to Cart Test Cases
    @Test(priority=4)
    public void testAddToCart() {
        // Test adding product to cart
        addToCartPage.clickAddToCart();
        Assert.assertTrue(addToCartPage.isProductInCart(), "Product not in cart");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
