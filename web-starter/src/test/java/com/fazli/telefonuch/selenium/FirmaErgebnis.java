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
public class FirmaErgebnis implements FirmaErgebnisPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/div/button[1]")
    public WebElement buttonFirmaBearbeiten;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/div/button[2]")
    public WebElement buttonFirmaLoeschen;

    //              kontakt
    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[2]/ul/li/div/button[1]")
    public WebElement buttonFirmaKontaktBearbeiten;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[2]/ul/li/div/button[2]")
    public WebElement buttonFirmaKontaktHinzufuegen;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[2]/ul/li/div/button[3]")
    public WebElement buttonFirmaKontaktLoeschen;


    //          adresse
    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[1]/ul/li/div/button[1]")
    public WebElement buttonFirmaAdresseBearbeiten;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[1]/ul/li/div/button[2]")
    public WebElement buttonFirmaAdresseHinzufuegen;


    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[1]/ul/li/div/button[3]")
    public WebElement buttonFirmaAdresseLoeschen;

    //                          Felder
    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/h5/p")
    public WebElement firmaNameFeld;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[2]/ul/li/p[1]")
    public WebElement festNetznummerField;

    @FindBy(xpath = "/html/body/app-root/app-firma-home/section/ul/li/app-firma/div[2]/ul/li/div/button[3]")
    public WebElement buttonEntfernen;

    @FindBy(xpath = "//li[@class='address-item']")
    public WebElement adresseFeld;





    

    
    
    public FirmaErgebnis(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    public FirmaErgebnis bearbeiten(String firmaname, String str, String hausNo, String plz, String stadt, String bundesland,
                                    String festNo, String email, String webseite, String faxNo, String branche) {
        buttonFirmaBearbeiten.click();
        return getFirmaForm().hinzufuegen(firmaname,str, hausNo, plz, stadt, bundesland ,
                festNo, email,webseite,faxNo,branche);
    }

    @Override
    public boolean loeschen() {

        return warteBisFirmaGeloescht();
    }


    @Override
    public boolean warteBisFirmaHinzugefuegt(int anzahlBevor){
        List<WebElement> result = driver.findElements(By.cssSelector("li.firma-block"));
        while(result.size()==anzahlBevor){
            // button suche click?
            driver.navigate().refresh();
            suche(null, null , null , null, null);
            result = driver.findElements(By.cssSelector("li.firma-block"));
        }
        return true;
    }

    @Override
    public boolean warteBisFirmaGeloescht() {
        int anzahlBevor = anzahlDerSichtbarenFirmen();
        List<WebElement> result = driver.findElements(By.cssSelector("li.firma-block"));
        while(result.size()==anzahlBevor){
            buttonFirmaLoeschen.click();
            driver.navigate().refresh();
            suche(null, null , null , null, null);
            result = driver.findElements(By.cssSelector("li.firma-block"));
        }
        return true;
    }
    
    @Override
    public boolean warteBisFirmanameAktualisiert(String firmaname) {
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(firmaNameFeld , firmaname));
        return firmaNameFeld.isDisplayed();
    }

    @Override
    public int anzahlDerSichtbarenFirmen() {
        List<WebElement> result = driver.findElements(By.cssSelector("li.firma-block"));
        return result.size();
    }

    @Override
    public FirmaErgebnis suche(String firmaname, String plz, String stadt, String bundesland, String branche){
        return getSucheLeiste().suche(firmaname,plz,stadt, bundesland, branche);
    }
    @Override
    public FirmaSucheLeiste getSucheLeiste(){
        return new FirmaSucheLeiste(driver);
    }

    @Override
    public FirmaHinzufuegen getFirmaForm() {
        return new FirmaHinzufuegen(driver);
    }

    @Override
    public FirmaÜbersicht getFirmaÜbersicht() {
        return new FirmaÜbersicht(driver);
    }

    //                      Kontakt

    @Override
    public boolean warteBisKontaktAktualisiert(String festNo, String email, String webseite, String faxNo) {
        driver.navigate().refresh();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(festNetznummerField , festNo));
        return festNetznummerField.isDisplayed();
    }

    @Override
    public boolean warteBisKontaktHinzugefuegt(String firmaname, int anzahlDerErwartetenKontakten){
        int rs  = anzahlDerSichtbarenFirmaKontakten();
        while (rs!=anzahlDerErwartetenKontakten){
            driver.navigate().refresh();

            rs = suche(firmaname ,null , null , null, null).anzahlDerSichtbarenFirmaKontakten();
        }
        return true;
    }

    public boolean warteBisKontaktGeloescht(String firmaname, int anzahlDerErwartetenKontakten){
        return warteBisKontaktHinzugefuegt(firmaname,0);
    }




    @Override
    public FirmaErgebnis firmaKontaktBearbeiten(String festNo, String email, String webseite, String faxNo) {
        buttonFirmaKontaktBearbeiten.click();
        return getFirmaKontaktForm().bearbeiten(festNo, email ,webseite, faxNo);
    }

    @Override
    public FirmaErgebnis firmaKontaktHinzufuegen(String festNo, String email, String webseite, String faxNo) {
        buttonFirmaKontaktHinzufuegen.click();
        return getFirmaKontaktForm().hinzufuegen(festNo,email,webseite,faxNo);

    }

    @Override
    public FirmaKontaktForm getFirmaKontaktForm() {
        return new FirmaKontaktForm(driver);
    }

    @Override
    public FirmaErgebnis firmaKontaktLoeschen() {
        buttonFirmaKontaktLoeschen.click();
        return new FirmaErgebnis(driver);
    }

    @Override
    public int anzahlDerSichtbarenFirmaKontakten(){
        List<WebElement> kontakten = driver.findElements(By.cssSelector("li.contact-item"));
        return kontakten.size();
    }


    //                      Adressen

    @Override
    public FirmaErgebnis firmaAdresseBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        buttonFirmaAdresseBearbeiten.click();
        return getAdresseForm().firmaBearbeiten(strasse, hausNo, plz, stadt, bundesland);
    }

    @Override
    public FirmaErgebnis firmaAdresseHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        buttonFirmaAdresseHinzufuegen.click();
        return getAdresseForm().firmaHinzufuegen(strasse, hausNo, plz, stadt, bundesland);
    }

    public boolean warteBisAdresseHinzugefuegt(String firmaname , int anzahlDerErwartetenAdressen){
        int rs = anzahlDerAdressen();
        while (rs!=anzahlDerErwartetenAdressen){
            driver.navigate().refresh();
            suche(firmaname,null,null, null , null).anzahlDerAdressen();
            rs = anzahlDerAdressen();
        }
        return true;
    }

    public int anzahlDerAdressen(){
        List<WebElement> adressenBlock = driver.findElements(By.cssSelector("li.address-item"));
        return adressenBlock.size();
    }
    
    public boolean warteBisAdresseAktualisiert(String firmaname ,
                                               String Strase, String hausNo, String plz, String stadt, String bundesland){

        driver.navigate().refresh();
        suche(firmaname,null,stadt,bundesland, null);
        return adresseFeld.getText().contains(Strase) &&
                adresseFeld.getText().contains(hausNo) &&
                adresseFeld.getText().contains(plz) &&
                adresseFeld.getText().contains(stadt) &&
                adresseFeld.getText().contains(bundesland);

    }

    @Override
    public FirmaErgebnis firmaAdresseLoeschen() {
        buttonFirmaAdresseLoeschen.click();
        return new FirmaErgebnis(driver);
    }

    public boolean warteBisAdresseGeloescht(String firmaname,int erwarteteAnzahlDerAdressen){
        return warteBisAdresseHinzugefuegt(firmaname,erwarteteAnzahlDerAdressen);
    }

    @Override
    public AdresseForm getAdresseForm() {
        return new AdresseForm(driver);
    }



    @Override
    public boolean istSichtbar(WebElement element) {
        return element.isDisplayed();
    }
}