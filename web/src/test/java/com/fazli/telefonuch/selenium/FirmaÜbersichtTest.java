package com.fazli.telefonuch.selenium;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class FirmaÜbersichtTest {

    private FirmaÜbersicht testSubject;
    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/firma");
        //        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

    }

    @BeforeMethod
    public void setupMethod(){
        testSubject = new FirmaÜbersicht(driver);
        var startseite = testSubject.anStartseiteWeiterleiten();
        testSubject = startseite.drückeFirmenHomePage();
//        driver.navigate().to("http://localhost:4200");
    }

    @AfterMethod
    public void tearDownMethod(){
        driver.navigate().to("http://localhost:4200/firma");
        testSubject.loescheAlleSichtbarenFirmen();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void test_startSeiteButton_sichtbar(){

        boolean exists = testSubject.istSichtbar(testSubject.homePageButton);

        Assert.assertTrue(exists);
    }

    @Test
    public void test_startSeiteButton_weiterleitet_positive(){

        testSubject.anStartseiteWeiterleiten();

        boolean weiterleitet = testSubject.zeigtStartSeiteAn();

        Assert.assertTrue(weiterleitet);

    }

    @Test
    public void test_personSucheButton_sichtbar(){

        boolean exists = testSubject.istSichtbar(testSubject.buttonPersonSuche);

        Assert.assertTrue(exists);
    }

    @Test
    public void test_personSucheButton_weiterleitet_positive(){
        testSubject.drückePersonSucheButton();

        boolean weiterleitet = testSubject.zeigtPersonSeiteAn();

        Assert.assertTrue(weiterleitet);
    }

    //              Firma Hinzufuegen
    @Test
    public void test_firmaHinzufuegen_neueFirma_positive(){
        String firmaname = "FirmaA" , strasse= "Bspstr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0187727272", email="emrerot@gmail.com", webseite ="" ,faxNummer ="016265262",
        branche="SOFTWARE";

        var firma = testSubject.hinzufuegen(firmaname,strasse,hausNo, plz, stadt, bundesland,
                festNummer,email,webseite,faxNummer, branche);

        boolean b = testSubject.warteBisFirmaDisplayed(firmaname);

//        firma.loeschen();
        Assert.assertTrue(b);

    }


    @Test
    public void test_firmaLoeschen_positive(){
        String firmaname = "FirmaB" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0187727272", email="firmab@gmail.com", webseite ="" ,faxNummer ="016265262",
        branche="SOFTWARE";
        var firma =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email, webseite,faxNummer,branche);

        boolean b =firma.loeschen();

        Assert.assertTrue(b);

    }

    //      Firmasuche
    @Test
    public void test_firmaSuche_sollteDieFirmaXFinden(){
        String firmaname = "FirmaX" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0187727272", email="firmab@gmail.com", webseite ="" ,faxNummer ="016265262",
                branche="SOFTWARE";
        var firmaX =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email, webseite,faxNummer,branche);
        firmaX.warteBisFirmaHinzugefuegt(0);

        Assert.assertEquals(1, firmaX.anzahlDerSichtbarenFirmen());

        firmaX.loeschen();

    }

    @Test
    public void test_firmaSuche_mussAlleFirmenAnzeigen(){

        int alleFirmen = testSubject.anzahlDerSichtbarenFirmen();

        testSubject.suche(null,null, null, null,null);

        Assert.assertEquals(alleFirmen, testSubject.anzahlDerSichtbarenFirmen());
    }



    @Test
    public void sollteFirmaZNameAendern(){
        Startseite startseite = new Startseite(driver);
        var firmaHomePage = startseite.drückeFirmenHomePage();
        String firmaname = "FirmaZ" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0187727272", email="firmab@gmail.com", webseite ="" ,faxNummer ="016265262",
                branche="SOFTWARE";
        var firmaZ =firmaHomePage.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email, webseite,faxNummer,branche);

        // FirmaName Aendern
        var neue = firmaZ.bearbeiten("NEUENAME",null,null,null,null,null,
                null,null,null,null,null);

        neue.warteBisFirmanameAktualisiert("NEUENAME");

        // assert
        firmaHomePage.suche("NEUENAME" , null, null, null,null);
        Assert.assertEquals(1, firmaHomePage.anzahlDerSichtbarenFirmen());
        neue.loeschen();


    }


    //                          FirmaKontakt
    @Test
    public void test_FirmaKontaktAendern_positive(){
        String firmaname = "Jupyter" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0248429234", email="juypter@gmail.com", webseite ="www.jupyter.com" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var firmaJupyter =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);

        String neueFestNo= "043249424" , neueEmail = "neu@gmail.com", neuewebseite = "www.neujuypter.com" , neuefaxNummer ="";

        firmaJupyter.firmaKontaktBearbeiten(neueFestNo,neueEmail,neuewebseite,neuefaxNummer);

        var ergebnis = testSubject.suche("Jupyter",null,null,null,null);

        boolean actual = ergebnis.warteBisKontaktAktualisiert(neueFestNo, neueEmail, neuewebseite, neuefaxNummer);

        ergebnis.loeschen();
        Assert.assertEquals(true, actual);

    }

    @Test
    public void test_FirmaKontaktHinzufuegen_positive(){
        String firmaname = "Angular" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0022355231", email="angular@gmail.com", webseite ="www.angular.dev" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var firmaAngular =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);
        String neueFestNo= "043249424" , neueEmail = "dev@gmail.com", neuewebseite = "www.angular.com" , neuefaxNummer ="";

        // KontaktHinzufuegen
        firmaAngular.firmaKontaktHinzufuegen(neueFestNo, neueEmail, neuewebseite, neuefaxNummer);
        firmaAngular = testSubject.suche(firmaname,null,null,null,null);

        boolean actual  = firmaAngular.warteBisKontaktHinzugefuegt(firmaname , 2);

        firmaAngular.loeschen();
        Assert.assertTrue(actual);

    }

    @Test
    public void test_FirmaKontaktLoeschen_positive(){
        String firmaname = "Github" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0022355231", email="github@gmail.com", webseite ="www.github.io" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var firmaGithub =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);

        firmaGithub.firmaKontaktLoeschen();

        boolean actual = firmaGithub.warteBisKontaktGeloescht(firmaname,0);

        Assert.assertTrue(actual, "Firma Kontakt wird nicht gelöscht.");
    }


    //                                  Adressen

    // Adresse hinzufügen

    @Test
    public void test_firmaAdresseHinzufuegen_NeueAdresse_positive(){
        String firmaname = "Youtube" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0022355231", email="youtube@gmail.com", webseite ="www.youtube.com" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var youtube =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);

//        youtube = testSubject.suche(firmaname,null,null,null,null);

        youtube.firmaAdresseHinzufuegen("Kennedyplt.","5","34452","Essen" , "Nordrhein-Westfallen");

        Assert.assertTrue(youtube.warteBisAdresseHinzugefuegt(firmaname,2));
    }



    // Adresse aendern
    @Test
    public void test_firmaAdresseAendern_positive(){
        String firmaname = "Alpha" , strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0022355231", email="youtube@gmail.com", webseite ="www.youtube.com" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var alpha =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);

        alpha.firmaAdresseBearbeiten("str2", "45","46463","Berlin","Berlin");

        boolean actual = alpha.warteBisAdresseAktualisiert(firmaname,"str2", "45","46463","Berlin","Berlin");

        Assert.assertTrue(actual, "Probleme bei der Adressebearbeitung.");
    }


    // Adresse Loeschen
    @Test
    public void test_firmaAdresseLoeschen_positive(){
        String firmaname = "McDonalds" ,
                strasse= "Neustr." , hausNo= "2", plz="22233",stadt = "Berlin" , bundesland = "Berlin";
        String festNummer = "0022355231", email=",mcking@gmail.com", webseite ="www.mcdonalds.com" ,faxNummer ="020340203",
                branche="SOFTWARE";
        var mcdonalds =testSubject.hinzufuegen(firmaname,strasse, hausNo, plz, stadt, bundesland, festNummer, email,
                webseite,faxNummer,branche);

        // adresse loeschen
        mcdonalds.firmaAdresseLoeschen();

        Assert.assertTrue(mcdonalds.warteBisAdresseGeloescht(firmaname,0));

    }





}
