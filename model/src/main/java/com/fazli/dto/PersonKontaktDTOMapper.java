package com.fazli.dto;


import com.fazli.PersonKontakt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PersonKontaktDTOMapper {
    @Mapping(source = "festnetzNummer", target = "festnetznummer")
    @Mapping(target = "mobilnummern" , source = "mobilNummern")
    PersonKontaktDTO toDTO(PersonKontakt personKontakt);

    @Mapping(source = "mobilnummern", target = "mobilNummern")
    PersonKontakt toPersonKontakt(PersonKontaktDTO personKontaktDTO);

    @Mapping(source = "mobilnummern" , target = "mobilNummern")
    PersonKontakt toPersonKontakt(PersonKontaktRequestDTO personKontaktDTO);
}