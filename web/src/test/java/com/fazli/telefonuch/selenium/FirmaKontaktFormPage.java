package com.fazli.telefonuch.selenium;

public interface FirmaKontaktFormPage extends Page {
    void fülleFestnetznummer(String festnetznummer);
    void fülleEmail(String email);
    void fülleWebseite(String webseite);
    void fülleFaxNo(String faxNo);

    FirmaErgebnis bearbeiten(String festNo, String email, String webseite, String faxNo);

    FirmaErgebnis hinzufuegen(String festNo, String email, String webseite, String faxNo);

    FirmaErgebnis speichern();
}
