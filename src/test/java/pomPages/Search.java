package pomPages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;



public class Search {
	WebDriver driver;
    WebDriverWait wait;
    
    @FindBy(xpath="//input[@placeholder='Search for anything']")
    WebElement searchBox;
    @FindBy(xpath="(//span[@class='suggestion-text']//span)[3]")
    WebElement option;
    @FindBy(xpath="//div[@id='mainContent']")
    WebElement pageContent;
    
    public Search(WebDriver driver) {
    	this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
	}
    
    
    public String performSearch(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.click();

        try {
            wait.until(ExpectedConditions.visibilityOf(option));
            return option.getText(); // returns dropdown suggestion text if visible
        } catch (Exception e) {
            return null; // dropdown didn't appear
        }
    }
    
	public void clickSearch() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(option));
        option.click();
        
    }
	
	public String searchPageTitleCheck()  throws Exception {

        System.out.println("Switched to new page: " + driver.getTitle());
        return driver.getTitle();
        
	}
	
	public void searchProduct(String keyword) {
		performSearch(keyword);
		searchBox.sendKeys(Keys.ENTER);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(pageContent));
	}
}
