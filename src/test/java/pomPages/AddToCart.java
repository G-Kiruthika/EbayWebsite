package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    private By addToCartButton = By.cssSelector("div.vim.x-atc-action.overlay-placeholder.atcv3modalloading a"); // Add to Cart button
    private By closeButton = By.cssSelector("#mainContent > div.vim.d-vi-evo-region > div.vim.vi-evo-row-gap > ul > li:nth-child(2) > div.vim.x-atc-action.overlay-placeholder.atcv3modal > div > div.lightbox-dialog__window.lightbox-dialog__window--animate.keyboard-trap--active > div.lightbox-dialog__header > button");
    private By goToCartButton = By.cssSelector("div.gh-cart span"); // Cart icon
    private By cartCount = By.cssSelector("div.gh-cart span span"); // Cart count
    private By addedToCartMsg = By.cssSelector("div.ux-icon ~ span"); // Add to cart confirmation overlay
    private By cartPageTitle = By.cssSelector("div.top-section h1");
    
    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public boolean isProductAddedMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addedToCartMsg)).isDisplayed();
    }

    public String getCartCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartCount)).getText();
    }

    
    public void clickClose() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton));
    	driver.findElement(closeButton).click();
    }
    public boolean goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageTitle));
        return driver.findElement(cartPageTitle).isDisplayed();
    }
    
}
