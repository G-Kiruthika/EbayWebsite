package pomTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pomPages.Login;
import pomPages.HomePage;
import pomPages.CreateRepoPage;
import pomPages.DeleteRepoPage;
import pomPages.SignOut;

public class testscripts {
    WebDriver driver;
    Login loginPage;
    HomePage homePage;
    CreateRepoPage createRepoPage;
    DeleteRepoPage deleteRepoPage;
    SignOut signOutPage;

    String baseUrl = "https://github.com/";
    String email = "testuser@example.com";
    String password = "TestPassword123";
    String repoName = "selenium-test-repo";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        loginPage = new Login(driver);
        homePage = new HomePage(driver);
        createRepoPage = new CreateRepoPage(driver);
        deleteRepoPage = new DeleteRepoPage(driver);
        signOutPage = new SignOut(driver);
    }

    @Test(priority = 1)
    public void loginPositive() {
        loginPage.clickLoginLink();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickSignInButton();
        Assert.assertTrue(driver.getTitle().contains("GitHub"), "Login failed or incorrect page title.");
    }

    @Test(priority = 2)
    public void RepoCreation() {
        homePage.clickCreateRepo();
        createRepoPage.enterRepoName(repoName);
        createRepoPage.createRepoBtnClick();
        Assert.assertTrue(driver.getCurrentUrl().contains(repoName), "Repository creation failed or incorrect URL.");
    }

    @Test(priority = 3)
    public void DeleteRepo() {
        deleteRepoPage.clickSettings();
        deleteRepoPage.clickDelete();
        deleteRepoPage.typeRepoName("testuser/" + repoName);
        deleteRepoPage.clickProceedDelete();
        Assert.assertTrue(driver.getCurrentUrl().contains("/repositories"), "Repository deletion failed or incorrect redirect.");
    }

    @Test(priority = 4)
    public void SignOut() {
        signOutPage.clickProfile();
        signOutPage.clickSignOut();
        Assert.assertTrue(driver.getTitle().contains("GitHub: Where the world builds software"), "Sign out failed or incorrect page title.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
