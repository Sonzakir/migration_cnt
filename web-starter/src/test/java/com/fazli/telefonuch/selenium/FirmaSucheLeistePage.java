package com.fazli.telefonuch.selenium;

public interface FirmaSucheLeistePage extends Page {
    void fülleFirmaName(String firmaname);
    void füllePLZ(String plz);
    void fülleStadt(String stadt);
    void fülleBundesland(String bundesland);
    void fülleBranche(String branche);
    FirmaErgebnis drückeSuche();
    FirmaErgebnis suche(String firmaname, String plz, String stadt, String bundesland, String branche);

}
