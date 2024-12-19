package com.fazli.dto;


import com.fazli.FirmaKontakt;

public class FirmaKontaktDTOMapper  {



    public FirmaKontaktDTO toDTO(FirmaKontakt kontakt) {
        return new FirmaKontaktDTO(
                kontakt.getId(),
                kontakt.getFestnetzNummer() ,
                kontakt.getEmail(),
                kontakt.getWebseite(),
                kontakt.getFaxnummer()

        );
    }

    public FirmaKontakt toFirmaKontakt(FirmaKontaktDTO dto) {
        return new FirmaKontakt(
                dto.getId(),
                dto.getFestnetznummer(),
                dto.getEmail(),
                dto.getWebseite(),
                dto.getFaxnummer()
        );
    }
    public FirmaKontakt toFirmaKontakt(FirmaKontaktRequestDTO dto) {
        return new FirmaKontakt(
                dto.getFestnetznummer(),
                dto.getEmail(),
                dto.getWebseite(),
                dto.getFaxnummer()
        );
    }

}