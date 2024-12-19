package com.fazli;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq_gen")
    @SequenceGenerator(name = "person_seq_gen" , sequenceName = "person_seq" , allocationSize = 1)
    private Long id;
    private String vorname;
    private String nachname;


    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<Adresse> adresse;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<PersonKontakt> kontaktList;




    public Person(String vorname, String nachname, List<Adresse> adresse, List<PersonKontakt> kontaktList) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.adresse = adresse;
        this.kontaktList = kontaktList;
    }


    //  setter



    public void setAdresse(List<Adresse> adresse) {
        this.adresse = ListUril.setSafe(this.adresse, adresse);
    }

    public void setKontaktList(List<PersonKontakt> kontaktList) {
        // SAFE SETTER !!!
        this.kontaktList = ListUril.setSafe(this.kontaktList, kontaktList);
    }

}

