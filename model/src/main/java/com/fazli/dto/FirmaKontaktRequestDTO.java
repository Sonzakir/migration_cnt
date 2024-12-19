package com.fazli.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class FirmaKontaktRequestDTO {
    private List<String> festnetznummer;
    private List<String> email;
    private List<String> webseite;
    private List<String> faxnummer;

    public FirmaKontaktRequestDTO() {
    }

    public FirmaKontaktRequestDTO(List<String> festnetznummer, List<String> email,
                                  List<String> webseite, List<String> faxnummer) {
        this.festnetznummer = festnetznummer;
        this.email = email;
        this.webseite = webseite;
        this.faxnummer = faxnummer;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getWebseite() {
        return webseite;
    }

    public void setWebseite(List<String> webseite) {
        this.webseite = webseite;
    }

    public List<String> getFestnetznummer() {
        return festnetznummer;
    }

    public void setFestnetznummer(List<String> festnetznummer) {
        this.festnetznummer = festnetznummer;
    }

    public List<String> getFaxnummer() {
        return faxnummer;
    }

    public void setFaxnummer(List<String> faxnummer) {
        this.faxnummer = faxnummer;
    }

    @Override
    public String toString() {
        return "FirmaKontaktRequestDTO{" +
                "festnetznummer=" + festnetznummer +
                ", email=" + email +
                ", webseite=" + webseite +
                ", faxnummer=" + faxnummer +
                '}';
    }
}

