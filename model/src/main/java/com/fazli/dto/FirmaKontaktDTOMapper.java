package com.fazli.dto;

import com.fazli.FirmaKontakt;
import org.mapstruct.Mapper;

@Mapper
public interface FirmaKontaktDTOMapper {

    FirmaKontaktDTO toDTO(FirmaKontakt kontakt);
    FirmaKontakt toFirmaKontakt(FirmaKontaktDTO dto);
    FirmaKontakt toFirmaKontakt(FirmaKontaktRequestDTO dto);

}