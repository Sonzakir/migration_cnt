package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StartseiteTest {
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
    public void personSucheButtonTest(){
        // setup
        Startseite startseite = new Startseite(driver);
        boolean istSichtbar = startseite.personSucheButtonSichtbar();
        // click
        startseite.drückePersonSuche();
        boolean weiterleitet = startseite.zeigtPersonSuchePageAn();
        // assert
        Assert.assertTrue(istSichtbar ,  "Person Suche Button ist nicht sichtbar");
        Assert.assertTrue(weiterleitet , "Button leitet nicht weiter");
    }

    @Test
    public void personenHomePageButtonTest(){
        // setup
        Startseite startseite = new Startseite(driver);
        boolean istSichtbar = startseite.personenHomePageButtonSichtbar();
        // click
        startseite.drückePersonenHomePage();
        boolean weiterleitet = startseite.zeigtPersonSuchePageAn();

        //assert
        Assert.assertTrue(istSichtbar , "Personen Homepage button ist nicht sichtbar");
        Assert.assertTrue(weiterleitet , "Button leitet nicht weiter");
    }

    public void meh() {
        Startseite startseite = new Startseite(driver);

//        startseite.drückePersonSuche().getLeiste().fülle().getErsterEintrag().

//        PersonSuche ps = startseite.drückePersonSuche();
//        PersonErgebnis e = ps.suche("vor", null, null, null);
//        ps.hinzufügen(hausnummer, plz, lsad);
//        ps.drückeHinzufgen();
//        ps.setzeHAusnummer()
//                ps.setStadt();
//        ps.drückeSpeichern().
//        e.hatAnzahlPersonen(5);
//


    }

}
