package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/personKontaktForm
public class PersonKontaktForm implements PersonKontaktFormPage {

//    @FindBy(css = "input[placeholder=' 0122xxx3747']")
//    public WebElement inputFestnetzNummer;


    @FindBy(xpath = "//*[@id='FestnetzNo']")
    public WebElement inputFestnetzNummer;



    @FindBy(xpath = "//input[@type='email']")
    public WebElement inputKontaktListEmail;

    @FindBy(xpath = "//input[@type='url']")
    public WebElement inputWebseite;

    @FindBy(xpath = "//input[@placeholder=' 0176xxxx9015']")
    public WebElement inputMobilnummern;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonSpeichern;
    
    private WebDriver driver;


    public WebElement inputKontaktList;



    public PersonKontaktForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void fülleFestnetznummer(String festnetznummer) {
        inputFestnetzNummer.clear();
        inputFestnetzNummer.sendKeys(festnetznummer);
    }

    @Override
    public void fülleEmail(String email) {
        inputKontaktListEmail.clear();
        inputKontaktListEmail.sendKeys(email);
    }

    @Override
    public void fülleWebseite(String webseite) {
        inputWebseite.clear();
        inputWebseite.sendKeys(webseite);
    }

    @Override
    public void fülleMobilnummer(String mobilnummer) {
        inputMobilnummern.clear();
        inputMobilnummern.sendKeys(mobilnummer);
    }

    @Override
    public PersonErgebnis speichern() {
        buttonSpeichern.click();
        return new PersonErgebnis(driver);
    }

    @Override
    public PersonErgebnis bearbeiten(String festNo, String email, String webseite, String mobilnummer) {
        if(festNo!=null)fülleFestnetznummer(festNo);
        if(email!=null)fülleEmail(email);
        if(webseite!=null)fülleWebseite(webseite);
        if(mobilnummer!=null)fülleMobilnummer(mobilnummer);
        return speichern();
    }

//    Um namen Konflikte zu vermeiden, habe ich hinzufuegen methode wieder implementiert jedoch die Logik dahinter ist 1:1 zu bearbetien
@Override
public PersonErgebnis hinzufuegen(String festNo, String email, String webseite, String mobilnummer) {
        if(festNo!=null)fülleFestnetznummer(festNo);
        if(email!=null)fülleEmail(email);
        if(webseite!=null)fülleWebseite(webseite);
        if(mobilnummer!=null)fülleMobilnummer(mobilnummer);
        return speichern();
    }

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }



}
