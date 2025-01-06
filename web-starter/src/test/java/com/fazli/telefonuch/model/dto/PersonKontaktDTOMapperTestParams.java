package com.fazli.telefonuch.model.dto;


import com.fazli.PersonKontakt;
import com.fazli.dto.PersonKontaktDTO;
import com.fazli.dto.PersonKontaktDTOMapper;
import com.fazli.dto.PersonKontaktRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class PersonKontaktDTOMapperTestParams {

    // subject
    PersonKontaktDTOMapper testSubject = Mappers.getMapper(PersonKontaktDTOMapper.class);

    static Stream<Arguments> boolean_PersonKontakt_PersonKontaktDTO_Provider() {
        //Basebuilder
        PersonKontakt.PersonKontaktBuilder personKontaktBuilder = PersonKontakt.builder()
                .id(1L)
                .festnetznummer(List.of("01626622"))
                .email(List.of("email@gmail.com"))
                .webseite(List.of("webseite.com"))
                .mobilNummern(List.of("0338383833"));

        PersonKontaktDTO.PersonKontaktDTOBuilder personKontaktDTOBuilder = PersonKontaktDTO.builder()
                .id(1L)
                .festnetznummer(List.of("01626622"))
                .email(List.of("email@gmail.com"))
                .webseite(List.of("webseite.com"))
                .mobilnummern(List.of("0338383833"));


        // Objekten

        // PersonKontakt
        PersonKontakt kontakt_1_Mit_Daten = personKontaktBuilder.build();
        PersonKontakt kontakt_1_Mit_NeueFestNo = personKontaktBuilder.
                festnetznummer(List.of("02346545343")).build();
        PersonKontakt kontakt_1_Mit_NeuID = personKontaktBuilder.id(2L).build();
        PersonKontakt kontakt_2_Mit_NeueWebseite_Email = personKontaktBuilder
                .email(List.of("neuemail@gmail.com"))
                .webseite(List.of("neuewebseite.com")).build();
        PersonKontakt kontakt_2_Mit_Leere_Daten = personKontaktBuilder
                .festnetznummer(List.of())
                .email(List.of()).webseite(List.of()).mobilNummern(List.of())
                .build();

        //PersonKontaktDTO
        PersonKontaktDTO dto_1_Mit_Daten = personKontaktDTOBuilder.build();
        PersonKontaktDTO dto_1_Mit_NeueFestNo = personKontaktDTOBuilder
                .festnetznummer(List.of("02346545343")).build();
        PersonKontaktDTO dto_1_Mit_NeuID = personKontaktDTOBuilder.id(2L).build();
        PersonKontaktDTO dto_2_Mit_NeueWebseite_Email = personKontaktDTOBuilder.
                email(List.of("neuemail@gmail.com"))
                .webseite(List.of("neuewebseite.com")).build();
        PersonKontaktDTO dto_2_Mit_Leere_Daten = personKontaktDTOBuilder.
                festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .mobilnummern(List.of())
                .build();

        return Stream.of(
                Arguments.of(kontakt_1_Mit_Daten , dto_1_Mit_Daten , true) ,
                Arguments.of(kontakt_1_Mit_Daten , dto_1_Mit_NeueFestNo , false) ,
                Arguments.of(kontakt_1_Mit_NeueFestNo , dto_1_Mit_NeueFestNo , true) ,
                Arguments.of(kontakt_1_Mit_NeuID , dto_1_Mit_NeueFestNo , false) ,
                Arguments.of(kontakt_1_Mit_NeuID ,dto_1_Mit_NeuID , true) ,
                Arguments.of(kontakt_2_Mit_NeueWebseite_Email , dto_1_Mit_NeuID , false) ,
                Arguments.of(kontakt_2_Mit_NeueWebseite_Email , dto_2_Mit_NeueWebseite_Email ,true),
                Arguments.of(kontakt_2_Mit_Leere_Daten , dto_2_Mit_NeueWebseite_Email , false) ,
                Arguments.of(kontakt_2_Mit_Leere_Daten , dto_2_Mit_Leere_Daten  , true)
        );
    }

    // PersonKontakt -> PersonKontaktDTO

    @ParameterizedTest
    @MethodSource("boolean_PersonKontakt_PersonKontaktDTO_Provider")
    @DisplayName("Korrekte Mapping PersonKontakt zu PersonKontaktDTO")
    void test_PersonKontakt_PersonKontaktDTO_Mapping(PersonKontakt input , PersonKontaktDTO output ,
                                                     boolean shouldEqual){
        PersonKontaktDTO actual = testSubject.toDTO(input);
        assertEquals(actual.equals(output) , shouldEqual);
    }

    // PersonKontaktDTO -> PersonKontakt
    @ParameterizedTest
    @MethodSource("boolean_PersonKontakt_PersonKontaktDTO_Provider")
    @DisplayName("Korrekte Mapping PersonKontaktDTO zu PersonKontakt")
    void  test_PersonKontaktDTO_PersonKontakt_Mapping(PersonKontakt output,  PersonKontaktDTO input,
                                                      boolean shouldEqual){
        PersonKontakt actual = testSubject.toPersonKontakt(input);
        System.out.println(actual);
        System.out.println(output);
        assertEquals(actual.equals(output) , shouldEqual);
    }


    static Stream<Arguments> boolean_PersonKontakt_PersonKontaktRequestDTO_Provider() {
        //Basebuilder
        PersonKontakt.PersonKontaktBuilder personKontaktBuilder = PersonKontakt.builder()
                .festnetznummer(List.of("01626622"))
                .email(List.of("email@gmail.com"))
                .webseite(List.of("webseite.com"))
                .mobilNummern(List.of("0338383833"));

        PersonKontaktRequestDTO.PersonKontaktRequestDTOBuilder requestDTOBuilder = PersonKontaktRequestDTO.builder()
                .festnetznummer(List.of("01626622"))
                .email(List.of("email@gmail.com"))
                .webseite(List.of("webseite.com"))
                .mobilnummern(List.of("0338383833"));


        // Objekten

        // PersonKontakt
        PersonKontakt kontakt_1_Mit_Daten = personKontaktBuilder.build();
        PersonKontakt kontakt_1_Mit_NeueFestNo = personKontaktBuilder.
                festnetznummer(List.of("02346545343")).build();
        PersonKontakt kontakt_2_Mit_NeueWebseite_Email = personKontaktBuilder
                .email(List.of("neuemail@gmail.com"))
                .webseite(List.of("neuewebseite.com")).build();
        PersonKontakt kontakt_2_Mit_Leere_Daten = personKontaktBuilder
                .festnetznummer(List.of())
                .email(List.of()).webseite(List.of()).mobilNummern(List.of())
                .build();

        //PersonKontaktDTO
        PersonKontaktRequestDTO dto_1_Mit_Daten = requestDTOBuilder.build();
        PersonKontaktRequestDTO dto_1_Mit_NeueFestNo = requestDTOBuilder
                .festnetznummer(List.of("02346545343")).build();
        PersonKontaktRequestDTO dto_2_Mit_NeueWebseite_Email = requestDTOBuilder.
                email(List.of("neuemail@gmail.com"))
                .webseite(List.of("neuewebseite.com")).build();
        PersonKontaktRequestDTO dto_2_Mit_Leere_Daten = requestDTOBuilder.
                festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .mobilnummern(List.of())
                .build();

        return Stream.of(
                Arguments.of(dto_1_Mit_Daten,kontakt_1_Mit_Daten  , true) ,
                Arguments.of( dto_1_Mit_NeueFestNo ,kontakt_1_Mit_Daten , false) ,
                Arguments.of( dto_1_Mit_NeueFestNo ,kontakt_1_Mit_NeueFestNo , true) ,
                Arguments.of( dto_1_Mit_NeueFestNo ,kontakt_1_Mit_Daten , false) ,
                Arguments.of( dto_1_Mit_NeueFestNo , kontakt_2_Mit_NeueWebseite_Email , false) ,
                Arguments.of(dto_2_Mit_NeueWebseite_Email ,kontakt_2_Mit_NeueWebseite_Email , true),
                Arguments.of( dto_2_Mit_NeueWebseite_Email ,kontakt_2_Mit_Leere_Daten , false) ,
                Arguments.of( dto_2_Mit_Leere_Daten  ,kontakt_2_Mit_Leere_Daten , true)
        );
    }

    @ParameterizedTest
    @MethodSource("boolean_PersonKontakt_PersonKontaktRequestDTO_Provider")
    @DisplayName("Korrekte Mapping PersonKontaktRequestDTO zu PersonKontaktDTO")
    void  test_PersonKontaktRequestDTO_PersonKontakt_Mapping(PersonKontaktRequestDTO input , PersonKontakt output,
                                                             boolean shouldEqual){
        var actual = testSubject.toPersonKontakt(input);
        assertEquals(actual.equals(output), shouldEqual);
    }












}
/**
 * static Stream<Arguments> personKontaktProvider() {
 *         // PersonKontakt Instanzen
 *         PersonKontakt personKontaktMitDaten = PersonKontakt.builder()
 *                 .festnetznummer(List.of("01626622"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilNummern(List.of("0338383833")).build();
 *         PersonKontakt personKontaktLeer = PersonKontakt.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of())
 *                 .webseite(List.of())
 *                 .mobilNummern(List.of())
 *                 .build();
 *         PersonKontakt personKontaktOhneFestNo = PersonKontakt.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilNummern(List.of("0338383833")).build();
 *         PersonKontakt personKontaktOhneEmail = PersonKontakt.builder()
 *                 .festnetznummer(List.of("01626622"))
 *                 .email(List.of())
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilNummern(List.of("0338383833")).build();
 *         PersonKontakt personKontaktOhneWebseiteUndMobilnummer = PersonKontakt.builder()
 *                 .festnetznummer(List.of("01626622"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of())
 *                 .mobilNummern(List.of()).build();
 *         PersonKontakt personKontaktMitGeanderteDaten = PersonKontakt.builder()
 *                 .festnetznummer(List.of("01626622"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilNummern(List.of("0338383833")).build();
 *         personKontaktMitGeanderteDaten.setFestnetzNummer(List.of("0333333"));
 *
 *
 *
 *
 *
 *
 *         return Stream.of(
 *                 Arguments.of(personKontaktMitDaten,true) ,
 *                 Arguments.of(personKontaktLeer , true),
 *                 Arguments.of(personKontaktOhneFestNo , true) ,
 *                 Arguments.of(personKontaktOhneEmail , true) ,
 *                 Arguments.of(personKontaktOhneWebseiteUndMobilnummer , true) ,
 *                 Arguments.of(personKontaktMitGeanderteDaten , true)
 *         );
 *     }
 *
 *     public boolean prufeDTOGleicheit(PersonKontakt personKontakt) {
 *         PersonKontaktDTO  actual = testSubject.toDTO(personKontakt);
 *         return actual.getFestnetznummer().equals(personKontakt.getFestnetzNummer()) &&
 *                 actual.getEmail().equals(personKontakt.getEmail()) &&
 *                 actual.getWebseite().equals(personKontakt.getWebseite()) &&
 *                 actual.getMobilnummern().equals(personKontakt.getMobilNummern());
 *     }
 *
 *         @ParameterizedTest
 *     @MethodSource("personKontaktProvider")
 *     @DisplayName("Korrekte Mapping PersonKontakt zu PersonKontaktDTO")
 *     void testPersonKontaktZuDTOMapping (PersonKontakt personKontakt, boolean expected) {
 *         assertEquals(expected , prufeDTOGleicheit(personKontakt));
 *     }
 *
 *
 *
 *     boolean prufeBidirektional(PersonKontakt personKontakt) {
 *         PersonKontaktDTO  actual = testSubject.toDTO(personKontakt);
 *         PersonKontakt bi = testSubject.toPersonKontakt(actual);
 *         return actual.getFestnetznummer().equals(bi.getFestnetzNummer()) &&
 *                 actual.getEmail().equals(bi.getEmail()) &&
 *                 actual.getWebseite().equals(bi.getWebseite()) &&
 *                 actual.getMobilnummern().equals(bi.getMobilNummern());
 *
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("personKontaktProvider")
 *     @DisplayName("Data Integrität zwischen Mappers: kontakt->dto->kontakt")
 *     void testIntegriätPersonKontaktDTOMapper(PersonKontakt personKontakt, boolean expected) {
 *         assertEquals(expected, prufeBidirektional(personKontakt));
 *     }
 *
 *
 *
 *     // PersonKontaktDTO
 *     static Stream<Arguments> personKontaktDTOProvider() {
 *         PersonKontaktDTO  dtoMitDaten = PersonKontaktDTO.builder()
 *                 .id(3L)
 *                 .festnetznummer(List.of("0383838484"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilnummern(List.of("0373737373"))
 *                 .build();
 *         PersonKontaktDTO  leereDTO = PersonKontaktDTO.builder()
 *                 .id(5L)
 *                 .festnetznummer(List.of())
 *                 .email(List.of())
 *                 .webseite(List.of())
 *                 .mobilnummern(List.of())
 *                 .build();
 *         PersonKontaktDTO personKontaktDTOOhneFestNo = PersonKontaktDTO.builder()
 *                 .id(6L)
 *                 .festnetznummer(List.of())
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilnummern(List.of("0373737373"))
 *                 .build();
 *         PersonKontaktDTO personKontaktDTOOhneEmail =PersonKontaktDTO.builder()
 *                 .id(7L)
 *                 .festnetznummer(List.of("0383838484"))
 *                 .email(List.of())
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilnummern(List.of("0373737373"))
 *                 .build();
 *         PersonKontaktDTO personKontaktDTOOhneWebseiteUndMobilnummer = PersonKontaktDTO.builder()
 *                 .id(8L)
 *                 .festnetznummer(List.of("0383838484"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of())
 *                 .mobilnummern(List.of())
 *                 .build();
 *         PersonKontaktDTO personKontaktDTOMitGeanderteDaten = PersonKontaktDTO.builder()
 *                 .id(3L)
 *                 .festnetznummer(List.of("0383838484"))
 *                 .email(List.of("email@gmail.com"))
 *                 .webseite(List.of("webseite.com"))
 *                 .mobilnummern(List.of("0373737373"))
 *                 .build();
 *         personKontaktDTOMitGeanderteDaten.setFestnetznummer(List.of("0333333"));
 *
 *
 *         return Stream.of(
 *                 Arguments.of(dtoMitDaten , true) ,
 *                 Arguments.of(leereDTO , true)  ,
 *                 Arguments.of(personKontaktDTOOhneFestNo , true) ,
 *                 Arguments.of(personKontaktDTOOhneEmail ,true) ,
 *                 Arguments.of(personKontaktDTOOhneWebseiteUndMobilnummer ,true) ,
 *                 Arguments.of(personKontaktDTOMitGeanderteDaten ,true)
 *         );
 *     }
 *
 *     boolean prufeDTOGleicheit(PersonKontaktDTO personKontaktDTO) {
 *         PersonKontakt actual = testSubject.toPersonKontakt(personKontaktDTO);
 *         return actual.getFestnetzNummer().equals(personKontaktDTO.getFestnetznummer()) &&
 *                 actual.getEmail().equals(personKontaktDTO.getEmail()) &&
 *                 actual.getWebseite().equals(personKontaktDTO.getWebseite()) &&
 *                 actual.getMobilNummern().equals(personKontaktDTO.getMobilnummern());
 *
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("personKontaktDTOProvider")
 *     @DisplayName("Korrekte Mapping PersonKontaktDTO zu PersonKontakt")
 *     void testPersonKontaktMapping(PersonKontaktDTO personKontaktDTO, boolean expected) {
 *         assertEquals(expected , prufeDTOGleicheit(personKontaktDTO));
 *     }
 *
 *
 *     boolean prufeDTOBidirektional(PersonKontaktDTO personKontaktDTO) {
 *
 *         PersonKontakt actual = testSubject.toPersonKontakt(personKontaktDTO);
 *         PersonKontaktDTO bi =  testSubject.toDTO(actual);
 *         return actual.getFestnetzNummer().equals(bi.getFestnetznummer()) &&
 *                 actual.getEmail().equals(bi.getEmail()) &&
 *                 actual.getWebseite().equals(bi.getWebseite()) &&
 *                 actual.getMobilNummern().equals(bi.getMobilnummern());
 *
 *
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("personKontaktDTOProvider")
 *     @DisplayName("Data Integrität zwischen Mappers: dto->kontakt->dto")
 *     void testIntegritaetPersonKontaktMapper(PersonKontaktDTO personKontaktDTO, boolean expected) {
 *         assertEquals(expected , prufeDTOBidirektional(personKontaktDTO));
 *     }
 *
 *     // PersonKontaktRequestDTO
 *     static Stream<Arguments> PersonKontaktRequestDTOProvider() {
 *
 *         PersonKontaktRequestDTO requestDTOMitDaten = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of("03456288122"))
 *                 .email(List.of("person@gmail.com"))
 *                 .webseite(List.of("webseite.de"))
 *                 .mobilnummern(List.of("05463452334"))
 *                 .build();
 *         PersonKontaktRequestDTO requestDTOOhneDaten = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of())
 *                 .webseite(List.of())
 *                 .mobilnummern(List.of())
 *                 .build();
 *         PersonKontaktRequestDTO requestDTOOhneFestNO = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of("person@gmail.com"))
 *                 .webseite(List.of("webseite.de"))
 *                 .mobilnummern(List.of("05463452334"))
 *                 .build();
 *         PersonKontaktRequestDTO requestDTOMitMehrereFestNO = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of("03456288122" , "02346756542"))
 *                 .email(List.of("person@gmail.com"))
 *                 .webseite(List.of("webseite.de"))
 *                 .mobilnummern(List.of("05463452334"))
 *                 .build();
 *         PersonKontaktRequestDTO requestDTOOhneEmail = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of("03456288122"))
 *                 .email(List.of())
 *                 .webseite(List.of("webseite.de"))
 *                 .mobilnummern(List.of("05463452334"))
 *                 .build();
 *         PersonKontaktRequestDTO requestDTOMitGeandertenDaten = PersonKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of("03456288122"))
 *                 .email(List.of("person@gmail.com"))
 *                 .webseite(List.of("webseite.de"))
 *                 .mobilnummern(List.of("05463452334"))
 *                 .build();
 *         requestDTOMitGeandertenDaten.setWebseite(List.of("neuewebseite.com"));
 *
 *         return Stream.of(
 *                 Arguments.of( requestDTOMitDaten , true),
 *                 Arguments.of( requestDTOOhneDaten , true),
 *                 Arguments.of( requestDTOOhneFestNO , true),
 *                 Arguments.of( requestDTOMitMehrereFestNO , true),
 *                 Arguments.of( requestDTOOhneEmail , true),
 *                 Arguments.of( requestDTOMitGeandertenDaten , true)
 *
 *
 *
 *                 );
 *
 *     }
 *
 *     boolean prufeReqZuKontaktGleicheit(PersonKontaktRequestDTO personKontaktRequestDTO) {
 *
 *         PersonKontakt  actual = testSubject .toPersonKontakt(personKontaktRequestDTO);
 *
 *         return actual.getFestnetzNummer().equals(personKontaktRequestDTO.getFestnetznummer()) &&
 *                 actual.getEmail().equals(personKontaktRequestDTO.getEmail()) &&
 *                 actual.getWebseite().equals(personKontaktRequestDTO.getWebseite()) &&
 *                 actual.getMobilNummern().equals(personKontaktRequestDTO.getMobilnummern());
 *
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("PersonKontaktRequestDTOProvider")
 *     @DisplayName("Korrekte Mapping PersonKontaktRequestDTO zu PersonKontaktDTO")
 *     void testPersonKontaktRequestZuPersonKontaktMapping(PersonKontaktRequestDTO personKontaktRequestDTO, boolean expected) {
 *         assertEquals(expected , prufeReqZuKontaktGleicheit(personKontaktRequestDTO));
 *     }
 */