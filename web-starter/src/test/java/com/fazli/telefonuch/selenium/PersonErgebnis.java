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
public class PersonErgebnis implements PersonErgebnisPage {
    private WebDriver driver;

    // TODO : wählt immer die erste Person in ergebnis
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/div/button[1]")
    public WebElement buttonPersonBearbeiten;

    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/div/button[2]")
    public WebElement buttonPersonLoeschen;
    
   
    // Kontakt Buttons 
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li/app-person/div[2]/ul/li/div/button[1]")
    public WebElement buttonKontaktBearbeiten;

    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > div:nth-of-type(2) > ul > li > div > button:nth-of-type(2)")
    public WebElement buttonKontaktHinzufuegen;

    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li/app-person/div[2]/ul/li/div/button[3]")
    public WebElement buttonKontaktLoeschen;




    // Adresse Buttons 

    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > div:nth-of-type(1) > ul > li > div > button:nth-of-type(1)")
    public WebElement buttonAdresseBearbeiten;

    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > div:nth-of-type(1) > ul > li > div > button:nth-of-type(2)")
    public WebElement buttonAdresseHinzufuegen;

    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > div:nth-of-type(1) > ul > li > div > button:nth-of-type(3)")
    public WebElement buttonAdresseEntfernen;



    //                              Namen Felder
    // Vorname Field
    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > h5 > p:nth-of-type(1)")
    public WebElement personVornameField;


    //Nachname Field
    @FindBy(css = "html > body > app-root > app-person-home > section > ul > li:nth-of-type(1) > app-person > h5 > p:nth-of-type(2)")
    public WebElement personNachnameField;

    //                              Kontakt Felder
    // Mobilnummer Field
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/app-person/div[2]/ul/li[1]/p[1]")
    public WebElement mobilNummerField;

    // Festnetznummer Field
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/app-person/div[2]/ul/li[1]/p[2]")
    public WebElement festnetzNummerField;

    // Webseite Field
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/app-person/div[2]/ul/li[1]/p[3]")
    public WebElement webseiteField;

    // Email Field
    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/app-person/div[2]/ul/li[1]/p[4]")
    public WebElement emailField;

    
    
    
    //                          AdressenFeld

    @FindBy(xpath = "/html/body/app-root/app-person-home/section/ul/li[1]/app-person/div[1]/ul/li[1]")
    public WebElement adresseFeld;






    






    public PersonErgebnis(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    @Override
    public int anzahlDerSichtbarenPersonen(){
        List<WebElement> result = driver.findElements(By.cssSelector("li.person-block"));
        return result.size();
    }



    @Override
    public PersonHinzufuegen getPersonForm(){
        return new PersonHinzufuegen(driver);
    }

    @Override
    public PersonErgebnis bearbeiten(
            String vorname, String nachname,
            String strasse, String hausNo, String plz, String stadt, String bundesland,
            String festnetzNo, String email, String webseite, String mobilNo
    ){

        buttonPersonBearbeiten.click();
        return getPersonForm().hinzufuegen(
                vorname, nachname,
                strasse, hausNo, plz, stadt, bundesland,
                festnetzNo, email, webseite, mobilNo
        );

    }

    @Override
    public PersonÜbersicht getPersonÜbersicht(){
        return new PersonÜbersicht(driver);
    }

    @Override
    public PersonÜbersicht drückeLoeschenButton(){
        buttonPersonLoeschen.click();
        return getPersonÜbersicht();
    }


    @Override
    public boolean loeschen(){
        return warteBisPersonGeloescht();

    }

    @Override
    public boolean warteBisPersonGeloescht(){
        int anzahlBevor = anzahlDerSichtbarenPersonen();
        buttonPersonLoeschen.click();
        List<WebElement> result = driver.findElements(By.cssSelector("li.person-block"));
        while(result.size()==anzahlBevor){
//            driver.navigate().refresh();
            suche(null,null,null,null);
            result = driver.findElements(By.cssSelector("li.person-block"));
        }
        return true;
    }

    @Override
    public PersonErgebnis loeschenMitErgebnis(){
        buttonPersonLoeschen.click();
        return new PersonErgebnis(driver);

    }

    //              Kontakt

    @Override
    public PersonKontaktForm getPersonKontaktForm(){
        return new PersonKontaktForm(driver);
    }
    @Override
    public PersonErgebnis personKontaktBearbeiten(String festNo, String email,
                                                  String webseite, String mobilNo) {
        buttonKontaktBearbeiten.click();
        return getPersonKontaktForm().bearbeiten(festNo , email , webseite , mobilNo);
    }

    // personKontakt hinzufuege
    @Override
    public PersonErgebnis personKontaktHinzufuegen(String festNo, String email, String webseite, String mobilNo) {
        
        buttonKontaktHinzufuegen.click();
        //Um selenium.NoSuchElementException nicht zu bekommen -> wait
        By locator = By.id("FestnetzNo");

        //wait up to 10 seconds until Festnetznummer Input field is loaded into DOM
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));


        return getPersonKontaktForm().hinzufuegen(festNo, email, webseite, mobilNo);
    }



    @Override
    public PersonErgebnis personKontaktLoeschen() {
        buttonKontaktLoeschen.click();
        return new PersonErgebnis(driver);
    }


    //              Adresse

    @Override
    public AdresseForm getAdresseForm(){
        return new AdresseForm(driver);
    }


    @Override
    public PersonErgebnis personAdresseBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        buttonAdresseBearbeiten.click();
        return getAdresseForm().bearbeiten(strasse, hausNo, plz, stadt, bundesland);
    }

    @Override
    public PersonErgebnis personAdresseHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland) {
        buttonAdresseHinzufuegen.click();
        return getAdresseForm().hinzufuegen(strasse, hausNo, plz, stadt, bundesland);
    }

    @Override
    public PersonErgebnis personAdresseLoeschen() {
        buttonAdresseEntfernen.click();
        return new PersonErgebnis(driver);
    }


    @Override
    public String getPersonVornameField(){
        return personVornameField.getText();
    }
    
    @Override
    public String getPersonNachnameField(){
        return personNachnameField.getText();
    }
    

    @Override
    public String getPersonFestNo(){
        return festnetzNummerField.getText();
    }
    @Override
    public String getPersonEmail(){
        return emailField.getText();
    }
    @Override
    public String getPersonWebseite(){
        return webseiteField.getText();
    }
    @Override
    public String getPersonMobilNo(){
        return mobilNummerField.getText();
    }

    @Override
    public boolean KontaktEquals(String festNo, String email, String webseite, String mobilNo) {
        return getPersonFestNo().equals(festNo) &&
                getPersonEmail().equals(email) &&
                getPersonWebseite().equals(webseite) &&
                getPersonWebseite().equals(mobilNo);
    }
    @Override
    public PersonSucheLeiste getSucheLeiste(){
        return new PersonSucheLeiste(driver);
    }

    @Override
    public PersonErgebnis suche(String vorname, String nachname, String stadt, String bundesland){
        return getSucheLeiste().suche(vorname,nachname,stadt,bundesland);
    }

    //                          Kontakten


    @Override
    public int anzahlDerKontakten(){
        List<WebElement> kontakten = driver.findElements(By.cssSelector("li.contact-item"));
        return kontakten.size();
    }

    // Warte bis KontakHinzugefügt
    @Override
    public boolean warteBisKontaktHinzugefuegt(String vorname, String nachname, int erwarteteAnzahlderKontakten){
        int rs = anzahlDerKontakten();
        while (rs!=erwarteteAnzahlderKontakten){
            driver.navigate().refresh();
            suche(vorname,nachname,null, null);
            rs = anzahlDerKontakten();
        }
        return true;

    }

    @Override
    public boolean warteBisKontaktGeloescht(String vorname, String nachname, int erwarteteAnzahlderKontakten){
        return warteBisKontaktHinzugefuegt(vorname, nachname , erwarteteAnzahlderKontakten);
    }

    @Override
    public boolean warteBisKontaktAktualisiert(String festNo, String email,
                                               String webseite, String mobilNo){
        return  warteBisFestNoAktualisiert(festNo) && warteBisEmailAktualisiert(email) &&
                warteBisWebseiteAktualisiert(webseite) && warteBisMobilNoAktualisiert(mobilNo);
    }

    @Override
    public boolean warteBisFestNoAktualisiert(String festNo){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(festnetzNummerField, festNo));
        return festnetzNummerField.isDisplayed();
    }

    @Override
    public boolean warteBisEmailAktualisiert(String email){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(emailField, email));
        return emailField.isDisplayed();
    }
    @Override
    public boolean warteBisMobilNoAktualisiert(String mobilNo){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(mobilNummerField, mobilNo));
        return mobilNummerField.isDisplayed();
    }
    @Override
    public boolean warteBisWebseiteAktualisiert(String webseite){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(webseiteField, webseite));
        return webseiteField.isDisplayed();
    }


    //                      Adressen

    @Override
    public int anzahlDerAdressen(){
        List<WebElement> adressenBlock = driver.findElements(By.cssSelector("li.address-item"));
        return adressenBlock.size();
    }

    @Override
    public boolean warteBisAdresseHinzugefuegt(String vorname, String nachname, int erwarteteAnzahlderAdressen){
        int rs = anzahlDerAdressen();
        while (rs!=erwarteteAnzahlderAdressen){
            driver.navigate().refresh();
            suche(vorname,nachname,null, null);
            rs = anzahlDerAdressen();
        }
        return true;
    }

    @Override
    public boolean warteBisAdresseAktualisiert(String vorname, String nachname,
                                               String Strase, String hausNo, String plz, String stadt, String bundesland){

        driver.navigate().refresh();
        suche(vorname,nachname,stadt,bundesland);
        return adresseFeld.getText().contains(Strase) &&
                adresseFeld.getText().contains(hausNo) &&
                adresseFeld.getText().contains(plz) &&
                adresseFeld.getText().contains(stadt) &&
                adresseFeld.getText().contains(bundesland);

    }

    @Override
    public boolean warteBisAdresseGeloescht(String vorname, String nachname, int erwarteteAnzahlderAdressen){
        return warteBisAdresseHinzugefuegt(vorname,nachname,erwarteteAnzahlderAdressen);
    }











    //              Index based button getters
    /**
     * Wählt die Bearbeiten Button von person in index PersonIndex
     * @param personIndex
     */
    @Override
    public void setPersonBearbeitenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +String.valueOf(personIndex)+ "]/div/button[1]";
        WebElement buttonBearbeiten = driver.findElement(By.xpath(xpath));
        buttonBearbeiten.click();
    }

    /**
     * Wählt and klickt die Löschen button von Person in index PersonIndex
     * @param personIndex
     */
    @Override
    public void setPersonLoeshcenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +String.valueOf(personIndex)+ "]/div/button[2]";
        WebElement loeschenButton = driver.findElement(By.xpath(xpath));
        loeschenButton.click();
    }

    @Override
    public void setAdresseBearbeitenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[1]/ul/li/div/button[1]" ;
        WebElement adresseBearbeitenButton  = driver.findElement(By.xpath(xpath));
        adresseBearbeitenButton.click();
    }
    @Override
    public void setAdresseHinzufuegenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[1]/ul/li/div/button[2]" ;
        WebElement adresseHinzufuegenButton  = driver.findElement(By.xpath(xpath));
        adresseHinzufuegenButton.click();
    }
    @Override
    public void setAdresseLoeschenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[1]/ul/li/div/button[3]" ;
        WebElement adresseLoeschenButton  = driver.findElement(By.xpath(xpath));
        adresseLoeschenButton.click();
    }

    @Override
    public void setKontaktBearbeitenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[2]/ul/li/div/button[1]";
    }

    @Override
    public void setKontaktHinzufuegenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[2]/ul/li/div/button[2]";
    }

    @Override
    public void setKontaktLoeschenButton(int personIndex){
        String xpath = "/html/body/app-root/app-person-home/section/ul/li[" +
                String.valueOf(personIndex) +
                "]/app-person/div[2]/ul/li/div/button[3]";
    }








}