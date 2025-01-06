package com.fazli.dto;

import com.fazli.Person;
import org.mapstruct.Mapper;

@Mapper(uses = {AdresseMapper.class , PersonKontaktDTOMapper.class})
public interface PersonDTOMapper {
    PersonDTO toDTO(Person person);
    Person toPerson(PersonDTO personDTO);
    Person ReqtoPerson(PersonRequestDTO personRequestDTO);
}