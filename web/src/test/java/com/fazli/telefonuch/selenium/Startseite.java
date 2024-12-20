package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/
public class Startseite implements StartseitePage {

    private WebDriver driver;

    public Startseite(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@routerlink='/person']")
    public WebElement buttonPersonSuche;

    @FindBy(xpath = "//button[@routerlink='/firma']")
    public WebElement buttonFirmaSuche;

    @FindBy(xpath = "//button[contains(@onclick, 'person')]")
    public WebElement buttonPersonenHomepage;

    @FindBy(xpath = "//button[@onclick=\"window.location.href='/firma';\"]")
    public WebElement buttonFirmenHomepage;




    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }

    public boolean personSucheButtonSichtbar(){
        return istSichtbar(buttonPersonSuche);
    }

    @Override
    public PersonÜbersicht drückePersonSuche(){
        buttonPersonSuche.click();
        return new PersonÜbersicht(driver);
    }

    public boolean zeigtPersonSuchePageAn(){
        return driver.getCurrentUrl().contains("/person");
    }

    
    public boolean personenHomePageButtonSichtbar() {
        return istSichtbar(buttonPersonenHomepage);
    }

    public PersonÜbersicht drückePersonenHomePage() {
        buttonPersonenHomepage.click();
        return new PersonÜbersicht(driver);
    }

    @Override
    public FirmaÜbersicht drückeFirmaSuche() {
        buttonFirmaSuche.click();
        return new FirmaÜbersicht(driver);
    }

    @Override
    public FirmaÜbersicht drückeFirmenHomePage() {
        buttonFirmaSuche.click();
        return new FirmaÜbersicht(driver);
    }


}