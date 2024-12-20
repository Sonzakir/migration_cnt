package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/personForm
public class PersonHinzufuegen implements PersonHinzufuegenPage {

    private WebDriver driver;
    public PersonHinzufuegen(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@name='name']")
    public WebElement inputName;

    @FindBy(xpath = "//input[@name='nachname']")
    public WebElement inputNachname;

    @FindBy(xpath = "//*[@id='strasse']")
    public WebElement inputStrasse;

    @FindBy(xpath = "//*[@id='hausNo']")
    public WebElement inputHausNo;

    @FindBy(xpath = "//*[@id='plz']")
    public WebElement inputPLZ;

    @FindBy(xpath = "//*[@id='stadt']")
    public WebElement inputStadt;

    @FindBy(xpath = "//*[@id='bundesland']")
    public WebElement inputBundesland;

    @FindBy(xpath = "//input[@placeholder=' 0122xxx3747']")
    public WebElement inputFestNo;

    @FindBy(xpath = "//input[@type='email']")
    public WebElement inputKontaktListEmail;

    @FindBy(xpath = "//input[@type='url']")
    public WebElement inputKontaktListWebseite;

    @FindBy(xpath = "//input[@placeholder=' 0176xxxx9015']")
    public WebElement inputKontaktListMobilnummern;


    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonSpeichern;

    @Override
    public void fülleVorname(String vorname) {
        inputName.sendKeys(vorname);
    }
    @Override
    public void fülleNachname(String nachname) {
        inputNachname.sendKeys(nachname);
    }
    @Override
    public void fülleAdresse(String strasse, String hausNo,
                             String plz, String stadt, String bundesland){
        if(strasse!=null){
            inputStrasse.clear();
            inputStrasse.sendKeys(strasse);
        }
        if(hausNo!=null){
            inputHausNo.clear();
            inputHausNo.sendKeys(hausNo);
        }
        if(plz!=null){
            inputPLZ.clear();
            inputPLZ.sendKeys(plz);
        }
        if(stadt!=null){
            inputStadt.clear();
            inputStadt.sendKeys(stadt);
        }
        if(bundesland!=null){
            inputBundesland.clear();
            inputBundesland.sendKeys(bundesland);
        }
    }

    @Override
    public void fülleKontakt(String festnetznummer, String email, String webseite, String mobilnummer) {
        if(festnetznummer!=null){
            inputFestNo.clear();
            inputFestNo.sendKeys(festnetznummer);
        }
        if(email!=null){
            inputKontaktListEmail.clear();
            inputKontaktListEmail.sendKeys(email);
        }
        if(webseite!=null){
            inputKontaktListWebseite.clear();
            inputKontaktListWebseite.sendKeys(webseite);
        }
        if(mobilnummer!=null){
            inputKontaktListMobilnummern.clear();
            inputKontaktListMobilnummern.sendKeys(mobilnummer);
        }
    }




    @Override
    public PersonErgebnis speichern() {
        buttonSpeichern.click();
        return new PersonErgebnis(driver);
    }

    @Override
    public PersonErgebnis hinzufuegen(String vorname, String nachname, String strasse, String hausNo, String plz, String stadt, String bundesland, String festNo, String email, String webseite, String mobilno) {
        if(vorname!=null){
            // Um duplikate Überschhreiben zu vermeiden
            inputName.clear();
            fülleVorname(vorname);
        }
        if(nachname!=null){
            inputNachname.clear();
            fülleNachname(nachname);
        }
        fülleAdresse(strasse,hausNo,plz,stadt,bundesland);
        fülleKontakt(festNo,email,webseite,mobilno);
        return speichern();

    }
}