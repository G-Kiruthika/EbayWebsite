package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "atcRedesignId_btn")
    WebElement addToCartButton;

    @FindBy(css = ".atc-layer__content .atc-layer__title, .atc-confirmation__message")
    WebElement addToCartConfirmation;

    @FindBy(id = "gh-cart-n")
    WebElement cartCount;

    @FindBy(id = "gh-cart-i")
    WebElement cartIcon;

    @FindBy(css = ".cart-bucket-lineitem")
    WebElement cartItemList;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    /**
     * Click the Add to Cart button on the product detail page.
     */
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    /**
     * Get the confirmation message after adding to cart.
     * @return Confirmation message text
     */
    public String getAddToCartConfirmation() {
        wait.until(ExpectedConditions.visibilityOf(addToCartConfirmation));
        return addToCartConfirmation.getText();
    }

    /**
     * Get the current cart item count displayed in the header.
     * @return Cart count as integer
     */
    public int getCartCount() {
        wait.until(ExpectedConditions.visibilityOf(cartCount));
        String countText = cartCount.getText();
        try {
            return Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Click the cart icon to go to the cart page.
     */
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    /**
     * Check if the cart item list is displayed.
     * @return true if displayed, false otherwise
     */
    public boolean isCartItemListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartItemList));
            return cartItemList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
