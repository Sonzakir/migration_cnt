package com.fazli.telefonuch.selenium;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomeComponentTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");
    }

    @AfterClass
    public void tearDown(){
       driver.quit();
    }

    @Test
    public void homeComponentTitleTest(){

        // Title
        WebElement title = driver.findElement(By.tagName("h1"));

        Assert.assertEquals(title.getText() , "Willkommen zum Deutschen Telefonbuch") ;

    }

    @Test
    public void homeComponentImageTest(){
        // Telefonbuch Icon
        WebElement image = driver.findElement(By.id("Homepage-Image"));

        Assert.assertTrue(image.isDisplayed() , "Homepage Image is not displayed") ;
        Assert.assertNotNull(image.getAttribute("src"));
        Assert.assertTrue(image.getAttribute("src").contains("assets/phonebook.jpg"),
                "Homepage Image source is not correct.");

    }

    @Test
    public void homeComponentTextTest(){
        // Erste
        WebElement paragraph1 =driver.findElement(By.xpath("//p[1]"));
        // Zweite
        WebElement paragraph2 =driver.findElement(By.xpath("//p[2]"));
        // Dritte
        WebElement paragraph3 =driver.findElement(By.xpath("//p[3]"));

        Assert.assertTrue(paragraph1.isDisplayed() , "Paragraph1 is not displayed") ;
        Assert.assertTrue(paragraph1.getText().contains("Das deutsche Telefonbuch ist eine umfangreiche"),
                "Paragraph1 text is not correct.");

        Assert.assertTrue(paragraph2.isDisplayed() , "Paragraph2 is not displayed") ;
        Assert.assertTrue(paragraph2.getText().contains("nach Personen zu suchen, sie hinzufügen oder zu löschen") ,
                "Paragraph2 text is not correct.");

        Assert.assertTrue(paragraph3.isDisplayed() , "Paragraph3 is not displayed") ;
        Assert.assertTrue(paragraph3.getText().contains("Firmensuchen durchzuführen"),
                "Paragraph3 text is not correct.");
    }

    @Test
    public void homeComponentPersonHomePageButtonTest(){

        // Existenz
        WebElement personHomePageButton = driver.findElement(By.xpath(
                "//button[contains( text() , 'Personen Homepage')]"));

        Assert.assertTrue(personHomePageButton.isDisplayed() , "Person homepage button is not displayed") ;


        // Navigation
        personHomePageButton.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/person") ,
                "Person homepage button navigation is incorrect.");
        driver.navigate().back();

    }

    @Test
    public void homeComponentFirmaHomePageButtonTest(){

        // Existenz
        WebElement firmaHomePageButton = driver.findElement(By.xpath(
                "//button[ contains( text() , 'Firmen Homepage')] "));

        Assert.assertTrue(firmaHomePageButton.isDisplayed() , "Firmen Homepage button is not displayed") ;

        // Navigation
        firmaHomePageButton.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/firma") ,
                "Firmen Homepage button navigation is incorrect.");
        driver.navigate().back();

    }






}

