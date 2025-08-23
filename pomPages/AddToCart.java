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
 * AddCartPage.java
 * Page Object Model for eBay Add to Cart functionality.
 * Covers selecting a product from search results, opening product detail,
 * adding to cart, verifying confirmation, and checking cart count.
 * Traceable to eBay functional requirements and test cases 137, 138, 139.
 */
public class AddCartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for product items in search results (assumes standard eBay result grid)
    @FindBy(css = "ul.srp-results > li.s-item")
    private List<WebElement> searchResultItems;

    // Locator for product title link inside a search result item
    @FindBy(css = "ul.srp-results > li.s-item a.s-item__link")
    private List<WebElement> productLinks;

    // Locator for 'Add to cart' button on product detail page
    @FindBy(id = "atcRedesignId_btn")
    private WebElement addToCartButton;

    // Locator for confirmation message after adding to cart
    @FindBy(css = "#ADDON_0 .atc-layer__content .atc-layer__title" )
    private WebElement addToCartConfirmation;

    // Locator for cart icon count (header)
    @FindBy(css = "#gh-cart-n")
    private WebElement cartCount;

    // Locator for cart icon/button (to view cart)
    @FindBy(id = "gh-cart-i")
    private WebElement cartIcon;

    // Locator for cart items in the cart page
    @FindBy(css = "div.cart-bucket-lineitem")
    private List<WebElement> cartItems;

    public AddCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Selects a product from the search results by index (0-based).
     * Opens the product detail page in the same tab.
     * @param index Index of the product in the search results
     */
    public void selectProductFromResults(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResultItems));
        if (productLinks.size() > index) {
            productLinks.get(index).click();
        } else {
            throw new RuntimeException("Product index out of bounds in search results.");
        }
    }

    /**
     * Clicks the 'Add to Cart' button on the product detail page.
     * Waits for the button to be clickable.
     */
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    /**
     * Verifies the confirmation message after adding to cart.
     * @return The confirmation message text
     */
    public String getAddToCartConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(addToCartConfirmation));
        return addToCartConfirmation.getText();
    }

    /**
     * Gets the current cart item count from the cart icon in the header.
     * @return The number of items in the cart as integer
     */
    public int getCartItemCount() {
        wait.until(ExpectedConditions.visibilityOf(cartCount));
        String countText = cartCount.getText().trim();
        try {
            return Integer.parseInt(countText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Clicks the cart icon to navigate to the cart page.
     */
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    /**
     * Returns the number of items currently listed in the cart page.
     * @return Number of cart items
     */
    public int getCartItemsCountInCartPage() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems.size();
    }

    /**
     * Checks if a product with the given title is present in the cart page.
     * @param productTitle The title of the product to check
     * @return true if present, false otherwise
     */
    public boolean isProductInCart(String productTitle) {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        for (WebElement item : cartItems) {
            if (item.getText().contains(productTitle)) {
                return true;
            }
        }
        return false;
    }
}
