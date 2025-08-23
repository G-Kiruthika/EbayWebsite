package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoginPage encapsulates all login-related elements and actions for eBay's login page.
 * This class follows the Page Object Model (POM) pattern for maintainability and reusability.
 *
 * Functional Knowledge Base Reference:
 * - Login page URL: https://signin.ebay.com/signin
 * - Email and password fields must be visible and interactive.
 * - Valid credentials redirect to home; invalid credentials show clear error messages.
 * - Error messages are immediate and field-specific.
 */
public class LoginPage { ... (truncated for brevity, but provide full code in actual context) ... }