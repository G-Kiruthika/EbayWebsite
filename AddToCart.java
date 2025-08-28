package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddToCart {
    WebDriver driver;
    By addToCartButton = By.id("addToCartBtn");
    By cartIcon = By.id("cartIcon");

    public AddToCart(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public boolean isProductInCart() {
        return driver.findElement(cartIcon).isDisplayed();
    }
}
