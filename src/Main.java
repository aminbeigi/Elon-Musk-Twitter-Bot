import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.*;

/*
* This script requires input of:
* username (LINE 54)
* password (LINE 59)
* image directory (LINE 111, 112)
*/

public class Main {

    public static WebDriver driver = null;
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        // open twitter.com
        driver.manage().window().maximize(); //maximise screan
        driver.navigate().to("https://twitter.com/elonmusk");
        String title = driver.getTitle(); //change browser title

        // search for either log in layout and click on log in icon
        Actions action = new Actions(driver);
        String LogInButton = "//*[@id=\"react-root\"]/div/div/div[2]/header/div[2]/div[1]/div/div[2]/div[1]/div[1]";
        String AltLogInButton = "//*[@id=\"react-root\"]/div/div/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/a/div/span/span";
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LogInButton)));
            driver.findElement(By.xpath(LogInButton)).click();
        }
        catch(Exception e) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(AltLogInButton)));
            driver.findElement(By.xpath(AltLogInButton)).click();
        }

        // signing into twitter
        // enter username
        String usernamePath = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/form/div/div[1]/label/div/div[2]/div/input";
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(usernamePath)));
        WebElement username = driver.findElement(By.xpath(usernamePath));
        username.sendKeys("@USERNAME");
        // enter password
        String passwordPath = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/form/div/div[2]/label/div/div[2]/div/input";
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordPath)));
        WebElement password = driver.findElement(By.xpath(passwordPath));
        password.sendKeys("PASSWORD");
        // click on login
        String LogInButton2 = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/form/div/div[3]";
        driver.findElement(By.xpath(LogInButton2)).click();

        String OldElonText= "";
        while (true) {
            // format time of last tweet written
            driver.navigate().refresh();
            String SinceLastTweetPath = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div/div[2]/div/div/div/article/div/div[2]/div[2]/div[1]/div/div/div[1]/a/time";
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SinceLastTweetPath)));
            String TweetStr = driver.findElement(By.xpath(SinceLastTweetPath)).getText();
            if (TweetStr.substring(TweetStr.length() - 1).equals("d")) {
                TweetStr = TweetStr.replace("d", " days");
                System.out.println("Elon Musk last tweeted "+ TweetStr + " ago.");
            } else if (TweetStr.substring(TweetStr.length() - 1).equals("h")) {
                TweetStr = TweetStr.replace("h", " hours");
                System.out.println("Elon Musk last tweeted "+ TweetStr + " ago.");
            } else if (TweetStr.substring(TweetStr.length() - 1).equals("m")) {
                TweetStr = TweetStr.replace("m", " minutes");
                System.out.println("Elon Musk last tweeted "+ TweetStr + " ago.");
            }

            // compare saved text with current page's text
            String ElonText = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div/div[1]/div/div/div/article/div/div[2]/div[2]/div[2]/div[1]/div/span";
            if (ElonText != OldElonText) {
                System.out.println("Elon Musk has tweeted something new!");
                OldElonText = ElonText;
                ;
            } else {
                System.out.println("No new Elon Musk Tweets :(");
                Thread.sleep(60000);
                continue;
            }

            // click on reply icon (only loops once)
            try {
                String replyTweetPath = "//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[2]/section/div/div/div/div[2]/div/div/div/article/div/div[2]/div[2]/div[2]/div[3]/div[1]/div";
                new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(replyTweetPath)));
                driver.findElement(By.xpath(replyTweetPath)).click();
            }
            catch(Exception e) {
            }

            // click on the upload image button
            String uploadImagePath = "//*[@id=\"react-root\"]/div/div/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[3]/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[1]/div[1]";
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uploadImagePath)));
            driver.findElement(By.xpath(uploadImagePath)).click();

            // uploading image (ElonMeme.png and ElonSmoke.png)
            Thread.sleep(1000);
            // insert image directories
            String filePath = "FILEPATH"; 
            String filePath2 = "FILEPATH2";

            // randomly select between two images
            double d = Math.random() * 100;
            if (d >= 50) {
                StringSelection file = new StringSelection(filePath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);
            } else {
                StringSelection file = new StringSelection(filePath2);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);
            }

            // simulate Ctrl+C then Ctrl+V on keyboard into image search box
            try {
                Robot robot = new Robot();
                robot.keyPress(VK_CONTROL);
                robot.keyPress(VK_V);
                robot.keyRelease(VK_V);
                robot.keyRelease(VK_CONTROL);
                robot.keyPress(VK_ENTER);
                robot.keyRelease(VK_ENTER);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            String replyButton = "//*[@id=\"react-root\"]/div/div/div[1]/div[2]/div/div/div/div[2]/div[2]/div/div[3]/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/span/span";
            driver.findElement(By.xpath(replyButton)).click();
            System.out.println("Successfully uploaded image :D");
            // wait 3 seconds before refreshing            
            Thread.sleep(3000);
        }
    }
}
 
