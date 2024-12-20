package com.fazli.telefonuch.selenium;


import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PersonComponentTest {




    /*
    //TODO: nach der click, lädt die Ergebnisse in Tests nicht
    @Test
    public void personComponentSUCHEBARTest() throws InterruptedException {
        //WebElement vornameBar = driver.findElement(By.xpath("//input[@placeholder='Vorname']"));
        WebElement nachnameBar = driver.findElement(By.xpath("//input[@placeholder='Nachname']"));
        WebElement stadtBar = driver.findElement(By.xpath("//input[@placeholder='Stadt']"));
        WebElement bundeslandBar = driver.findElement(By.xpath("//input[@placeholder='Bundesland']"));
        WebElement sucheButton = driver.findElement(By.xpath("//button[contains(text(),'Suche')]"));

        // Existenz
        //Assert.assertTrue(vornameBar.isDisplayed() , "Vorname Bar is not displayed");
        Assert.assertTrue(nachnameBar.isDisplayed() , "Nachname Bar is not displayed");
        Assert.assertTrue(stadtBar.isDisplayed() , "Stadt Bar is not displayed");
        Assert.assertTrue(bundeslandBar.isDisplayed() , "Bundesland Bar is not displayed");
        Assert.assertTrue(sucheButton.isDisplayed() , "Suche Button is not displayed");

        // Suche Ausführen
        //vornameBar.sendKeys("NICHTEXISTIERENDE VORNAME");
        nachnameBar.sendKeys("NICHTEXISTIERENDE NACHNAME");
        sucheButton.click();

        Thread.sleep(3000);

        List<WebElement> result = driver.findElements(By.cssSelector("li.person-block"));
        Assert.assertEquals( 0 , result.size());



    }

     */
    private WebDriver driver;



    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/person");

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
/**
    @Test
    public void foa() throws InterruptedException {
        Page2 na = new Page2(driver);
        na.fülleFor("222222222");
        Thread.sleep(10000);

    }
    @Test
    public void hinzufügen() throws InterruptedException {
        Page2 ne = new Page2(driver);
        ne.fü("aaaa");
    }

    @Test
    public void trya(){
        PersonPage pg = new PersonPage();
        pg.fülleVorname("PersonVorname");
        pg.fülleNachname("PersonNachname");
        pg.suche();
    }

    */

    /*
    public void personComponentSUCHEBARTest2() throws InterruptedException {
        SuchleistePage page = new SuchleistePage(driver);

        page.fülleStadt("lkds");
        page.drückeAufPersonSuche();
        page.zeigtPersonSucheAn(); // assert
        page.suche();

        page.hatEregbisse(4);
        page.zeigtPersonMitVoranmeAn("heinz");



        WebElement vornameBar = driver.findElement(By.xpath("//input[@placeholder='Vorname']"));
        WebElement nachnameBar = driver.findElement(By.xpath("//input[@placeholder='Nachname']"));
        WebElement stadtBar = driver.findElement(By.xpath("//input[@placeholder='Stadt']"));
        WebElement bundeslandBar = driver.findElement(By.xpath("//input[@placeholder='Bundesland']"));
        WebElement sucheButton = driver.findElement(By.xpath("//button[contains(text(),'Suche')]"));

        // Existenz
        Assert.assertTrue(vornameBar.isDisplayed() , "Vorname Bar is not displayed");
        Assert.assertTrue(nachnameBar.isDisplayed() , "Nachname Bar is not displayed");
        Assert.assertTrue(stadtBar.isDisplayed() , "Stadt Bar is not displayed");
        Assert.assertTrue(bundeslandBar.isDisplayed() , "Bundesland Bar is not displayed");
        Assert.assertTrue(sucheButton.isDisplayed() , "Suche Button is not displayed");

        // Suche Ausführen
        vornameBar.sendKeys("NICHTEXISTIERENDE VORNAME");
        nachnameBar.sendKeys("NICHTEXISTIERENDE NACHNAME");
        sucheButton.click();

        Thread.sleep(3000);

        List<WebElement> result = driver.findElements(By.cssSelector("li.person-block"));
        Assert.assertEquals( 0 , result.size());



    }

    @Test
    public void personComponentLISTINGTest(){
        // Listing aller Personen
        List<WebElement> listing = driver.findElements(By.cssSelector("li.person-block"));
        Assert.assertTrue(!listing.isEmpty());

        // Eine PersonListing
        for (WebElement personElement : listing) {
            // Bearbeiten Button
            WebElement bearbeitenButton = personElement.findElement(By.xpath(
                    "//button[contains(text() ,'Bearbeiten')]"));
            Assert.assertTrue(bearbeitenButton.isDisplayed() , "Bearbeiten button is not displayed");

            // Loeschen Button
            WebElement loeschenButton = personElement.findElement(By.xpath(
                    "//button[contains(text() ,'Löschen')]"
            ));
            Assert.assertTrue(loeschenButton.isDisplayed(),"Löschen button is not displayed");

        }

    }

    @Test
    public void personComponentLOESCHENTest()  {

        List<WebElement> listing = driver.findElements(By.cssSelector("li.person-block"));
        int bevor = listing.size();
        if(bevor>0){
            WebElement personLOESCHENButton = listing.getFirst().findElement(
                    By.xpath("//button[contains(text(),'Löschen')]"));
            personLOESCHENButton.click();

        }


        // Thread.sleep() funktioniert nicht
        int danach = 0;
        while(danach==bevor){
            listing = driver.findElements(By.cssSelector("li.person-block"));
             danach = listing.size();
        }
        Assert.assertTrue(bevor>danach);

    }

    @Test
    public void personComponentVorNachnameDisplayTest() {
        List<WebElement> listing = driver.findElements(By.cssSelector("li.person-block"));
        if(listing.size()>0){
            WebElement person = listing.getFirst();
            WebElement vorNachnameHeading = person.findElement(By.cssSelector("h5.listing-heading"));

            Assert.assertTrue(vorNachnameHeading.isDisplayed(), "VorNachname Heading is not displayed");

            WebElement vorname = vorNachnameHeading.findElement(By.xpath("//p[1]"));
            WebElement nachname = vorNachnameHeading.findElement(By.xpath("//p[2]"));

            Assert.assertTrue(vorname.isDisplayed(), "Vorname is not displayed");
            Assert.assertTrue(nachname.isDisplayed(), "Nachname is not displayed");

        }
    }

    @Test
    public void personComponentAdresseDisplayTest(){
        List<WebElement> listing = driver.findElements(By.cssSelector("li.person-block"));
        if(listing.size()>0){
            System.out.println();
        }
    }

     */



}
