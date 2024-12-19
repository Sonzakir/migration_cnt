package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonKontaktRequestDTO {

    private List<String> festnetznummer;
    private List<String> email ;
    private List<String> webseite;
    private List<String> mobilnummern;



    public List<String> getFestnetznummer() {
        return festnetznummer;
    }

    public void setFestnetznummer(List<String> festnetznummer) {
        this.festnetznummer = festnetznummer;
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

    public List<String> getMobilnummern() {
        return mobilnummern;
    }

    public void setMobilnummern(List<String> mobilnummern) {
        this.mobilnummern = mobilnummern;
    }

    @Override
    public String toString() {
        return "PersonKontaktRequestDTO{" +
                "festnetznummer=" + festnetznummer +
                ", email=" + email +
                ", webseite=" + webseite +
                ", mobilnummern=" + mobilnummern +
                '}';
    }
}
