package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePageTest {

    WebDriver driver;

    @BeforeTest
    private void init() {
        // initiate browser
        System.setProperty("webdriver.chorme.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        // go to home page
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void checkElement() {
        // check h1 element
        Assert.assertEquals(driver.findElement(By.cssSelector("header h1")).getText(),"CURA Healthcare Service");
        Assert.assertEquals(Color.fromString(driver.findElement(By.cssSelector("header h1")).getCssValue("color")).asHex(), "#ffffff");

        // check h3 element
        Assert.assertEquals(driver.findElement(By.cssSelector("header h3")).getText(),"We Care About Your Health");
        Assert.assertEquals(Color.fromString(driver.findElement(By.cssSelector("header h3")).getCssValue("color")).asHex(), "#4fb6e7");

        // check button
        Assert.assertEquals(driver.findElement(By.id("btn-make-appointment")).getText(),"Make Appointment");
        Assert.assertEquals(driver.findElement(By.id("btn-make-appointment")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");

        // check toggle view
        Assert.assertEquals(driver.findElement(By.id("menu-toggle")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");
    }

    @AfterTest
    private void closeBrowser() {
        driver.quit();
    }

}
