import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.*;

public class SwipeTest {

    public static WebDriver driver;
    Set<String> browsers;
    Swipe swipe;

    ChromeOptions options = new ChromeOptions();
    Map <String, Integer> prefs = new HashMap<>();

    @BeforeSuite
    //setup the browser
    public void InitialSetUp() throws IOException {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            if ("true".equalsIgnoreCase(System.getProperty("headless"))) {
                options.addArguments("headless");
            }

            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            //changed to run on mac
            options.addArguments("--auth-server-whitelist=*amgen.com");
            //headless chrome window size is important, because default is 800x600 - hchoudha
            options.addArguments("window-size=1920,1080");
            WebDriverManager.chromedriver().setup();
            //for debug
            // options.addArguments("--remote-debugging-port=9222");

        //Pass the argument 1 to allow and 2 to block
        //add key and value to map as follow to switch off browser notification
        prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs",prefs);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        }

    @BeforeClass
    public void beforeClass() {
        swipe = new Swipe(driver);
    }
    @Test
    public void swipeRight() throws InterruptedException, IOException {
        driver.get("https://www.tinder.com");
        String tinderLoginTitle = driver.getWindowHandle();
        swipe.iAcceptBtnClick();
        swipe.loginTopCornerBtnClick();
        swipe.loginWithFacebookClick();
        swipe.waitForFewSeconds(3000);
        browsers = driver.getWindowHandles();
        System.out.println("size is:" + browsers.size());
        for(String singleTab: browsers) {
            driver.switchTo().window(singleTab);
            System.out.println("title is:" + driver.getTitle());
            if(driver.getTitle().equals("Facebook")) {
                swipe.enterFacebookEmailId();
                swipe.enterFacebookPassword();
                swipe.facebookFormLoginBtnClick();
                driver.switchTo().window(tinderLoginTitle);
                //wait until modal appears ~10 seoncds
                swipe.waitForFewSeconds(10000);
                int i = 0;
                int count = 0;

                //once we get match tinder has modal popup . to avoid click on message button
                Boolean isMessagePresent =  swipe.isMessagePresentStaus();
                if(isMessagePresent) {
                    swipe.clickMessageButton();
                    swipe.waitForFewSeconds(2000);
                }

                do{
                    Boolean isPresentNotInterested =  swipe.notInteresetedBtnStatus();
                    Boolean isPresentSuperLikeCancel =  swipe.cancelSuperLikeBtnStatus();

                    //not interested button
                    if(isPresentNotInterested) {
                        swipe.notInteresetedBtnClick();
                        swipe.waitForFewSeconds(2000);

                        //superlike cancel button
                    } else if(isPresentSuperLikeCancel) {
                        swipe.cancelSuperLikeBtnClick();
                        swipe.waitForFewSeconds(2000);
                    }
                    else {
                        //like click button
                        swipe.likeBtnClick();
                        swipe.waitForFewSeconds(2000);
                        System.out.println("count is: " + count++);
                    }
                }while(i == 0);
            }
        }

    }

    //there is no clean up test because its infinite. Only clean up is use manually close all
}
