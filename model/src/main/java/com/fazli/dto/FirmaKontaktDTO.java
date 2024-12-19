package com.fazli.dto;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public class FirmaKontaktDTO
 {
     private Long id;
     private List<String> festnetznummer;
     private List<String> email;
     private List<String> webseite;
     private List<String> faxnummer;

     public FirmaKontaktDTO(Long id, List<String> festnetznummern,
                            List<String> email,  List<String> webSeite , List<String> faxnummern) {
         this.id = id;
         this.festnetznummer = festnetznummern;
         this.email = email;
         this.faxnummer = faxnummern;
         this.webseite = webSeite;
     }

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public List<String> getEmail() {
         return email;
     }

     public void setEmail(List<String> email) {
         this.email = email;
     }

     public List<String> getFestnetznummer() {
         return festnetznummer;
     }

     public void setFestnetznummer(List<String> festnetznummer) {
         this.festnetznummer = festnetznummer;
     }

     public List<String> getWebseite() {
         return webseite;
     }

     public void setWebseite(List<String> webseite) {
         this.webseite = webseite;
     }

     public List<String> getFaxnummer() {
         return faxnummer;
     }

     public void setFaxnummer(List<String> faxnummer) {
         this.faxnummer = faxnummer;
     }

     @Override
     public boolean equals(Object o) {
         if (!(o instanceof FirmaKontaktDTO that)) return false;
         return Objects.equals(id, that.id) && Objects.equals(festnetznummer, that.festnetznummer) && Objects.equals(email, that.email) && Objects.equals(webseite, that.webseite) && Objects.equals(faxnummer, that.faxnummer);
     }

     @Override
     public int hashCode() {
         return Objects.hash(id, festnetznummer, email, webseite, faxnummer);
     }

     @Override
     public String toString() {
         return "FirmaKontaktDTO{" +
                 "id=" + id +
                 ", festnetznummer=" + festnetznummer +
                 ", email=" + email +
                 ", webseite=" + webseite +
                 ", faxnummer=" + faxnummer +
                 '}';
     }
 }
