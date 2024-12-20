package com.fazli.telefonuch.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HeaderComponentTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void headerComponentHEADERTest(){
        WebElement header = driver.findElement(By.tagName("header"));
        Assert.assertTrue(header.isDisplayed() , "Header is not displayed");
    }

    @Test
    public void headerComponentICONTest(){
        WebElement icon = driver.findElement(By.id("Phonebook-Icon"));

        Assert.assertNotNull(icon);
        Assert.assertTrue(icon.isDisplayed());
        Assert.assertTrue(icon.getAttribute("src").contains("assets/icons8-phonebook-49.png") ,
                "Header Icon source is not correct");

    }

    @Test
    public void headerComponentTITLETest(){
        WebElement title = driver.findElement(By.tagName("span"));

        Assert.assertNotNull(title);
        Assert.assertEquals(title.getText(), "DE-TELEFONBUCH" , "Header Title is not correct");

    }

    @Test
    public void headerComponentPERSONBUTTONTest(){
        // Existenz
        WebElement personButton = driver.findElement(By.xpath(
                "//button[contains(text(),'Person Suche')]"));

        Assert.assertTrue(personButton.isDisplayed() , "Person Suche Button is not displayed");

        // Navigation
        personButton.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/person"));
        driver.navigate().back();
    }

    @Test
    public void headerComponentFIRMABUTTONTest(){
        //Existenz
        WebElement firmaSucheButton = driver.findElement(By.xpath(
                "//button[contains(text() , 'Firma Suche')]"));
        Assert.assertTrue(firmaSucheButton.isDisplayed() , "Firma Suche Button is not displayed");

        // Navigation
        firmaSucheButton.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/firma"));
        driver.navigate().back();
    }



}
