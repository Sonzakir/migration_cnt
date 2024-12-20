package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebElement;

public interface PersonKontaktFormPage {
    void fülleFestnetznummer(String festnetznummer);

    void fülleEmail(String email);

    void fülleWebseite(String webseite);

    void fülleMobilnummer(String mobilnummer);

    PersonErgebnis speichern();

    PersonErgebnis bearbeiten(String festNo, String email, String webseite, String mobilnummer);

    //    Um namen Konflikte zu vermeiden, habe ich hinzufuegen methode wieder implementiert jedoch die Logik dahinter ist 1:1 zu bearbetien
    PersonErgebnis hinzufuegen(String festNo, String email, String webseite, String mobilnummer);

    boolean istSichtbar(WebElement element);
}
