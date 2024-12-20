package com.fazli.telefonuch.selenium;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class PersonÜbersichtTest {


    private PersonÜbersicht testSubject;

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/person");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    }

    @BeforeMethod
    public void setupMethod(){
        testSubject = new PersonÜbersicht(driver);
        var startseite = testSubject.anStartSeiteWeiterleiten();
        testSubject = startseite.drückePersonenHomePage();
//        driver.navigate().to("http://localhost:4200/person");
    }

    @AfterMethod
    public void tearDownMethod(){
        driver.navigate().to("http://localhost:4200/person");
        testSubject.loescheAlleSichtbarenPersonen();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }





    //                      Startseite



    @Test
    public void sollteStartSeiteButtonAngezeigt(){

        boolean exist = testSubject.istSichtbar(testSubject.spanDE_Telefonbuch);

        Assert.assertTrue(exist , "StartSeite Button wird nicht angezeigt");


    }


    @Test
    public void sollteStartSeiteWeiterleiten(){

        testSubject.anStartSeiteWeiterleiten();
        boolean weiterleitet = testSubject.zeigtStartSeiteAn();

        Assert.assertTrue(weiterleitet , "Button leitet nicht an Startseite weiter.");
    }



    @Test
    public void sollteFirmSucheButtonAngezeigt(){
        // Existenz
        boolean exists = testSubject.istSichtbar(testSubject.buttonFirmaSuche);

        Assert.assertTrue(exists , "Firma Suche wird nicht angezeigt");

    }



    @Test
    public void test_zeigtFirmaSeiteAn_sollteFirmaÜbersichtSeiteWeiterleiten(){
        // click
        testSubject.drückeFirmaSuche();
        boolean weiterleitet = testSubject.zeigtFirmaSeiteAn();

        Assert.assertTrue(weiterleitet , "Firma Suche button leitet nicht an Firma Seite weiter");
    }





  //                           Person HINZUFUEGEN



    @Test
    public void test_personHinzufuegen_positive(){
        // Person Attributen
        String vorname= "Emre" , nachname = "Rot";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="emrerot@gmail.com", webseite ="" ,mobilnummer ="016265262";

        var ergebnis= testSubject.hinzufuegen(vorname, nachname, strasse,hausNo,plz, stadt, bundesland, festNummer,
                email, webseite, mobilnummer);

        boolean b = testSubject.warteBisPersonDisplayedWhileLoop("Emre", "Rot");

        Assert.assertTrue(b);
        ergebnis.loeschen();
    }





    //              PERSON LOESCHEN


    @Test
    public void test_personLoeschen_positive() {
        // Zuerst, Person Hinzufügen
        String vorname= "Albert" , nachname = "Einstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="", webseite ="" ,mobilnummer ="016265262";
        testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,festNummer,email,webseite,mobilnummer);
        var albert = testSubject.suche("Albert" , null,null, null);


        boolean b = albert.loeschen();

        Assert.assertTrue(b);
    }






    //                  PERSONSUCHE






    @Test
    public void test_personSuche_mitNamen_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Albert" , nachname = "Einstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="", webseite ="" ,mobilnummer ="016265262";
        testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,festNummer,email,webseite,mobilnummer);

        // suchen
        var albert = testSubject.suche("Albert" , null,null, null);

        // assert
        Assert.assertEquals( 1, testSubject.anzahlDerSichtbarenPersonen());


    }




    @Test
    public void test_personSuche_mitNamen_negative(){

        testSubject.suche("NICHT EXISTIERENDE VORNAME" , null,null, null);

        Assert.assertEquals( 0, testSubject.anzahlDerSichtbarenPersonen());
    }



    @Test
    public void test_personSuche_mussAllePersonenAnzeigen_positive(){

        int allePersonen = testSubject.anzahlDerSichtbarenPersonen();

        testSubject.suche(null , null,null, null);

        Assert.assertEquals( allePersonen, testSubject.anzahlDerSichtbarenPersonen());
    }



    @Test
    public void test_personBearbeiten_mitNamen_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Albert" , nachname = "Einstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="", webseite ="" ,mobilnummer ="016265262";
        testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,festNummer,email,webseite,mobilnummer);
        var albert = testSubject.suche("Albert" , null,null, null);

        //Person Aendern
        albert.bearbeiten("Erik","Sawade",null,null,null,null,null,null,null,null,null);

        // assert
        testSubject.suche("Erik" ,"Sawade",null,null);
        Assert.assertEquals(1 , testSubject.anzahlDerSichtbarenPersonen());



    }









    @Test
    public void test_personKontaktBearbeiten_positive()  {
        // Zuerst, Person Hinzufügen
        String vorname= "Günther" , nachname = "Einstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="", webseite ="" ,mobilnummer ="016265262";
        var günther = testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,festNummer,email,webseite,mobilnummer);


        // Kontakt Bearbeiten
        günther.personKontaktBearbeiten("0101001012","","www.alber.com","");
        günther = testSubject.suche(vorname , null,null, null);


        boolean actual = günther.warteBisKontaktAktualisiert("0101001012","", "www.alber.com" ,"");


        // assert
        Assert.assertTrue(actual , "Probleme bei der KontaktBearbeitung");

    }


    @Test
    public void test_personKontaktHinzufuegen_NeueKontakt_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Albert" , nachname = "Einstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        var albert = testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,null,null,null,null);
//        albert = testSubject.suche("Albert" , null,null, null);

        // KontaktHinzufuegen
        albert.personKontaktHinzufuegen("034234234","albert@email.com","www.albert.com","03472374234");


        Assert.assertTrue(albert.warteBisKontaktHinzugefuegt("Albert","Einstein",2));


    }


    // personKontakt Loeschen
    @Test
    public void test_personKontaktLoeschen_loeschtKontakt_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Soner" , nachname = "Kiraz";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        String festNummer = "0187727272", email="", webseite ="" ,mobilnummer ="016265262";
        testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,festNummer,email,webseite,mobilnummer);

        var soner = testSubject.suche(vorname , nachname,null, null);

        soner.personKontaktLoeschen();

        boolean actual = soner.warteBisKontaktGeloescht(vorname,nachname,0);

        Assert.assertTrue(actual, "Person Kontakt wird nicht gelöscht");
    }




    //                      PERSON ADRESSE
    //  Adresse hinzufügen
    @Test
    public void test_personAdresseHinzufuegen_NeueAdresse_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Jörg" , nachname = "Jäger";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        var jörg = testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,null,null,null,null);
//        jörg = testSubject.suche(vorname , null,null, null);

        // adresse hinzufuegen
        jörg.personAdresseHinzufuegen("Kennedyplt.","5","34452","Essen" , "Nordrhein-Westfallen");



        Assert.assertTrue(jörg.warteBisAdresseHinzugefuegt(vorname,nachname,2));


    }




    // Adresse aendern
    @Test
    public void test_personAdresseAendern_positive(){
        var neuePerson = testSubject.hinzufuegen("Maria","Sawade",
                "str1","2", "27723", "Berlin", "Berlin", null,null,null,null);

        var neue2 = neuePerson.personAdresseBearbeiten("str2", "45","46463","Berlin","Berlin");

        var neue3 = neue2.suche("Maria","Sawade",null,null);

        // adresse bearbeiten
        boolean actual = neue3.warteBisAdresseAktualisiert("Maria","Sawade",
                "str2", "45","46463","Berlin","Berlin");

        Assert.assertTrue(actual , "Probleme bei der AdresseBearbeitung");

    }


    // Adresse Loeschen
    @Test
    public void test_personAdresseLoeschen_positive(){
        // Zuerst, Person Hinzufügen
        String vorname= "Gabrielle" , nachname = "Eldstein";
        String strasse = "Neustr.", hausNo = "22", plz="33312", stadt = "Berlin", bundesland = "Berlin";
        var gabriella = testSubject.hinzufuegen(vorname,nachname,strasse,hausNo,plz,
                stadt,bundesland,null,null,null,null);
        gabriella = testSubject.suche(vorname, null,null, null);

        // adresse loeschen
        gabriella.personAdresseLoeschen();



        Assert.assertTrue(gabriella.warteBisAdresseGeloescht(vorname,nachname,0));



    }


}
