package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookPage extends BasePage{
    @FindBy(css = ".BRpagecontainer.BRpage-visible ")
    private List<WebElement> pageImages;
    @FindBy(css = ".pagediv0 .BRpageimage")
    private WebElement coverImage;
    @FindBy(css = ".BRcurrentpage")
    private WebElement pageCounter;
    @FindBy(css = ".hflip")
    private WebElement flipPage;
    @FindBy(xpath = "//div[@class='logged-out-toolbar']//a[text()[contains(.,'Log in')]]")
    private WebElement logIn;
    @FindBy(css = "input[type='email']")
    private WebElement emailInput;
    @FindBy(css = "input[type='password']")
    private WebElement passwordInput;
    @FindBy(css = "input[name='submit-to-login']")
    private WebElement submitButton;
    private static final String regex = "-?\\d+";


    public BookPage(WebDriver givenDriver) {
        super(givenDriver);
    }
    public BookPage login() {
        driver.navigate().to("https://archive.org");
        WebElement shadowLoginBtn = driver.findElement(By.tagName("app-root")).getShadowRoot()
                .findElement(By.cssSelector("ia-topnav")).getShadowRoot()
                .findElement(By.cssSelector("primary-nav")).getShadowRoot()
                .findElement(By.cssSelector("login-button")).getShadowRoot()
                .findElement(By.cssSelector("[href='/account/login']"));
        shadowLoginBtn.click();
        emailInput.sendKeys(System.getProperty("email"));
        passwordInput.sendKeys(System.getProperty("password"));
        findElement(submitButton).click();
        return this;
    }

    public WebElement returnBookButton() {
        return driver.findElement(By.tagName("ia-book-actions")).getShadowRoot()
                .findElement(By.cssSelector("collapsible-action-group")).getShadowRoot()
                .findElement(By.cssSelector(".ia-button.danger.initial"));
    }
    public BookPage viewOrBorrowBook(String bookURL, String choice) {
        switch (choice) {
            case "borrow" :
              driver.navigate().to(bookURL);
              borrowButton().click();
                emailInput.sendKeys(System.getProperty("email"));
                passwordInput.sendKeys(System.getProperty("password"));
                findElement(submitButton).click();
            case "view" :
                driver.get(bookURL);
            default:
                return this;
        }
    }
    public BookPage clickNextPage() {
        flipPage.click();
        return this;
    }
    public BookPage saveImage(WebElement element, String fileName, String directory) {
        try {
            String logoSRC = findElement(element).getAttribute("src");
            Reporter.log("logoSRC: " + logoSRC, true);
            URI imageURL = new URI(logoSRC);
            BufferedImage saveImage = ImageIO.read(imageURL.toURL());

            ImageIO.write(saveImage, "png", new File("saved/"+directory+"/"+fileName+".png"));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return this;
    }
    public void saveCoverImage(String directory) {
        saveImage(coverImage, "coverImage", directory);
    }
    public void savePages(String directory) {
        saveCoverImage(directory);
        clickNextPage();
        int current;
        int total = Integer.parseInt(getCurrentPage("total"));
        int i =  1;
        while (i < total) {

            current =  Integer.parseInt(getCurrentPage("current"));

            Reporter.log("i: " + i, true);
            WebElement firstImg = getImage(Integer.valueOf(i).toString());
            saveImage(firstImg, Integer.valueOf(i).toString(), directory);
            WebElement secondImg = getImage(Integer.valueOf(i+1).toString());
            saveImage(secondImg, Integer.valueOf(i+1).toString(), directory);
            i = current + 2;
            clickNextPage();

        }
        returnBookButton().click();
        actions.pause(Duration.ofSeconds(3)).perform();
    }
    public WebElement getImage(String element) {
        WebElement image = driver.findElement(By.cssSelector(".pagediv"+element+" .BRpageimage"));
        return wait.until(ExpectedConditions.visibilityOf(image));
    }
    public String buttonText() {
        return borrowButton().getText();
    }
    public WebElement borrowButton() {
        return driver.findElement(By.tagName("ia-book-actions")).getShadowRoot()
                .findElement(By.cssSelector("collapsible-action-group")).getShadowRoot()
                .findElement(By.cssSelector("button.ia-button.primary.initial"));
    }
    public String getCurrentPage(String choice) {
        List<String> finds = new ArrayList<>();
        String pageCounts = findElement(pageCounter).getText();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageCounts);
        while(matcher.find()) {
            finds.add(matcher.group());
        }
        return switch (choice) {
            case "current" -> finds.get(0);
            case "total" -> finds.get(1);
            default -> matcher.group();
        };

    }

    public BookPage pause(int seconds) {
        actions.pause(Duration.ofSeconds(seconds)).perform();
        return this;
    }
    public BookPage contextMenuSaveImage() {
        String imgSRC = findElement(coverImage).getAttribute("src");
        Reporter.log("imgSRC: " + imgSRC, true);
        driver.get(imgSRC);
        contextClick(find(By.tagName("img")));
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        return this;
    }


}
