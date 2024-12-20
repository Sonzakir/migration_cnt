package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = http://localhost:4200/firmaForm
public class FirmaHinzufuegen implements FirmaHinzufuegenPage {
    
    private WebDriver driver;
    public FirmaHinzufuegen(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(xpath = "//input[@name='name']")
    public WebElement inputName;

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

    @FindBy(xpath = "//input[@name='kontaktList.festnetznummer']")
    public WebElement inputfestnetznummer;

    @FindBy(xpath = "//input[@type='email']")
    public WebElement inputEmail;

    @FindBy(xpath = "//input[@name='kontaktList.webseite']")
    public WebElement inputWebseite;

    @FindBy(xpath = "//input[@placeholder=' 0176xxxx9015']")
    public WebElement inputFaxNummer;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonSpeichern;

    @FindBy(xpath = "//input[@name='branche']")
    public WebElement inputBranche;

   

  

    @Override
    public void fülleFirmaname(String firmaname) {
        inputName.sendKeys(firmaname);

    }

    @Override
    public void fülleAdresse(String strasse, String hausnummer, String plz, String stadt, String bundesland) {
        if(strasse!=null){
            inputStrasse.clear();
            inputStrasse.sendKeys(strasse);
        }
        if(hausnummer!=null){
            inputHausNo.clear();
            inputHausNo.sendKeys(hausnummer);
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
    public void fülleKontakt(String festnetznummer, String email, String webseite, String faxNo) {
        if(festnetznummer!=null){
            inputfestnetznummer.clear();
            inputfestnetznummer.sendKeys(festnetznummer);
        }
        if(email!=null){
            inputEmail.clear();
            inputEmail.sendKeys(email);
        }
        if(webseite!=null){
            inputWebseite.clear();
            inputWebseite.sendKeys(webseite);
        }
        if(faxNo!=null){
            inputFaxNummer.clear();
            inputFaxNummer.sendKeys(faxNo);
        }

    }

    public void fülleBranche(String branche){
        inputBranche.clear();
        inputBranche.sendKeys(branche);
    }

    @Override
    public FirmaErgebnis speichern() {
        buttonSpeichern.click();
        return new FirmaErgebnis(driver);
    }

    @Override
    public FirmaErgebnis hinzufuegen(String firmaname, String strasse, String hausNo, String plz, String stadt, String bundesland, 
                                     String festNo, String email, String webseite, String faxNo,
                                     String branche) {
        if(firmaname!=null){
            // Um duplikate Überschhreiben zu vermeiden
            inputName.clear();
            fülleFirmaname(firmaname);
        }

        fülleAdresse(strasse,hausNo,plz,stadt,bundesland);
        fülleKontakt(festNo,email,webseite,faxNo);
        if(branche!=null){
            fülleBranche(branche);
        }
        
        return speichern();
    }

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
}