package com.fazli.dto;



import com.fazli.Branche;
import com.fazli.Firma;

import java.util.stream.Collectors;


public class FirmaDTOMapper  {
    private final AdresseMapper adresseMapper;
    private final FirmaKontaktDTOMapper firmaKontaktDTOMapper;

    public FirmaDTOMapper( AdresseMapper adresseDTOMapper,
                          FirmaKontaktDTOMapper firmaKontaktDTOMapper) {
        this.adresseMapper = adresseDTOMapper;
        this.firmaKontaktDTOMapper = firmaKontaktDTOMapper;
    }

   public FirmaDTO toDTO(Firma firma) {
       return new FirmaDTO(
               firma.getId(),
               firma.getName(),
               firma.getAdresse().stream()
                       .map(adresseMapper::toDTO)
                       .collect(Collectors.toList()),
               firma.getBranchen().stream()
                       .map(Branche::name)
                       .collect(Collectors.toList()) ,
               firma.getKontaktList().stream()
                       .map(firmaKontaktDTOMapper::toDTO)
                       .collect(Collectors.toList()));
   }

   public Firma toFirma(FirmaDTO firmaDTO) {
        return new Firma(
                firmaDTO.getId(),
                firmaDTO.getName(),
                firmaDTO.getAdresse().stream()
                        .map(adresseMapper::toAdresse)
                        .collect(Collectors.toList()),
                firmaDTO.getBranche().stream().map(Branche::valueOf).collect(Collectors.toList()) ,
                firmaDTO.getKontaktList().stream()
                        .map(firmaKontaktDTOMapper::toFirmaKontakt)
                        .collect(Collectors.toList())

        );
   }
    public Firma toFirma(FirmaRequestDTO firmaDTO) {
        return new Firma(
                firmaDTO.getName() ,
                firmaDTO.getAdresse().stream()
                        .map(adresseMapper::toAdresse)
                        .collect(Collectors.toList()),
                firmaDTO.getBranche().stream().map(s->Branche.valueOf(s.toUpperCase())).collect(Collectors.toList()) ,
                firmaDTO.getKontaktList().stream()
                        .map(firmaKontaktDTOMapper::toFirmaKontakt)
                        .collect(Collectors.toList())

        );
    }

}
