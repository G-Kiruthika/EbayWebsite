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

    // Locators for login page elements
    @FindBy(id = "userid")
    WebElement emailInput;

    @FindBy(id = "pass")
    WebElement passwordInput;

    @FindBy(id = "signin-continue-btn")
    WebElement continueButton;

    @FindBy(id = "sgnBt")
    WebElement signInButton;

    @FindBy(css = "div[role='alert']")
    WebElement errorMessage;

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Wait for the email input to be visible
    public void waitForEmailInput() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
    }

    // Enter email address
    public void enterEmail(String email) {
        waitForEmailInput();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    // Click Continue (if present)
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
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

    // Get error message (if any)
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Get page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Check if email input is focused
    public boolean isEmailInputFocused() {
        return emailInput.equals(driver.switchTo().activeElement());
    }

    // Check if email input is visible
    public boolean isEmailInputVisible() {
        return emailInput.isDisplayed();
    }
}
