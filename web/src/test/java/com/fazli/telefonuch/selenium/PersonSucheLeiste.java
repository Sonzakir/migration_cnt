package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/person
public class PersonSucheLeiste implements  PersonSucheLeistePage {

    private WebDriver driver;
    
    @FindBy(xpath = "//input[@placeholder='Vorname']")
    public WebElement inputVorname;

    @FindBy(xpath = "//input[@placeholder='Nachname']")
    public WebElement inputNachname;

    @FindBy(xpath = "//input[@placeholder='Stadt']")
    public WebElement inputStadt;

    @FindBy(xpath = "//input[@placeholder='Bundesland']")
    public WebElement inputBundesland;

    @FindBy(xpath = "//*[@id='search-bar-suche']")
    public WebElement sucheButton;
    
    
    
    
    public PersonSucheLeiste(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
    
    @Override
    public void fülleVorname(String vorname) {
        inputVorname.sendKeys(vorname);
    }

    @Override
    public void fülleNachname(String nachname) {
        inputNachname.sendKeys(nachname);
    }

    @Override
    public void fülleStadt(String stadt) {
        inputStadt.sendKeys(stadt);
    }

    @Override
    public void fülleBundesland(String bundesland) {
        inputBundesland.sendKeys(bundesland);
    }

    @Override
    public PersonErgebnis drückeSuche() {
        sucheButton.click();
        return new PersonErgebnis(driver);
    }

    @Override
    public PersonErgebnis suche(String vorname, String nachname, String stadt, String bundesland) {
        if (vorname!=null) fülleVorname(vorname);
        if(nachname!=null) fülleNachname(nachname);
        if(stadt!=null) fülleStadt(stadt);
        if(bundesland!=null) fülleBundesland(bundesland);
        return drückeSuche();
    }
}