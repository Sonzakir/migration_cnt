package com.fazli.dto;

import com.fazli.Branche;
import com.fazli.Firma;
import com.fazli.dto.FirmaDTO;
import com.fazli.dto.FirmaRequestDTO;
import com.fazli.dto.AdresseDTO;
import com.fazli.dto.AdresseRequestDTO;
import com.fazli.dto.FirmaKontaktDTO;
import com.fazli.dto.FirmaKontaktRequestDTO;
import org.mapstruct.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper( uses = {AdresseMapper.class, FirmaKontaktDTOMapper.class})
public interface FirmaDTOMapper {

    @Mapping(target = "branche", source = "branchen")
    FirmaDTO toDTO(Firma firma);

    @Mapping(target = "branchen", source = "branche" )
    Firma toFirma(FirmaDTO firmaDTO);

    @Mapping(target = "branchen", source = "branche")
    Firma toFirma(FirmaRequestDTO firmaRequestDTO);

    String map (Branche branche);
    Branche map (String s);

}
