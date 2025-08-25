package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    private By addToCartButton = By.xpath("//div[contains(@class,'vi-evo-row-gap')]//ul/li[2]"); // Add to Cart button
    private By closeButton = By.xpath("//div[contains(@class,'lightbox-dialog__header')]//button[contains(@class,'lightbox-dialog__close')]");
    private By seeCartButton = By.xpath("//div[@class='ux-section__item']//a");
    private By goToCartButton = By.cssSelector("div.gh-cart span"); // Cart icon
    private By cartCount = By.cssSelector("div.gh-cart span span"); // Cart count
    private By addedToCartMsg = By.cssSelector("div.ux-icon ~ span"); // Add to cart confirmation overlay
    private By cartPageTitle = By.cssSelector("div.top-section h1");
    
    private By profile = By.xpath("//button[@class='gh-flyout__target gh-flyout__target--left']");
    private By signOutButton = By.xpath("//ul[@class='gh-identity-signed-in']//li[3]//a");
    
    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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

    
    public void clickClose() throws Exception {
    	wait.until(ExpectedConditions.elementToBeClickable(closeButton));
    	Thread.sleep(3000);
    	driver.findElement(closeButton).click();
    }
    public void clickSeeInCart()throws Exception{
    	wait.until(ExpectedConditions.elementToBeClickable(seeCartButton));
    	driver.findElement(seeCartButton).click();
    }
    public boolean goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageTitle));
        return driver.findElement(cartPageTitle).isDisplayed();
    }
    
    public void clickSignOut() throws Exception {
    	Actions actions = new Actions(driver);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(profile));
    	WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(profile));
    	actions.moveToElement(profileIcon).perform();
        System.out.println("Hovered over profile");
        
    	Thread.sleep(3000);
    	
    	wait.until(ExpectedConditions.elementToBeClickable(signOutButton));
    	System.out.println("Signed out");
    	driver.findElement(signOutButton).click();
    	Thread.sleep(4000);
    }
    
}
