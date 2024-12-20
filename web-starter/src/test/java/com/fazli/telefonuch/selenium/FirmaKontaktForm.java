package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/firmaKontaktForm
public class FirmaKontaktForm implements FirmaKontaktFormPage {

    @FindBy(xpath = "//input[@name='kontaktList.festnetznummer']")
    public WebElement inputFestnetzNummer;

    @FindBy(xpath = "//input[@type='email']")
    public WebElement inputKontaktListEmail;

    @FindBy(xpath = "//input[@type='url']")
    public WebElement inputWebseite;

    @FindBy(xpath = "//input[@placeholder=' 0176xxxx9015']")
    public WebElement inputFaxNummer;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement buttonSpeichern;

    private WebDriver driver;
    public FirmaKontaktForm(WebDriver driver) {
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
    public void fülleFaxNo(String faxNo) {
        inputFaxNummer.clear();
        inputFaxNummer.sendKeys(faxNo);
    }

    public FirmaErgebnis speichern(){
        buttonSpeichern.click();
        return new FirmaErgebnis(driver);
    }

    @Override
    public FirmaErgebnis bearbeiten(String festNo, String email, String webseite, String faxNo) {
        if(festNo!=null){
            fülleFestnetznummer(festNo);
        }
        if(email!=null){
            fülleEmail(email);
        }
        if(webseite!=null){
            fülleWebseite(webseite);
        }
        if (faxNo!=null){
            fülleFaxNo(faxNo);
        }
        return speichern();
    }

    @Override
    public FirmaErgebnis hinzufuegen(String festNo, String email, String webseite, String faxNo) {
        if(festNo!=null){
            fülleFestnetznummer(festNo);
        }
        if(email!=null){
            fülleEmail(email);
        }
        if(webseite!=null){
            fülleWebseite(webseite);
        }
        if (faxNo!=null){
            fülleFaxNo(faxNo);
        }
        return speichern();    }

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
}