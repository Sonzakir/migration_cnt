package com.fazli.dto;


import com.fazli.Adresse;

public class AdresseMapper {


    public AdresseDTO toDTO(Adresse adresse) {
        return new AdresseDTO(adresse.getId(), adresse.getStrasse(),adresse.getHausNo() ,adresse.getPlz()
                , adresse.getStadt(), adresse.getBundesland() );
    }

    public Adresse toAdresse(AdresseDTO adresseDTO){
        return new Adresse(adresseDTO.getId(), adresseDTO.getStrasse() ,adresseDTO.getHausNo() ,
                adresseDTO.getPlz(), adresseDTO.getStadt() , adresseDTO.getBundesland());
    }

    public Adresse toAdresse(AdresseRequestDTO adresseDTO){
        return new Adresse(adresseDTO.getStrasse() ,adresseDTO.getHausNo() ,
                adresseDTO.getPlz(), adresseDTO.getStadt() , adresseDTO.getBundesland());
    }

}
