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

    @FindBy(css = "ul.srp-results > li.s-item a.s-item__link")
    List<WebElement> productLinks;

    @FindBy(id = "atcRedesignId_btn")
    WebElement addToCartButton;

    @FindBy(css = "div#ADDON_0 div[class*='atc-layer__content']")
    WebElement addToCartConfirmation;

    @FindBy(css = "span#gh-cart-n")
    WebElement cartCount;

    @FindBy(id = "gh-cart-i")
    WebElement cartIcon;

    @FindBy(css = "div.cart-bucket-lineitem")
    List<WebElement> cartItems;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Select first product from search results
    public void selectFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        productLinks.get(0).click();
    }

    // Click Add to Cart button
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    // Get Add to Cart confirmation message
    public String getAddToCartConfirmation() {
        try {
            wait.until(ExpectedConditions.visibilityOf(addToCartConfirmation));
            return addToCartConfirmation.getText();
        } catch (Exception e) {
            return "";
        }
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

    // Go to cart page
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    // Get number of items in cart
    public int getCartItemsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems.size();
    }
}
