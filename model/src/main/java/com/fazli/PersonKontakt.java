package com.fazli;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.Objects;


@Entity
@Builder
public class PersonKontakt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "person_kontakt_gen")
    @SequenceGenerator(name = "person_kontakt_gen" , sequenceName = "person_kontakt_seq" , allocationSize = 1)
    private Long id;
    //@Nullable()
    @ElementCollection
    protected List<String> festnetznummer;
    @ElementCollection
    protected List<String> email;
    @ElementCollection
    protected List<String> webseite;
    @ElementCollection
    private List<String> mobilNummern;

    public PersonKontakt() {}

    public PersonKontakt(Long id, List<String> festnetznummer, List<String> email,
                         List<String> webseite, List<String> mobilNummern) {
        this.id = id;
        this.festnetznummer = festnetznummer;
        this.email = email;
        this.webseite = webseite;
        this.mobilNummern = mobilNummern;
    }

    public PersonKontakt(List<String> festnetznummer, List<String> email,
                         List<String> webseite, List<String> mobilNummern) {
        this.festnetznummer = festnetznummer;
        this.email = email;
        this.webseite = webseite;
        this.mobilNummern = mobilNummern;
    }

    public List<String> getMobilNummern() {
        return mobilNummern;
    }

    public void setMobilNummern(List<String> mobilNummern) {
        this.mobilNummern = mobilNummern;
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
    public boolean equals(Object o) {
        if (!(o instanceof PersonKontakt kontakt)) return false;
        return Objects.equals(id, kontakt.id) && Objects.equals(festnetznummer, kontakt.festnetznummer) && Objects.equals(email, kontakt.email) && Objects.equals(webseite, kontakt.webseite) && Objects.equals(mobilNummern, kontakt.mobilNummern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, festnetznummer, email, webseite, mobilNummern);
    }

    @Override
    public String toString() {
        return "PersonKontakt{" +
                "id=" + id +
                ", festnetznummer=" + festnetznummer +
                ", email=" + email +
                ", webseite=" + webseite +
                ", mobilNummern=" + mobilNummern +
                '}';
    }
}

