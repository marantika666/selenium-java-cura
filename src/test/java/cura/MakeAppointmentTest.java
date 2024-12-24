package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class MakeAppointmentTest {

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

        // go to make appointment page
        driver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("John Doe");
        driver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @Test (priority = 0)
    public void checkElement() {
        // check h2 element
        Assert.assertEquals(driver.findElement(By.cssSelector("section h2")).getText(), "Make Appointment");

        // check dropdown element
        Select dropdownFacility = new Select(driver.findElement(By.xpath("//*[@id=\"combo_facility\"]")));
        List<WebElement> dropdownOption = dropdownFacility.getOptions();
        Assert.assertEquals(dropdownOption.get(0).getDomAttribute("value"), "Tokyo CURA Healthcare Center");
        Assert.assertEquals(dropdownOption.get(1).getDomAttribute("value"), "Hongkong CURA Healthcare Center");
        Assert.assertEquals(dropdownOption.get(2).getDomAttribute("value"), "Seoul CURA Healthcare Center");

        // check text area
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"txt_comment\"]")).getDomAttribute("placeholder"), "Comment");
    }

    @Test (priority = 1)
    public void makeAppointmentCorrectValues() {
        // dropdown
        Select dropdownFacility = new Select(driver.findElement(By.xpath("//*[@id=\"combo_facility\"]")));
        dropdownFacility.selectByValue("Hongkong CURA Healthcare Center");

        // check box
        driver.findElement(By.xpath("//*[@id=\"chk_hospotal_readmission\"]")).click();

        // radio button
        driver.findElement(By.xpath("//*[@id=\"radio_program_medicaid\"]")).click();

        // date picker
        driver.findElement(By.xpath("//*[@id=\"txt_visit_date\"]")).sendKeys("25/12/2024");

        // text area
        driver.findElement(By.xpath("//*[@id=\"txt_comment\"]")).sendKeys("Hello Miss, \n\nMy handsome face is growing day by day, is this normal? See you on that date to check it out.");

        // click button
        driver.findElement(By.xpath("//*[@id=\"btn-book-appointment\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/appointment.php#summary");
    }

    @Test (priority = 2, dependsOnMethods = {"makeAppointmentCorrectValues"})
    public void checkAppointmentSummary() {
        // check h2 element
        Assert.assertEquals(driver.findElement(By.cssSelector("section h2")).getText(), "Appointment Confirmation");

        // check p element
        Assert.assertEquals(driver.findElement(By.cssSelector("section p")).getText(), "Please be informed that your appointment has been booked as following:");

        // check facility
        Assert.assertEquals(driver.findElement(By.id("facility")).getText(), "Hongkong CURA Healthcare Center");

        // check readmission
        Assert.assertEquals(driver.findElement(By.id("hospital_readmission")).getText(), "Yes");

        // check healthcare program
        Assert.assertEquals(driver.findElement(By.id("program")).getText(), "Medicaid");

        // check visit date
        Assert.assertEquals(driver.findElement(By.id("visit_date")).getText(), "25/12/2024");

        // check comment
        Assert.assertEquals(driver.findElement(By.id("comment")).getText(), "Hello Miss, My handsome face is growing day by day, is this normal? See you on that date to check it out.");

        // go to homepage
        driver.findElement(By.xpath("//*[@id=\"summary\"]/div/div/div[7]/p/a")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/");
    }

    @AfterTest
    private void closeBrowser() {
        driver.close();
    }
}
