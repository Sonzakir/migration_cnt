package com.fazli.telefonuch.selenium;

import org.openqa.selenium.WebElement;

public interface FirmaErgebnisPage {
    FirmaErgebnis bearbeiten(String firmaname, String str, String hausNo, String plz, String stadt, String bundesland,
                             String festNo, String email, String webseite, String faxNo, String branche);

    boolean loeschen();

    boolean warteBisFirmaHinzugefuegt(int anzahlBevor);

    boolean warteBisFirmaGeloescht();

    boolean warteBisFirmanameAktualisiert(String firmaname);

    int anzahlDerSichtbarenFirmaKontakten();

    FirmaErgebnis suche(String firmaname, String plz, String stadt, String bundesland, String branche);

    FirmaSucheLeiste getSucheLeiste();

    FirmaHinzufuegen getFirmaForm();

    int anzahlDerSichtbarenFirmen();

    FirmaÜbersicht getFirmaÜbersicht();


    //              kontakt

    FirmaErgebnis firmaKontaktBearbeiten(String festNo, String email, String webseite, String faxNo);

    FirmaErgebnis firmaKontaktHinzufuegen(String festNo, String email, String webseite, String faxNo);

    FirmaKontaktForm getFirmaKontaktForm();

    FirmaErgebnis firmaKontaktLoeschen();

    boolean warteBisKontaktGeloescht(String firmaname, int anzahlDerErwartetenKontakten);

    boolean warteBisKontaktAktualisiert(String festNo, String email, String webseite, String faxNo);

    boolean warteBisKontaktHinzugefuegt(String firmaname, int anzahlDerErwartetenKontakten);

    //            adresse

    FirmaErgebnis firmaAdresseBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland);

    FirmaErgebnis firmaAdresseHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland);

    FirmaErgebnis firmaAdresseLoeschen();

    AdresseForm getAdresseForm();


    //  Webelementen

    boolean istSichtbar(WebElement element);
}
