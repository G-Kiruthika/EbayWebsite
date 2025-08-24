package pomTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.Assert;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import pomPages.Login;
import pomPages.Search;
import pomPages.AddToCart;
import pomPages.ResultsPage;
public class testscript{
	public WebDriver driver;
	
	@BeforeClass
    public void setup() {
        driver = new ChromeDriver();
 
        driver.manage().window().maximize();
        driver.get("https://signin.ebay.com/signin");
        System.out.println("Navigating to URL ");
        
    }
 
	//positive login testcase
	@Test(priority=1)
    public void loginToAccountPositive() throws Exception {
		Login loginPage = new Login(driver);
		loginPage.verifyEmailVisibility();
		loginPage.verifyEmailClickability();
        loginPage.enterEmail("qetestascend@gmail.com");
        loginPage.verifyContinueBtnVisibility();
        loginPage.verifyContinueBtnClickability();
        loginPage.clickContinue();
        loginPage.verifyPasswordVisibility();
        loginPage.verifyPasswordClickability();
        loginPage.enterPassword("Kiruthika2002");
        loginPage.verifySignInBtnVisibility();
        loginPage.verifySignInBtnClickability();
        loginPage.clickSignIn();
        loginPage.homePageTitleCheck();
        System.out.println("Login successful");
        Thread.sleep(3000);
       
    }
	@Test(priority=2)
	public void searchProduct()throws Exception{
		Search search = new Search(driver);
		search.searchProduct("fossil watch");
		System.out.println("Product searched");
	}
	@Test(priority=3)
	public void addToCartProduct()throws Exception{
		AddToCart add = new AddToCart(driver);
		ResultsPage result = new ResultsPage(driver);
		result.searchProduct("watch");
		result.clickFirstProduct();
		// switch to new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        add.clickAddToCart();
        add.clickClose();
        add.goToCart();
	}
	
	 @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser closed.");
	        }
	    }
}



