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

// page_url = http://localhost:4200/person
public class PersonÜbersicht implements PersonÜbersichtPage {

    private WebDriver driver;

    @FindBy(xpath = "//span")
    public WebElement spanDE_Telefonbuch;

    @FindBy(xpath = "//button[@routerlink='/firma']")
    public WebElement buttonFirmaSuche;

    @FindBy(xpath = "//button[@routerlink='/personForm']")
    public WebElement buttonHinzufügen;
    
    
    public PersonÜbersicht(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    

    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
    
    @Override
    public Integer anzahlDerSichtbarenPersonen(){
        List<WebElement> result = driver.findElements(By.cssSelector("li.person-block"));
        return result.size();

    }

    @Override
    public FirmaÜbersichtPage drückeFirmaSuche(){
        buttonFirmaSuche.click();
        return new FirmaÜbersicht(driver);
    }

    @Override
    public boolean zeigtFirmaSeiteAn(){
        return driver.getCurrentUrl().equals("http://localhost:4200/firma");
    }



   @Override
   public boolean zeigtStartSeiteAn(){
       return driver.getCurrentUrl().equals("http://localhost:4200/");
   }

   @Override
   public Startseite anStartSeiteWeiterleiten(){
       spanDE_Telefonbuch.click();
       return new Startseite(driver);
   }


    @Override
    public PersonSucheLeiste getSucheLeiste(){
        return new PersonSucheLeiste(driver);
    }

    @Override
    public PersonErgebnis suche(String vorname, String nachname,
                                String stadt, String bundesland){
        return getSucheLeiste().suche(vorname, nachname, stadt , bundesland);
    }

    @Override
    public PersonErgebnis getErgebnis() {
        return new PersonErgebnis(driver);
    }

    @Override
    public PersonHinzufuegen getPersonForm(){
        return new PersonHinzufuegen(driver);
    }


    @Override
    public PersonErgebnis hinzufuegen(
            String vorname, String nachname,
            String strasse, String hausNo, String plz, String stadt, String bundesland,
            String festNo, String email, String webseite, String mobilno
    ) {
        // ohne button click-> geht nicht an die entsprechende url somit speichern funktionieren nicht
        buttonHinzufügen.click();
        return getPersonForm().hinzufuegen(vorname, nachname, strasse, hausNo, plz, stadt, bundesland, festNo, email, webseite, mobilno);
    }


    // Methode zu warten und überprüfen ob die Person hinzugefügt wird
    @Override
    public boolean warteBisPersonDisplayed(String vorname, String nachname){

        suche(vorname, nachname, null , null);

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        WebElement neuHinzugefügtePerson = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("/html/body/app-root/app-person-home/section/ul/li/app-person/h5/p[1]")));

        return neuHinzugefügtePerson.isDisplayed();

    }

    // warten mit while loop
    @Override
    public boolean warteBisPersonDisplayedWhileLoop(String vorname, String nachname){
        var result =   suche(vorname, nachname, null , null);
        while (result.anzahlDerSichtbarenPersonen()==0){
            driver.navigate().refresh();
            getSucheLeiste().inputVorname.clear();
            getSucheLeiste().inputNachname.clear();
            result =   suche(vorname, nachname, null , null);
        }
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        WebElement neuHinzugefügtePerson = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/app-root/app-person-home/section/ul/li/app-person/h5/p[1]")));
        return neuHinzugefügtePerson.isDisplayed();
    }



    @Override
    public boolean zeigtHinzufuegenFormAn(){
        return driver.getCurrentUrl().equals("http://localhost:4200/personForm");
    }

    public boolean loescheAlleSichtbarenPersonen(){
        var sichtbarenPersonen = suche(null,null,null,null);
        while (sichtbarenPersonen.anzahlDerSichtbarenPersonen()!=0){
//            driver.navigate().refresh();
            // löscht die Person, die in 1. listing index angezeigt wird
            sichtbarenPersonen.loeschen();
            sichtbarenPersonen = suche(null,null,null,null);
        }
        return true;
    }
    
}