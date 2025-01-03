package com.fazli.telefonuch.model.dto;


import com.fazli.Adresse;
import com.fazli.Person;
import com.fazli.PersonKontakt;
import com.fazli.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOMapperTest {

    private final AdresseMapper adresseMapper = Mappers.getMapper(AdresseMapper.class); // Get the MapStruct implementation
    PersonKontaktDTOMapper personKontaktDTOMapper = new PersonKontaktDTOMapper();

    // subject
    PersonDTOMapper subject = new PersonDTOMapper(adresseMapper, personKontaktDTOMapper);

    static Stream<Arguments> boolean_Person_PersonDTO_Provider(){

        // BaseBuilder
        Person.PersonBuilder personBuilder = Person.builder()
                .id(1L)
                .vorname("Max")
                .nachname("Müller")
                .adresse(List.of(new Adresse(1L,"Bspstr.","22","45342","Bochum","Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(2L, List.of("01737373773"),List.of("maxmüller@gmail.com")
                        ,List.of("max.blogspot.com"),List.of("017727272"))));

        PersonDTO.PersonDTOBuilder dtoBuilder = PersonDTO.builder()
                .id(1L)
                .vorname("Max")
                .nachname("Müller")
                .adresse(List.of(new AdresseDTO(1L,"Bspstr.","22","45342","Bochum","Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontaktDTO(2L, List.of("01737373773"),List.of("maxmüller@gmail.com")
                        ,List.of("max.blogspot.com"),List.of("017727272"))));

        // Instanzen

        // Person
        Person person_1_Mit_Daten = personBuilder.build();
        Person person_1_Mit_Neuer_Name = personBuilder.vorname("Maximillian").build();
        Person person_1_Mit_Neuer_Nachname = personBuilder.nachname("Wittek").build();
        Person person_1_Mit_Neue_ID = personBuilder.id(200L).build();
        Person person_1_Mit_Leere_Daten  = personBuilder
                .vorname("")
                .nachname("")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();
        // PersonDTO
        PersonDTO dto_1_Mit_Daten = dtoBuilder.build();
        PersonDTO dto_1_Mit_Neuer_Name = dtoBuilder.vorname("Maximillian").build();
        PersonDTO dto_1_Mit_Neuer_Nachname = dtoBuilder.nachname("Wittek").build();
        PersonDTO dto_1_Mit_Neue_ID = dtoBuilder.id(200L).build();
        PersonDTO dto_1_Mit_Leere_Daten = dtoBuilder
                .vorname("")
                .nachname("")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();

        return Stream.of(
                Arguments.of(person_1_Mit_Daten ,  dto_1_Mit_Daten ,true ) ,
                Arguments.of(person_1_Mit_Neuer_Name, dto_1_Mit_Daten , false),
                Arguments.of(person_1_Mit_Neuer_Name , dto_1_Mit_Neuer_Name , true),
                Arguments.of(person_1_Mit_Neuer_Nachname, dto_1_Mit_Neuer_Name ,false ),
                Arguments.of(person_1_Mit_Neuer_Nachname , dto_1_Mit_Neuer_Nachname , true) ,
                Arguments.of(person_1_Mit_Neue_ID , dto_1_Mit_Neue_ID, true),
                Arguments.of(person_1_Mit_Neue_ID , dto_1_Mit_Neuer_Nachname , false ),
                Arguments.of(person_1_Mit_Leere_Daten , dto_1_Mit_Daten ,false) ,
                Arguments.of(person_1_Mit_Leere_Daten , dto_1_Mit_Leere_Daten , true)
        );

    }

    // Person -> PersonDTO
    @ParameterizedTest
    @MethodSource("boolean_Person_PersonDTO_Provider")
    @DisplayName("Korrekte Mapping von Person zu PersonDTO")
    void test_Person_PersonDTO_Mapping(Person input , PersonDTO output ,
                                       boolean shouldEqual){
        var actual = subject.toDTO(input);
        assertEquals(actual.equals(output), shouldEqual);
    }

    // PersonDTO -> Person
    @ParameterizedTest
    @MethodSource("boolean_Person_PersonDTO_Provider")
    @DisplayName("Korrekte Mapping von PersonDTO zu Person")
    void test_PersonDTO_Person_Mapping(Person output, PersonDTO input ,
                                       boolean shouldEqual){
        var actual = subject.toPerson(input);
        assertEquals(actual.equals(output), shouldEqual);
    }



    //  PersonRequestDTO -> Person

    static Stream<Arguments> boolean_PersonReqguestDTO_Person_Provider(){

        // BaseBuilder
        PersonRequestDTO.PersonRequestDTOBuilder requestDTOBuilder = PersonRequestDTO.builder()
                .vorname("Max")
                .nachname("Müller")
                .adresse(List.of(new AdresseRequestDTO("Bspstr.","22","45342","Bochum","Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontaktRequestDTO( List.of("01737373773"),List.of("maxmüller@gmail.com")
                        ,List.of("max.blogspot.com"),List.of("017727272"))));

        Person.PersonBuilder personBuilder = Person.builder()
                .vorname("Max")
                .nachname("Müller")
                .adresse(List.of(new Adresse("Bspstr.","22","45342","Bochum","Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt( List.of("01737373773"),List.of("maxmüller@gmail.com")
                        ,List.of("max.blogspot.com"),List.of("017727272"))));

        // Instanzen

        // PersonRequestDTO
        PersonRequestDTO dto_1_Mit_Daten = requestDTOBuilder.build();
        PersonRequestDTO dto_1_Mit_Neuer_Name = requestDTOBuilder.vorname("Maximillian").build();
        PersonRequestDTO dto_1_Mit_Neuer_Nachname = requestDTOBuilder.nachname("Wittek").build();
        PersonRequestDTO dto_1_Mit_Leere_Daten = requestDTOBuilder
                .vorname("")
                .nachname("")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();

        // Person
        Person person_1_Mit_Daten = personBuilder.build();
        Person person_1_Mit_Neuer_Name = personBuilder.vorname("Maximillian").build();
        Person person_1_Mit_Neuer_Nachname = personBuilder.nachname("Wittek").build();
        Person person_1_Mit_Leere_Daten = personBuilder
                .vorname("")
                .nachname("")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();


        return Stream.of(
                Arguments.of(dto_1_Mit_Daten , person_1_Mit_Daten ,  true ) ,
                Arguments.of( dto_1_Mit_Daten ,person_1_Mit_Neuer_Name, false),
                Arguments.of(dto_1_Mit_Neuer_Name ,person_1_Mit_Neuer_Name ,  true),
                Arguments.of(dto_1_Mit_Neuer_Name , person_1_Mit_Neuer_Nachname, false ),
                Arguments.of( dto_1_Mit_Neuer_Nachname ,person_1_Mit_Neuer_Nachname , true) ,
                Arguments.of(dto_1_Mit_Leere_Daten, person_1_Mit_Neuer_Nachname ,false) ,
                Arguments.of(dto_1_Mit_Leere_Daten , person_1_Mit_Leere_Daten , true)
        );
    }

    // PersonRequestDTO -> Person
    @ParameterizedTest
    @MethodSource("boolean_PersonReqguestDTO_Person_Provider")
    @DisplayName("Korrekte Mapping von PersonRequestDTO zu Person")
    void test_Person_PersonDTO_Mapping(PersonRequestDTO input , Person output ,
                                       boolean shouldEqual){
        var actual = subject.ReqtoPerson(input);
        assertEquals(actual.equals(output), shouldEqual);
    }



}