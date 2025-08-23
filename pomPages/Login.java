package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Model for eBay Login Page
 * Traceability: Covers requirements from eBay Functional Knowledge Base - Login Page
 *   - Page loads reliably with correct title
 *   - Email input is visible and interactive
 *   - Valid credentials log in and redirect to home
 *   - Invalid credentials trigger clear errors
 *   - All visible form errors are clear and related to the input field with the issue
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String PAGE_TITLE = "Sign in or Register | eBay";
    private static final String LOGIN_URL = "https://signin.ebay.com/signin";

    // Locators for login elements
    @FindBy(id = "userid")
    private WebElement emailInput;

    @FindBy(id = "signin-continue-btn")
    private WebElement continueButton;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(id = "sgnBt")
    private WebElement signInButton;

    // Error messages
    @FindBy(css = "div[role='alert']")
    private WebElement alertErrorMessage;

    @FindBy(css = "#userid + .inline-notice__content")
    private WebElement emailErrorMessage;

    @FindBy(css = "#pass + .inline-notice__content")
    private WebElement passwordErrorMessage;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Loads the eBay login page and waits for the email input to be visible.
     * Requirement: Login page loads reliably with correct title and visible email input.
     */
    public void loadLoginPage() {
        driver.get(LOGIN_URL);
        wait.until(ExpectedConditions.titleContains("eBay"));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
    }

    /**
     * Enters the email/username into the email input field.
     * @param email The email address to enter
     * Requirement: Email input is visible and interactive
     */
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Clicks the Continue button after entering email.
     * Requirement: Continue to password step
     */
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    /**
     * Enters the password into the password input field.
     * @param password The password to enter
     * Requirement: Password input is visible and interactive
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Clicks the Sign In button to attempt login.
     * Requirement: Attempt login with provided credentials
     */
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
    }

    /**
     * Verifies if login was successful by checking for home page title or URL.
     * @return true if redirected to eBay home page, false otherwise
     * Requirement: Valid credentials log in and redirect to home
     */
    public boolean isLoginSuccessful() {
        // eBay home page title may vary; check for URL pattern as well
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("ebay.com"),
            ExpectedConditions.not(ExpectedConditions.urlContains("signin"))
        ));
        String currentUrl = driver.getCurrentUrl();
        return !currentUrl.contains("signin");
    }

    /**
     * Gets the error message displayed for invalid email or password.
     * @return The error message text, or null if not present
     * Requirement: Invalid credentials trigger clear errors
     */
    public String getAlertErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(alertErrorMessage));
            return alertErrorMessage.getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the inline error message for the email field.
     * @return The email error message text, or null if not present
     */
    public String getEmailErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
            return emailErrorMessage.getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the inline error message for the password field.
     * @return The password error message text, or null if not present
     */
    public String getPasswordErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordErrorMessage));
            return passwordErrorMessage.getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Utility: Checks if the email input is displayed and enabled.
     * @return true if email input is visible and enabled
     */
    public boolean isEmailInputReady() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            return emailInput.isDisplayed() && emailInput.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Utility: Checks if the password input is displayed and enabled.
     * @return true if password input is visible and enabled
     */
    public boolean isPasswordInputReady() {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            return passwordInput.isDisplayed() && passwordInput.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
