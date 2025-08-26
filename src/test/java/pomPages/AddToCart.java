package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddToCart {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 15;

    @FindBy(xpath = "//div[contains(@class,'vi-evo-row-gap')]//ul/li[2]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//button[contains(@aria-label,'Close')]" )
    private WebElement closeButton;

    @FindBy(xpath = "//div[@class='ux-section__item']//a")
    private WebElement seeCartButton;

    @FindBy(css = "div.gh-cart span")
    private WebElement goToCartButton;

    @FindBy(css = "div.gh-cart span span")
    private WebElement cartCount;

    @FindBy(css = "div.ux-icon ~ span")
    private WebElement addedToCartMsg;

    @FindBy(css = "div.top-section h1")
    private WebElement cartPageTitle;

    @FindBy(xpath = "//button[@class='gh-flyout__target gh-flyout__target--left']")
    private WebElement profileIcon;

    @FindBy(xpath = "//ul[@class='gh-identity-signed-in']//li[3]//a")
    private WebElement signOutButton;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public boolean isProductAddedMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(addedToCartMsg)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartCount() {
        try {
            String countText = wait.until(ExpectedConditions.visibilityOf(cartCount)).getText();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        closeButton.click();
        wait.until(ExpectedConditions.invisibilityOf(closeButton));
    }

    public void clickSeeInCart() {
        wait.until(ExpectedConditions.elementToBeClickable(seeCartButton));
        seeCartButton.click();
    }

    public boolean goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(goToCartButton));
        goToCartButton.click();
        try {
            wait.until(ExpectedConditions.visibilityOf(cartPageTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSignOut() {
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(profileIcon));
        actions.moveToElement(profileIcon).perform();
        wait.until(ExpectedConditions.visibilityOf(signOutButton));
        wait.until(ExpectedConditions.elementToBeClickable(signOutButton));
        signOutButton.click();
        // Wait for sign-in page to appear (handled in test assertion)
    }
}
