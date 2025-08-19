package pomTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.AssertJUnit;
import java.time.Duration;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import pomPages.AddToWishlist;
import pomPages.Login;
import pomPages.Search;

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
	@Test(priority=5)
    public void addToWish() throws Exception {
		 AddToWishlist add = new AddToWishlist(driver);
		 add.selectProduct();
		 
		 Thread.sleep(4000);
		 
		// Step 1: Reset state - if already in wishlist, unwatch it
		 String initialText = add.readAddToWishlistBtnText();
		 
		 if (initialText.equalsIgnoreCase("Added to watchlist")) {
		     System.out.println("Resetting state: Clicking 'Unwatch' to remove from wishlist");
		     add.clickAddToWishListBtn(); // Click to unwatch
		     Thread.sleep(2000);
		 }

		 // Step 2: Add to wishlist
		 add.clickAddToWishListBtn();
		 System.out.println("Clicked Add to wishlist");
		 Thread.sleep(2000);
	}	
	
	 @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser closed.");
	        }
	    }
}



