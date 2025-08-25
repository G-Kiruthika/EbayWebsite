package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".srp-results .s-item a.s-item__link")
    List<WebElement> productLinks;

    @FindBy(id = "atcRedesignId_btn")
    WebElement addToCartButton;

    @FindBy(css = ".cart-bucket-lineitem .cart-bucket-lineitem-title")
    List<WebElement> cartItems;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem")
    List<WebElement> cartItemRows;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem-title")
    WebElement cartItemTitle;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem-quantity")
    WebElement cartItemQuantity;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem-price")
    WebElement cartItemPrice;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem-remove")
    WebElement removeItemButton;

    @FindBy(css = ".cart-bucket .cart-bucket-lineitem-confirmation")
    WebElement confirmationMessage;

    @FindBy(id = "gh-cart-n")
    WebElement cartCount;

    @FindBy(id = "gh-cart-i")
    WebElement cartIcon;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Select product by index from search results
    public void selectProduct(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        if (index < productLinks.size()) {
            productLinks.get(index).click();
        }
    }

    // Click Add to Cart button
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    // Get confirmation message after adding to cart
    public String getConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        return confirmationMessage.getText();
    }

    // Get cart count
    public int getCartCount() {
        wait.until(ExpectedConditions.visibilityOf(cartCount));
        String countText = cartCount.getText();
        try {
            return Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Click cart icon to view cart
    public void clickCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    // Get all items in cart
    public List<WebElement> getCartItems() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems;
    }
}
