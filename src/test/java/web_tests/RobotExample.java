package web_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by garth.combrinck on 2017/06/15.
 * http://www.guru99.com/using-robot-api-selenium.html
 */
public class RobotExample {
    public static WebDriver driver;
    @BeforeMethod
    public void setup(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/java/lib/chromedriver_2.29.exe");
        driver = new ChromeDriver();
    }
    @Test
    public void robot_example() throws AWTException, InterruptedException {
        driver.get("http://spreadsheetpage.com/index.php/file/C35/P10/"); // sample url
        driver.findElement(By.xpath(".//a[@href=contains(text(),'array formula calendar.xls')]")).click();
        Robot robot = new Robot();  // Robot class throws AWT Exception
        Thread.sleep(2000); // Thread.sleep throws InterruptedException
        robot.keyPress(KeyEvent.VK_DOWN);  // press arrow down key of keyboard to navigate and select Save radio button

        Thread.sleep(2000);  // sleep has only been used to showcase each event separately
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        // press enter key of keyboard to perform above selected action
    }
}