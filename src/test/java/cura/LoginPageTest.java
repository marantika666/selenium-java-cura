package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginPageTest {

    WebDriver driver;

    @BeforeTest
    private void init() {
        // initiate browser
        System.setProperty("webdriver.chromedriver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        // go to home page
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();

        // go to login page
        driver.findElement(By.id("btn-make-appointment")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/profile.php#login");
    }

    @Test (priority = 0)
    public void checkElement() {
        // check h2 element
        Assert.assertEquals(driver.findElement(By.cssSelector("section h2")).getText(), "Login");

        // check p element
        Assert.assertEquals(driver.findElement(By.cssSelector("section p")).getText(), "Please login to make appointment.");

        // check text box element
        Assert.assertEquals(driver.findElement(By.id("txt-username")).getDomAttribute("placeholder"), "Username");
        Assert.assertEquals(driver.findElement(By.id("txt-password")).getDomAttribute("placeholder"), "Password");
    }

    @Test (priority = 1)
    public void loginWithNullValues() {
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 2)
    public void loginWithNullUsername() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 3)
    public void loginWithNullPassword() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("John Doe");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 4)
    public void loginWithWrongValues() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("Marantika Rhama Putra");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("Marantika Rhama Putra");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 5)
    public void loginWithWrongUsername() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("Marantika Rhama Putra");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 6)
    public void loginWithWrongPassword() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("John Doe");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("Marantika Rhama Putra");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 7)
    public void loginWithCorrectValues() {
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("John Doe");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @AfterTest
    private void closeBrowser() {
        driver.quit();
    }

}
