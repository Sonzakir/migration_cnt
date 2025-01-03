package com.fazli;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Entity
@Builder
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "adresse_seq_gen")
    @SequenceGenerator(name="adresse_seq_gen" , sequenceName = "adresse_seq" , allocationSize = 1)
    private Long id;



    private String strasse;
    private String hausNo;
    private final String plz;
    private final String stadt;
    private final String bundesland;


    public Adresse(){
        this.plz="";
        this.stadt="";
        this.bundesland="";
    }

    public Adresse(String strasse, String hausNo, String plz, String Stadt, String bundesland ) {
        this.strasse = strasse;
        this.bundesland = bundesland;
        this.plz = plz;
        this.stadt = Stadt;
        this.hausNo = hausNo;
    }

    public Adresse(Long id, String strasse, String hausNo, String plz, String stadt, String bundesland) {
        this.id = id;
        this.strasse = strasse;
        this.hausNo = hausNo;
        this.plz = plz;
        this.stadt = stadt;
        this.bundesland = bundesland;
    }




}
