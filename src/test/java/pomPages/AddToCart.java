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

    @FindBy(css = ".s-item__info.clearfix .s-item__title")
    List<WebElement> productTitles;

    @FindBy(css = "a.s-item__link")
    List<WebElement> productLinks;

    @FindBy(id = "atcRedesignId_btn")
    WebElement addToCartButton;

    @FindBy(css = ".atc-layer__content .atc-layer__title")
    WebElement addToCartConfirmation;

    @FindBy(id = "gh-cart-n")
    WebElement cartCount;

    @FindBy(id = "gh-cart-i")
    WebElement cartIcon;

    @FindBy(css = ".cart-bucket-lineitem")
    List<WebElement> cartItems;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    // Select product by index from search results
    public void selectProduct(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        if (productLinks.size() > index) {
            productLinks.get(index).click();
        }
    }

    // Click Add to Cart button
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    // Get Add to Cart confirmation message
    public String getAddToCartConfirmation() {
        wait.until(ExpectedConditions.visibilityOf(addToCartConfirmation));
        return addToCartConfirmation.getText();
    }

    // Get current cart count
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

    // Get number of items in cart
    public int getCartItemsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
        return cartItems.size();
    }
}
