package com.fazli;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
public class FirmaKontakt  {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "firma_kontakt_seq_gen")
    @SequenceGenerator(name = "firma_kontakt_seq_gen" , sequenceName = "firma_kontakt_seq" , allocationSize = 1)
    private Long id;
    @ElementCollection
    protected List<String> festnetznummer;
    @ElementCollection
    protected List<String> email;
    @ElementCollection
    protected List<String> webseite;
    @ElementCollection
    private List< String> faxnummer;
    public FirmaKontakt() {
    }

    public FirmaKontakt(Long id, List<String> festnetznummer, List<String> email,
                        List<String> webseite, List<String> faxnummer) {
        this.id = id;
        this.festnetznummer = festnetznummer;
        this.email = email;
        this.webseite = webseite;
        this.faxnummer = faxnummer;
    }

    public FirmaKontakt(List<String> festnetznummer, List<String> email,
                        List<String> webseite, List<String> faxnummer) {
        this.festnetznummer = festnetznummer;
        this.email = email;
        this.webseite = webseite;
        this.faxnummer = faxnummer;
    }

    public List<String> getFestnetznummer() {
        return festnetznummer;
    }

    public void setFestnetznummer(List<String> festnetznummer) {
        this.festnetznummer = festnetznummer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getFaxnummer() {
        return faxnummer;
    }


    public void setFaxnummer(List<String> faxNummern) {
        this.faxnummer = faxNummern;
    }


    public void eineFaxnummerHinzufuegen(String faxNummer) {
        this.faxnummer.add(faxNummer);
    }



    // possible solution get the id then call the neueKontakt hinzufuegen
    public void faxnummerAendern(String alte_fnummer,String neue_fnummer){
        Integer index = faxnummer.indexOf(alte_fnummer);
        this.faxnummer.remove(index);
        this.faxnummer.add(neue_fnummer);
    }

    public void loescheFaxnummern(){
        faxnummer.clear();
    }

    public void loescheEineFaxnummer(String loeschendeNummer){
        this.faxnummer.remove(loeschendeNummer);
    }



    public Long getId() {
        return id;
    }




    public List<String> getFestnetzNummer() {
        return festnetznummer;
    }

    public void setFestnetzNummer(List<String> festnetzNummer) {
        this.festnetznummer = festnetzNummer;
    }

    public void festnetzNummerHinzufuegen(String festnetzNummer) {
        this.festnetznummer.add(festnetzNummer);
    }

    public void eineFestnetznummerLoeschen(String festnetzNummer) {
        this.festnetznummer.remove(festnetzNummer);
    }
    public void alleFestnetznummerLoeschen() {
        this.festnetznummer.clear();
    }

    public void emailHinzufuegen(String email) {
        this.email.add(email);
    }


    public void setEmail(List<String> email) {
        this.email = email;
    }

    public void eineEmailLoeschen(String email) {
        this.email.remove(email);
    }
    public void alleEmailsLoeschen() {
        this.email.clear();
    }

    public List<String> getEmail() {
        return email;
    }

    public List<String> getWebseite() {
        return webseite;
    }

    public void setWebseite(List<String> webseite) {
        this.webseite = webseite;
    }
    public void webseiteHinzufuegen(String webseite) {
        this.webseite.add(webseite);
    }

    public void eineWebseiteLoeschen(String webseite) {
        this.webseite.remove(webseite);
    }
    public void alleWebseitenLoeschen() {
        this.webseite.clear();
    }

    @Override
    public String toString() {
        return "FirmaKontakt{" +
                "id=" + id +
                ", festnetznummer=" + festnetznummer +
                ", email=" + email +
                ", webseite=" + webseite +
                ", faxnummer=" + faxnummer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FirmaKontakt that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(festnetznummer, that.festnetznummer) && Objects.equals(email, that.email) && Objects.equals(webseite, that.webseite) && Objects.equals(faxnummer, that.faxnummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, festnetznummer, email, webseite, faxnummer);
    }
}
