package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebElement;

public interface PersonSucheLeistePage {
    boolean istSichtbar(WebElement element);

    void fülleVorname(String vorname);

    void fülleNachname(String nachname);

    void fülleStadt(String stadt);

    void fülleBundesland(String bundesland);

    PersonErgebnis drückeSuche();

    PersonErgebnis suche(String vorname, String nachname, String stadt, String bundesland);
}
