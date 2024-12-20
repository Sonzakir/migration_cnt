package com.fazli.telefonuch.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// page_url = http://localhost:4200/firma
public class FirmaÜbersicht implements FirmaÜbersichtPage{
    private WebDriver driver;


    public FirmaÜbersicht(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span")
    public WebElement homePageButton;

    @FindBy(xpath = "//button[@routerlink='/person']")
    public WebElement buttonPersonSuche;

    @FindBy(xpath = "//button[@class='add-button']")
    public WebElement buttonHinzufuegen;
    
    



   

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }

    @Override
    public Integer anzahlDerSichtbarenFirmen() {
        List<WebElement> result = driver.findElements(By.cssSelector("li.firma-block"));
        return result.size();
    }


    @Override
    public PersonÜbersicht drückePersonSucheButton() {
        buttonPersonSuche.click();
        return new PersonÜbersicht(driver);
    }

    @Override
    public boolean zeigtPersonSeiteAn() {
        return driver.getCurrentUrl().equals("http://localhost:4200/person");
    }

    @Override
    public boolean zeigtStartSeiteAn() {
        return driver.getCurrentUrl().equals("http://localhost:4200/");

    }

    @Override
    public Startseite anStartseiteWeiterleiten() {
        homePageButton.click();
        return new Startseite(driver);
    }

    @Override
    public FirmaSucheLeiste getSucheLeiste() {
        return new FirmaSucheLeiste(driver);
    }

    @Override
    public FirmaErgebnis getErgebnis() {
        return new FirmaErgebnis(driver);
    }

    public FirmaHinzufuegen getFirmaForm(){
        return new FirmaHinzufuegen(driver);
    }

    @Override
    public boolean warteBisFirmaDisplayed(String firmaname) {
        var result = suche(firmaname, null,null,null,null);
        while(result.anzahlDerSichtbarenFirmen()==0){
            driver.navigate().refresh();
            getSucheLeiste().inputFirmaname.clear();
            result = suche(firmaname, null,null,null,null);
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement neuFirma = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("li.firma-block")
        ));
        return neuFirma.isDisplayed();
    }

    @Override
    public FirmaErgebnis suche(String Firmaname, String plz, String stadt, String bundesland, String branche) {
        return getSucheLeiste().suche(Firmaname , plz, stadt,bundesland , branche);
    }

    @Override
    public FirmaErgebnis hinzufuegen(String firmaname, String str, String hausNo, String plz, String stadt, String bundesland,
                                     String festNo, String email, String webseite, String fax, String branche) {
        buttonHinzufuegen.click();
        return getFirmaForm().hinzufuegen( firmaname,  str,  hausNo,  plz,  stadt,  bundesland,
                festNo,  email,  webseite,  fax , branche);
    }


    public boolean loescheAlleSichtbarenFirmen(){
        var sichtbarenFirmen = suche(null, null,null,null,null);
        while (sichtbarenFirmen.anzahlDerSichtbarenFirmen()!=0){
            //löscht die Firma, die in 1. listing index angezeigt wird
            sichtbarenFirmen.loeschen();
            sichtbarenFirmen = suche(null, null,null,null,null);
        }
        return true;
    }
}