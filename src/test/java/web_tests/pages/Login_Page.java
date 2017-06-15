package web_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by garth.combrinck on 2017/06/12.
 */
public class Login_Page {

    private static WebElement element = null;

    public static WebElement txtbx_UserName(WebDriver driver) {
        element = driver.findElement(By.id("log"));
        return element;
    }

    public static WebElement txtbx_Password(WebDriver driver) {
        element = driver.findElement(By.id("pwd"));
        return element;
    }

    public static WebElement btn_LogIn(WebDriver driver) {
        element = driver.findElement(By.id("login"));
        return element;
    }

    public static void Execute(WebDriver driver,String sUsername, String sPassword){
        Home_Page.lnk_MyAccount(driver).click();
        txtbx_UserName(driver).sendKeys(sUsername);
        txtbx_Password(driver).sendKeys(sPassword);
        btn_LogIn(driver).click();
    }
}