package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for eBay Add to Cart Functionality
 * Covers: Product selection, add to cart, confirmation, cart count
 * Traceable to: Add to cart test cases, eBay functional KB
 */
public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".s-item__info .s-item__link")
    List<WebElement> productLinks;

    @FindBy(id = "atcRedesignId_btn")
    WebElement addToCartButton;

    @FindBy(css = ".cart-bucket-lineitem .BOLD")
    WebElement cartItemCount;

    @FindBy(css = ".atc-layer__content .atc-layer__title")
    WebElement addToCartConfirmation;

    @FindBy(id = "gh-cart-n")
    WebElement cartIconCount;

    @FindBy(id = "gh-cart-i")
    WebElement cartIcon;

    @FindBy(css = ".cart-bucket-lineitem")
    List<WebElement> cartItems;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void selectProduct(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        if (index < productLinks.size()) {
            productLinks.get(index).click();
        }
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public String getAddToCartConfirmation() {
        wait.until(ExpectedConditions.visibilityOf(addToCartConfirmation));
        return addToCartConfirmation.getText();
    }

    public int getCartCount() {
        wait.until(ExpectedConditions.visibilityOf(cartIconCount));
        String countText = cartIconCount.getText();
        try {
            return Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    public int getCartItemsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems.size();
    }
}
