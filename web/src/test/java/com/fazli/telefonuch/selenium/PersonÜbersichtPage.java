package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebElement;

public interface PersonÜbersichtPage {

    boolean istSichtbar(WebElement element);

    Integer anzahlDerSichtbarenPersonen();

    FirmaÜbersichtPage drückeFirmaSuche();

    boolean zeigtFirmaSeiteAn();

    boolean zeigtStartSeiteAn();

    Startseite anStartSeiteWeiterleiten();

    PersonSucheLeiste getSucheLeiste();

    PersonErgebnis suche(String vorname, String nachname,
                         String stadt, String bundesland);

    PersonHinzufuegen getPersonForm();

    PersonErgebnis hinzufuegen(
            String vorname, String nachname,
            String strasse, String hausNo, String plz, String stadt, String bundesland,
            String festNo, String email, String webseite, String mobilno
    );

    // Methode zu warten und überprüfen ob die Person hinzugefügt wird
    boolean warteBisPersonDisplayed(String vorname, String nachname);

    // warten mit while loop
    boolean warteBisPersonDisplayedWhileLoop(String vorname, String nachname);

    boolean loescheAlleSichtbarenPersonen();

    boolean zeigtHinzufuegenFormAn();
    PersonErgebnis getErgebnis();


}
