package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "userid")
    WebElement emailField;

    @FindBy(id = "signin-continue-btn")
    WebElement continueButton;

    @FindBy(id = "pass")
    WebElement passwordField;

    @FindBy(id = "sgnBt")
    WebElement signInButton;

    @FindBy(css = ".inline-notice__content")
    WebElement errorMessage;

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Wait for login page to load and verify title
    public boolean isLoginPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        return driver.getTitle().equals("Sign in or Register | eBay");
    }

    // Enter email
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    // Click Continue after entering email
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    // Enter password
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click Sign In
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    // Get error message (if any)
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
