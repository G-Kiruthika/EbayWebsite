package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCart {
    WebDriver driver;
    WebDriverWait wait;

    By addToCartButton = By.xpath("//div[contains(@class,'vi-evo-row-gap')]//ul/li[2]");
    By closeButton = By.xpath("//button[contains(@aria-label,'Close')]" );
    By seeCartButton = By.xpath("//div[@class='ux-section__item']//a");
    By goToCartButton = By.cssSelector("div.gh-cart span");
    By cartCount = By.cssSelector("div.gh-cart span span");
    By addedToCartMsg = By.cssSelector("div.ux-icon ~ span");
    By cartPageTitle = By.cssSelector("div.top-section h1");
    By profile = By.xpath("//button[@class='gh-flyout__target gh-flyout__target--left']");
    By signOutButton = By.xpath("//ul[@class='gh-identity-signed-in']//li[3]//a");

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCart() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        btn.click();
    }

    public boolean isProductAddedMessageDisplayed() {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(addedToCartMsg));
            return msg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCartCount() {
        try {
            WebElement count = wait.until(ExpectedConditions.visibilityOfElementLocated(cartCount));
            return count.getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void clickClose() {
        WebElement close = wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        close.click();
    }

    public void clickSeeInCart() {
        WebElement seeCart = wait.until(ExpectedConditions.elementToBeClickable(seeCartButton));
        seeCart.click();
    }

    public boolean goToCart() {
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(goToCartButton));
        cartIcon.click();
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageTitle));
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSignOut() {
        Actions actions = new Actions(driver);
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(profile));
        actions.moveToElement(profileIcon).perform();
        WebElement signOut = wait.until(ExpectedConditions.visibilityOfElementLocated(signOutButton));
        wait.until(ExpectedConditions.elementToBeClickable(signOut)).click();
    }
}
