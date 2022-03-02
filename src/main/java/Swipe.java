import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Swipe {

    public static final String userPropertiesFile = "user.properties";
    public static final String userPropertiesFileUrl = Swipe.class.getClassLoader().getResource(userPropertiesFile).getPath();
    public static final Properties userProperties = new Properties();
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//button[contains(@data-testid,'privacyPreferencesAccept')]")
    WebElement iAcceptBtn;


    //tinder topr right corner login button
    @FindBy(xpath = "//a[contains(@data-testid,'appLoginBtn')]")
    WebElement loginBtnTopCorner;

    //login with facebookt button
    @FindBy(xpath = "//button[contains(@aria-label,'Log in with Facebook')]")
    WebElement loginWithFacebookBtn;

    //facebook email id
    @FindBy(id = "email")
    WebElement emailInput;


    //button[contains(@data-testid,'cancel')]


    //facebook passrod
    @FindBy(id = "pass")
    WebElement passwordInput;

    //facebook form login button id
    @FindBy(name = "login")
    WebElement fbLoginbtn;

    //allow location button needs first time
    @FindBy(xpath = "//button[contains(@aria-label,'Allow')]")
    WebElement allowBtn;

    //enable button
    @FindBy(xpath = "//button[contains(@aria-label,'Enable')]")
    WebElement enableBtn;

    //like button
    @FindBy(xpath = "//button[contains(@data-testid,'gamepadLike')]")
    WebElement likeBtn;


    public Swipe(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, 5);
    }

    //iaccept button click method
    public void iAcceptBtnClick() {
        wait.until(ExpectedConditions.visibilityOf(iAcceptBtn));
        iAcceptBtn.click();
    }

    //top corner right login button click method
    public void loginTopCornerBtnClick() {
        wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.visibilityOf(loginBtnTopCorner));
        loginBtnTopCorner.click();
    }

    //login with facebook button click method
    public void loginWithFacebookClick() {
        wait.until(ExpectedConditions.visibilityOf(loginWithFacebookBtn));
        loginWithFacebookBtn.click();
    }

    //facebook for login button
    public void facebookFormLoginBtnClick() {
        wait.until(ExpectedConditions.visibilityOf(fbLoginbtn));
        fbLoginbtn.click();
    }


    //click like button
    public void likeBtnClick() {
        wait.until(ExpectedConditions.visibilityOf(likeBtn));
        likeBtn.click();
    }

    public void waitForFewSeconds(int secondsWait) throws InterruptedException {
        Thread.sleep(secondsWait);
    }


    //Method: get the user email
    public static String getUserEmailName() throws IOException {
        InputStream inputStream = null;
        String email = null;
        try {
            inputStream = new FileInputStream(userPropertiesFileUrl);
            if (inputStream != null) {
                userProperties.load(inputStream);
                email = userProperties.getProperty("email");
            } else {
                throw new FileNotFoundException("property file '" + userPropertiesFile + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            inputStream.close();
        }

        return email;
    }

    //Method: get the user password
    public static String getUserPassword() throws IOException {
        InputStream inputStream = null;
        String password = null;
        try {
            inputStream = new FileInputStream(userPropertiesFileUrl);
            if (inputStream != null) {
                userProperties.load(inputStream);
                password = userProperties.getProperty("password");
            } else {
                throw new FileNotFoundException("property file '" + userPropertiesFile + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            inputStream.close();
        }

        return password;
    }

    //enter facebook email id
    public void enterFacebookEmailId() throws IOException {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        String fbEmailId = getUserEmailName();
        emailInput.sendKeys(fbEmailId);
    }


    //enter password
    public void enterFacebookPassword() throws IOException {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        String fbPassword = getUserPassword();
        passwordInput.sendKeys(fbPassword);
    }

    //not interested button status
    public boolean notInteresetedBtnStatus() {
        Boolean isPresent = driver.findElements(By.xpath("//button[contains(@data-testid,'cancel')]")).size() > 0;
        return isPresent;
    }

    //method to click not interested button
    public void notInteresetedBtnClick() {
        WebElement notInterested = driver.findElement(By.xpath("//button[contains(@data-testid,'cancel')]"));
        notInterested.click();
    }


    //method superlike modal button status
    public boolean cancelSuperLikeBtnStatus() {
        Boolean isPresent = driver.findElements(By.xpath("//*[@id='s-225515700']/div/div/button[2]")).size() > 0;
        return isPresent;
    }


    //method superlike  click not interested button
    public void cancelSuperLikeBtnClick() {
        WebElement notIntegersted = driver.findElement(By.xpath("//*[@id='s-225515700']/div/div/button[2]"));
        notIntegersted.click();
    }



    //method message button present
    public boolean isMessagePresentStaus() {
        Boolean isMessagePresent = driver.findElements(By.xpath("//button[contains(text(),'Message')]")).size() > 0;
        return isMessagePresent;
    }


    //method to click  message button
    public void clickMessageButton() {
        WebElement messageBtn = driver.findElement(By.xpath("//button[contains(text(),'Message')]"));
        messageBtn.click();
    }

}
