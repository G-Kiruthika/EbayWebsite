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

    /**
     * Enter email in the login page.
     * @param email Email address to enter
     */
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    /**
     * Click the Continue button after entering email.
     */
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    /**
     * Enter password in the login page.
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Click the Sign In button after entering password.
     */
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    /**
     * Get the error message displayed on login failure.
     * @return Error message text
     */
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
}
