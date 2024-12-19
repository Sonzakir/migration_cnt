package com.fazli.dto;


import com.fazli.Person;

import java.util.stream.Collectors;


public class PersonDTOMapper {
    private final AdresseMapper adresseMapper;
    private final PersonKontaktDTOMapper personKontaktDTOMapper;

    public PersonDTOMapper(  AdresseMapper adresseMapper, PersonKontaktDTOMapper personKontaktDTOMapper) {
        this.adresseMapper = adresseMapper;
        this.personKontaktDTOMapper = personKontaktDTOMapper;
    }


    public PersonDTO toDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getVorname() ,
                person.getNachname(),
                person.getAdresse().stream().map(adresseMapper::toDTO).collect(Collectors.toList()),
                person.getKontaktList().stream().map(personKontaktDTOMapper::toDTO).collect(Collectors.toList())
        );
    }


    public Person toPerson(PersonDTO personDTO) {
        return new Person(
                personDTO.getId(),
                personDTO.getVorname() ,
                personDTO.getNachname(),
                personDTO.getAdresse().stream().map(adresseMapper::toAdresse).collect(Collectors.toList()),
                personDTO.getKontaktList().stream().map(personKontaktDTOMapper::toPersonKontakt).collect(Collectors.toList())
        );
    }


    public Person ReqtoPerson(PersonRequestDTO personRequestDTO) {
        return new Person(
                personRequestDTO.getVorname() ,
                personRequestDTO.getNachname(),
                personRequestDTO.getAdresse().stream().map(adresseMapper::toAdresse).collect(Collectors.toList()),
                personRequestDTO.getKontaktList().stream().map(personKontaktDTOMapper::toPersonKontakt).collect(Collectors.toList())
        );
    }




}
