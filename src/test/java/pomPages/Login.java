package pomPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.asserts.SoftAssert;

public class Login{
	WebDriver driver;
    WebDriverWait wait;
    
	@FindBy(xpath ="//input[@id='userid']")
	WebElement emailField;
    @FindBy(xpath="//button[@id='signin-continue-btn']")
    WebElement ctnButton;
    @FindBy(xpath="//p[@id='signin-error-msg']")
    WebElement errorMsg;
	@FindBy(xpath="//input[@id='pass']")
	WebElement passwordField;
	@FindBy(xpath = "//button[@id='sgnBt']")
	WebElement signInBtn;
	@FindBy(xpath="//a[@id='passkeys-cancel-btn']")
	WebElement skipForNow;
	
	// Constructor
    public Login(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        PageFactory.initElements(driver, this); 
    }
    
    //signin page title check
    public String loginPageTitleCheck()  throws Exception {

        System.out.println( driver.getTitle());
        return driver.getTitle();
        
	}
    //email validation
    public boolean verifyEmailVisibility() {
    	wait.until(ExpectedConditions.visibilityOf(emailField));
    	return emailField.isDisplayed();
    }
    public boolean verifyEmailClickability() {
    	wait.until(ExpectedConditions.elementToBeClickable(emailField));
    	return emailField.isEnabled();
    }
    
    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }
    
  //continue button validation
    public boolean verifyContinueBtnVisibility() {
    	wait.until(ExpectedConditions.visibilityOf(ctnButton));
    	return ctnButton.isDisplayed();
    }
    public boolean verifyContinueBtnClickability() {
    	wait.until(ExpectedConditions.elementToBeClickable(ctnButton));
    	return ctnButton.isEnabled();
    }

    public void clickContinue() {
        ctnButton.click();
    }
    
  //password validation
    public boolean verifyPasswordVisibility() {
    	wait.until(ExpectedConditions.visibilityOf(passwordField));
    	return passwordField.isDisplayed();
    }
    public boolean verifyPasswordClickability() {
    	wait.until(ExpectedConditions.elementToBeClickable(passwordField));
    	return passwordField.isEnabled();
    }
    
    public void enterPassword(String password) {
    	passwordField.sendKeys(password);
    }
    
  //signIn button validation
    public boolean verifySignInBtnVisibility() {
    	wait.until(ExpectedConditions.visibilityOf(signInBtn));
    	return signInBtn.isDisplayed();
    }
    public void verifySignInBtnClickability() {
    	wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
    	
    }
    public void clickSignIn() {
        signInBtn.click();
//        wait.until(ExpectedConditions.elementToBeClickable(skipForNow));
//        skipForNow.click();
    }
    
    public String homePageTitleCheck()  throws Exception {

        System.out.println( driver.getTitle());
        return driver.getTitle();
        
	}
   
    public void loginToAccount(String email, String password) throws InterruptedException {
        enterEmail(email);
        clickContinue();
        Thread.sleep(2000); 
        enterPassword(password);
        clickSignIn();
        Thread.sleep(2000);
    }
    

}
