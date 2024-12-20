package com.fazli.telefonuch.selenium;

public interface FirmaHinzufuegenPage extends Page{
    void fülleFirmaname(String firmaname);
    void fülleAdresse(String strasse, String hausnummer, String plz, String stadt, String bundesland);
    void fülleKontakt(String festnetznummer, String email, String webseite, String faxNo);
    FirmaErgebnis speichern();
    FirmaErgebnis hinzufuegen(String firmaname,
                               String strasse, String hausNo , String plz, String stadt, String bundesland,
                               String festNo, String email, String webseite, String faxNo,
                              String branche);
    void fülleBranche(String branche);


}
