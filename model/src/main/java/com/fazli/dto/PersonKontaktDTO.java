package com.fazli.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Builder
public class PersonKontaktDTO{

    private Long id ;
    private List<String> festnetznummer;
    private List<String> email ;
    private List<String> webseite;
    private List<String> mobilnummern;

    public PersonKontaktDTO(Long id, List<String> festnetzNummer, List<String> email,
                            List<String> webSeite, List<String> mobilnummern) {
        this.id = id;
        this.festnetznummer = festnetzNummer;
        this.email = email;
        this.webseite = webSeite;
        this.mobilnummern = mobilnummern;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
    public boolean equals(Object o) {
        if (!(o instanceof PersonKontaktDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(festnetznummer, that.festnetznummer) && Objects.equals(email, that.email) && Objects.equals(webseite, that.webseite) && Objects.equals(mobilnummern, that.mobilnummern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, festnetznummer, email, webseite, mobilnummern);
    }

    @Override
    public String toString() {
        return "PersonKontaktDTO{" +
                "id=" + id +
                ", festnetznummer=" + festnetznummer +
                ", email=" + email +
                ", webseite=" + webseite +
                ", mobilnummern=" + mobilnummern +
                '}';
    }
}
