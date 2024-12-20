package com.fazli.telefonuch.selenium;

public interface PersonErgebnisPage {
    int anzahlDerSichtbarenPersonen();

    PersonHinzufuegen getPersonForm();

    PersonErgebnis bearbeiten(
            String vorname, String nachname,
            String strasse, String hausNo, String plz, String stadt, String bundesland,
            String festnetzNo, String email, String webseite, String mobilNo
    );

    PersonÜbersicht getPersonÜbersicht();

    PersonÜbersicht drückeLoeschenButton();

    boolean loeschen();

    boolean warteBisPersonGeloescht();

    PersonErgebnis loeschenMitErgebnis();

    PersonKontaktForm getPersonKontaktForm();

    PersonErgebnis personKontaktBearbeiten(String festNo, String email,
                                           String webseite, String mobilNo);

    // personKontakt hinzufuege
    PersonErgebnis personKontaktHinzufuegen(String festNo, String email, String webseite, String mobilNo);

    PersonErgebnis personKontaktLoeschen();

    AdresseForm getAdresseForm();

    PersonErgebnis personAdresseBearbeiten(String strasse, String hausNo, String plz, String stadt, String bundesland);

    PersonErgebnis personAdresseHinzufuegen(String strasse, String hausNo, String plz, String stadt, String bundesland);

    PersonErgebnis personAdresseLoeschen();

    String getPersonVornameField();

    String getPersonNachnameField();

    String getPersonFestNo();

    String getPersonEmail();

    String getPersonWebseite();

    String getPersonMobilNo();

    boolean KontaktEquals(String festNo, String email, String webseite, String mobilNo);

    PersonSucheLeiste getSucheLeiste();

    PersonErgebnis suche(String vorname, String nachname, String stadt, String bundesland);

    int anzahlDerKontakten();

    // Warte bis KontakHinzugefügt
    boolean warteBisKontaktHinzugefuegt(String vorname, String nachname, int erwarteteAnzahlderKontakten);

    boolean warteBisKontaktGeloescht(String vorname, String nachname, int erwarteteAnzahlderKontakten);

    boolean warteBisKontaktAktualisiert(String festNo, String email,
                                        String webseite, String mobilNo);

    boolean warteBisFestNoAktualisiert(String festNo);

    boolean warteBisEmailAktualisiert(String email);

    boolean warteBisMobilNoAktualisiert(String mobilNo);

    boolean warteBisWebseiteAktualisiert(String webseite);

    int anzahlDerAdressen();

    boolean warteBisAdresseHinzugefuegt(String vorname, String nachname, int erwarteteAnzahlderAdressen);

    boolean warteBisAdresseAktualisiert(String vorname, String nachname,
                                        String Strase, String hausNo, String plz, String stadt, String bundesland);

    boolean warteBisAdresseGeloescht(String vorname, String nachname, int erwarteteAnzahlderAdressen);

    void setPersonBearbeitenButton(int personIndex);

    void setPersonLoeshcenButton(int personIndex);

    void setAdresseBearbeitenButton(int personIndex);

    void setAdresseHinzufuegenButton(int personIndex);

    void setAdresseLoeschenButton(int personIndex);

    void setKontaktBearbeitenButton(int personIndex);

    void setKontaktHinzufuegenButton(int personIndex);

    void setKontaktLoeschenButton(int personIndex);
}
