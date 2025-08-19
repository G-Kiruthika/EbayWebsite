package pomPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddToWishlist {
	
	WebDriver driver;
    WebDriverWait wait;
    
    @FindBy(xpath="//input[@placeholder='Search for anything']")
    WebElement searchBox;
    
    @FindBy(xpath="//div[@id='mainContent']")
    WebElement pageContent;
    
    @FindBy(xpath="//ul/li[contains(@class, 's-item')][1]//div[contains(@class, 's-item__title')]")
    WebElement productChosen;
    
    @FindBy(xpath="//div[@id='watchWrapperId']//span//button//span//span")
    WebElement addToWishlistBtn;
    

    public AddToWishlist(WebDriver driver) {
    	this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public String readAddToWishlistBtnText() {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowList = new ArrayList<>(windowHandles);

		// Switch to the new tab (second one)
		driver.switchTo().window(windowList.get(1));

		// the new tab
		System.out.println("Switched to new tab: " + driver.getTitle());
		return addToWishlistBtn.getText();
	}
	public void selectProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(pageContent));
		
		productChosen.click();
	}
	
	
	public void clickAddToWishListBtn() throws Exception {
		
		Thread.sleep(4000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToWishlistBtn);
		//click add to wishlist button
		addToWishlistBtn.click();
		Thread.sleep(3000);
		
	}

}
