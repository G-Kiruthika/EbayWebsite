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
    WebElement emailInput;

    @FindBy(id = "pass")
    WebElement passwordInput;

    @FindBy(id = "signin-continue-btn")
    WebElement continueButton;

    @FindBy(id = "sgnBt")
    WebElement signInButton;

    @FindBy(css = "div[role='alert']")
    WebElement errorAlert;

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
        PageFactory.initElements(driver, this);
    }

    // Wait for login page to load by checking title
    public void waitForLoginPageToLoad() {
        wait.until(ExpectedConditions.titleIs("Sign in or Register | eBay"));
    }

    // Verify email input is visible and enabled
    public boolean isEmailInputVisible() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput.isDisplayed() && emailInput.isEnabled();
    }

    // Focus on email input
    public void focusEmailInput() {
        wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        emailInput.click();
    }

    // Enter email
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    // Click Continue (if present)
    public void clickContinue() {
        if (continueButton.isDisplayed() && continueButton.isEnabled()) {
            continueButton.click();
        }
    }

    // Enter password
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    // Click Sign In
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    // Check for error alert
    public boolean isErrorAlertDisplayed() {
        try {
            return errorAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get error alert text
    public String getErrorAlertText() {
        if (isErrorAlertDisplayed()) {
            return errorAlert.getText();
        }
        return "";
    }
}
