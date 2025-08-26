package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 10;

    @FindBy(xpath = "//input[@id='userid']")
    private WebElement emailField;

    @FindBy(xpath = "//button[@id='signin-continue-btn']")
    private WebElement continueButton;

    @FindBy(xpath = "//input[@id='pass']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='sgnBt']")
    private WebElement signInButton;

    @FindBy(xpath = "//p[@id='signin-error-msg']")
    private WebElement errorMsg;

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void verifyEmailVisibility() {
        wait.until(ExpectedConditions.visibilityOf(emailField));
    }

    public void verifyEmailClickability() {
        wait.until(ExpectedConditions.elementToBeClickable(emailField));
    }

    public void enterEmail(String email) {
        verifyEmailVisibility();
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void verifyContinueBtnVisibility() {
        wait.until(ExpectedConditions.visibilityOf(continueButton));
    }

    public void verifyContinueBtnClickability() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
    }

    public void clickContinue() {
        verifyContinueBtnClickability();
        continueButton.click();
    }

    public void verifyPasswordVisibility() {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
    }

    public void verifyPasswordClickability() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
    }

    public void enterPassword(String password) {
        verifyPasswordVisibility();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void verifySignInBtnVisibility() {
        wait.until(ExpectedConditions.visibilityOf(signInButton));
    }

    public void verifySignInBtnClickability() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
    }

    public void clickSignIn() {
        verifySignInBtnClickability();
        signInButton.click();
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMsg)).getText();
        } catch (Exception e) {
            return null;
        }
    }

    // Helper for positive login flow
    public void loginToAccount(String email, String password) {
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSignIn();
    }
}
