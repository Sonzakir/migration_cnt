package com.fazli.telefonuch.selenium;

public interface AdresseFormPage extends Page {
    void fülleStrasse(String strasse);
    void fülleHausNo(String hausNo);
    void füllePLZ(String plz);
    void fülleStadt(String stadt);
    void fülleBundesland(String bundesland);
    PersonErgebnis bearbeiten(String strasse, String hausNo,
                              String plz, String stadt, String bundesland);

    PersonErgebnis speichern();
    PersonErgebnis hinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland);

    //firma
    FirmaErgebnis firmaBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland);
    FirmaErgebnis firmaSpeichern();
    FirmaErgebnis firmaHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland);
    String getStrasseFirstAdresse();
    String getHausNoFirstAdresse();
    String getPlzFirstAdresse();
    String getStadtFirstAdresse();
    String getBundeslandFirstAdresse();
}
