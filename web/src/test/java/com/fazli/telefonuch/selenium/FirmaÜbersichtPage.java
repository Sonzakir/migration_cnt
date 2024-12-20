package com.fazli.telefonuch.selenium;

public interface FirmaÜbersichtPage extends Page{
    Integer anzahlDerSichtbarenFirmen();
    PersonÜbersicht drückePersonSucheButton();
    boolean zeigtPersonSeiteAn();
    boolean zeigtStartSeiteAn();
    Startseite anStartseiteWeiterleiten();

    FirmaSucheLeiste getSucheLeiste();
    FirmaErgebnis getErgebnis();
    FirmaErgebnis suche(String Firmaname , String plz, String stadt , String bundesland, String branche);
    FirmaErgebnis hinzufuegen(String firmaname, String str, String hausNo, String plz , String stadt, String bundesland ,
                              String festNo, String email, String webseite , String fax,
                              String branche);
    FirmaHinzufuegen getFirmaForm();
    boolean warteBisFirmaDisplayed(String firmaname);
    boolean loescheAlleSichtbarenFirmen();

}
