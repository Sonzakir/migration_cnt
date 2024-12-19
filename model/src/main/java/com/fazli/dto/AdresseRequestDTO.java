package com.fazli.dto;

import lombok.Builder;

@Builder
public class AdresseRequestDTO {
    private String strasse;
    private String hausNo;
    private String plz;
    private String stadt;
    private String bundesland;

    public AdresseRequestDTO( String strasse,String hausNo, String plz, String stadt, String bundesland) {
        this.hausNo = hausNo;
        this.strasse = strasse;
        this.plz = plz;
        this.stadt = stadt;
        this.bundesland = bundesland;
    }

    public AdresseRequestDTO() {
    }

    public String getHausNo() {
        return hausNo;
    }

    public void setHausNo(String hausNo) {
        this.hausNo = hausNo;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }
}
