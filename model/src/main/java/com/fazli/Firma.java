package com.fazli;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Firma  {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "firma_seq_gen")
    @SequenceGenerator(name = "firma_seq_gen", sequenceName = "firma_seq" , allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<Adresse> adresse;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    private List<Branche> branchen;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FirmaKontakt> kontaktList;



    public Firma(String name,  List<Adresse> adresse,List<Branche> branchen, List<FirmaKontakt> kontaktList) {
        this.name = name;
        this.branchen = branchen;
        this.adresse = adresse;
        this.kontaktList = kontaktList;
    }






    public void setAdresse(List<Adresse> adresse) {
        // Safe Setter
        this.adresse = ListUril.setSafe(this.adresse, adresse);
    }

    public void setKontaktList(List<FirmaKontakt> kontaktList) {
        // Safe Setter
        this.kontaktList = ListUril.setSafe(this.kontaktList, kontaktList);
    }


    public void setBranchen(List<Branche> branchen) {
        // Branche safe set
        this.branchen = ListUril.setSafe(this.branchen, branchen);
    }





}



