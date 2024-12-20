package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/adresseForm
public class AdresseForm implements AdresseFormPage {
    private WebDriver driver;
    @FindBy(xpath = "//*[@id='strasse']")
    public WebElement inputStrasse;

    @FindBy(xpath = "//*[@id='hausNo']")
    public WebElement inputHausNo;

    @FindBy(xpath = "//*[@id='plz']")
    public WebElement inputPlz;

    @FindBy(xpath = "//*[@id='stadt']")
    public WebElement inputStadt;

    @FindBy(xpath = "//*[@id='bundesland']")
    public WebElement inputBundesland;
    


    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonSpeichern;
    public AdresseForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void fülleStrasse(String strasse) {
        inputStrasse.sendKeys(strasse);
    }

    @Override
    public void fülleHausNo(String hausNo) {
        inputHausNo.sendKeys(hausNo);
    }

    @Override
    public void füllePLZ(String plz) {
        inputPlz.sendKeys(plz);
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
    public boolean istSichtbar(WebElement element) {
        return false;
    }

    // Bearbeiten
    public PersonErgebnis bearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        if(strasse!=null){
            inputStrasse.clear();
            fülleStrasse(strasse);
        }
        if (hausNo!=null){
            inputHausNo.clear();
            fülleHausNo(hausNo);
        }
        if(plz!=null){
            inputPlz.clear();
            füllePLZ(plz);
        }
        if(stadt!=null){
            inputStadt.clear();
            fülleStadt(stadt);
        }
        if(bundesland!=null){
            inputBundesland.clear();
            fülleBundesland(bundesland);
        }
        return speichern();
    }

    public FirmaErgebnis firmaBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        if(strasse!=null){
            inputStrasse.clear();
            fülleStrasse(strasse);
        }
        if (hausNo!=null){
            inputHausNo.clear();
            fülleHausNo(hausNo);
        }
        if(plz!=null){
            inputPlz.clear();
            füllePLZ(plz);
        }
        if(stadt!=null){
            inputStadt.clear();
            fülleStadt(stadt);
        }
        if(bundesland!=null){
            inputBundesland.clear();
            fülleBundesland(bundesland);
        }
        return firmaSpeichern();
    }



    // Speichern
    public PersonErgebnis speichern() {
        buttonSpeichern.click();
        return new PersonErgebnis(driver);
    }

    public FirmaErgebnis firmaSpeichern() {
        buttonSpeichern.click();
        return new FirmaErgebnis(driver);
    }

    // hinzufuegen
    public PersonErgebnis hinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland){
        return bearbeiten(strasse, hausNo, plz, stadt, bundesland);
    }

    public FirmaErgebnis firmaHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland){
        return firmaBearbeiten(strasse, hausNo, plz, stadt, bundesland);
    }

    //  sonstige
    public String getStrasseFirstAdresse(){
        return inputStrasse.getText();
    }

    public String getHausNoFirstAdresse(){
        return inputHausNo.getText();
    }
    public String getPlzFirstAdresse(){
        return inputPlz.getText();
    }
    public String getStadtFirstAdresse(){
        return inputStadt.getText();
    }
    public String getBundeslandFirstAdresse(){
        return inputBundesland.getText();
    }



}