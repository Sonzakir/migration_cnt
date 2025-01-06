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

    @Mapping(target = "branche", source = "branchen", qualifiedByName = "brancheToStringList")
    FirmaDTO toDTO(Firma firma);

    @Mapping(target = "branchen", source = "branche", qualifiedByName = "stringListToBranche")
    Firma toFirma(FirmaDTO firmaDTO);

    @Mapping(target = "branchen", source = "branche", qualifiedByName = "stringListToBranche")
    Firma toFirma(FirmaRequestDTO firmaRequestDTO);

    @Named("brancheToStringList")
    static List<String> brancheToStringList(List<Branche> branchen) {
        return branchen.stream().map(Branche::name).collect(Collectors.toList());
    }

    @Named("stringListToBranche")
    static List<Branche> stringListToBranche(List<String> branche) {
        return branche.stream().map(s -> Branche.valueOf(s.toUpperCase())).collect(Collectors.toList());
    }
}
