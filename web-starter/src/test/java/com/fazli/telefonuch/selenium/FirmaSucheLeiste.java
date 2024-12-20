package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

// page_url = http://localhost:4200/firma
public class FirmaSucheLeiste implements FirmaSucheLeistePage{
    private WebDriver driver;

    public FirmaSucheLeiste(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//input[@placeholder='Firmaname']")
    public WebElement inputFirmaname;

    @FindBy(xpath = "//input[@placeholder='PLZ']")
    public WebElement inputPLZ;

    @FindBy(xpath = "//input[@placeholder='Stadt']")
    public WebElement inputStadt;

    @FindBy(xpath = "//input[@placeholder='Bundesland']")
    public WebElement inputBundesland;

    @FindBy(xpath = "//input[@placeholder='Branche']")
    public WebElement inputBranche;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/div/div[5]/button[1]")
    public WebElement buttonSuche;


    @Override
    public void fülleFirmaName(String firmaname) {
        inputFirmaname.sendKeys(firmaname);
    }

    @Override
    public void füllePLZ(String plz) {
        inputPLZ.sendKeys(plz);
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
    public void fülleBranche(String branche) {
        inputBranche.sendKeys(branche);
    }

    @Override
    public FirmaErgebnis drückeSuche() {
        buttonSuche.click();
        return new FirmaErgebnis(driver);
    }

    @Override
    public FirmaErgebnis suche(String firmaname, String plz, String stadt, String bundesland, String branche) {
        if(firmaname!=null){
            fülleFirmaName(firmaname);
        }
        if(plz!=null){
            füllePLZ(plz);
        }
        if(stadt!=null){
            fülleStadt(stadt);
        }
        if(bundesland!=null){
            fülleBundesland(bundesland);
        }
        if(branche!=null){
            fülleBranche(branche);
        }
        return drückeSuche();
    }

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
}