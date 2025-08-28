package pomPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CreateRepoPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "repository_name")
    WebElement repoNameInput;

    @FindBy(xpath = "//button[contains(text(),'Create repository')]")
    WebElement createRepoBtn;

    public CreateRepoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterRepoName(String name) {
        wait.until(ExpectedConditions.visibilityOf(repoNameInput));
        repoNameInput.clear();
        repoNameInput.sendKeys(name);
    }

    public void createRepoBtnClick() {
        wait.until(ExpectedConditions.elementToBeClickable(createRepoBtn));
        createRepoBtn.click();
    }
}
