package com.fazli.telefonuch.selenium;

public interface PersonHinzufuegenPage {
    void fülleVorname(String vorname);

    void fülleNachname(String nachname);

    void fülleAdresse(String strasse, String hausNo,
                      String plz, String stadt, String bundesland);

    void fülleKontakt(String festnetznummer, String email, String webseite, String mobilnummer);

    PersonErgebnis speichern();

    PersonErgebnis hinzufuegen(String vorname, String nachname, String strasse, String hausNo, String plz, String stadt, String bundesland, String festNo, String email, String webseite, String mobilno);
}
