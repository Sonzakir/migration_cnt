package com.fazli.telefonuch.model.dto;


import com.fazli.FirmaKontakt;
import com.fazli.dto.FirmaKontaktDTO;
import com.fazli.dto.FirmaKontaktDTOMapper;
import com.fazli.dto.FirmaKontaktRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FirmaKontaktDTOMapperTest {

    // subject

    FirmaKontaktDTOMapper subject = Mappers.getMapper(FirmaKontaktDTOMapper.class);


    //    FirmaKontakt -> FirmaKontaktDTO

    static Stream<Arguments> boolean_Kontakt_KontaktDTO_Provider(){

        //  Base Builder
        FirmaKontakt.FirmaKontaktBuilder baseKontaktBuilder = FirmaKontakt.builder()
                .id(1L)
                .festnetznummer(List.of("0345637432"))
                .email(List.of("firma@gmail.com"))
                .webseite(List.of("firmaweb.com"))
                .faxnummer(List.of("05463453443"));

        FirmaKontaktDTO.FirmaKontaktDTOBuilder baseDTOBuilder = FirmaKontaktDTO.builder()
                .id(1L)
                .festnetznummer(List.of("0345637432"))
                .email(List.of("firma@gmail.com"))
                .webseite(List.of("firmaweb.com"))
                .faxnummer(List.of("05463453443"));


        FirmaKontakt kontakt_1_Mit_Daten = baseKontaktBuilder.build();
        FirmaKontakt kontakt_1_Mit_NeueFestNO = baseKontaktBuilder.festnetznummer(List.of("02455244242")).build();

        FirmaKontakt kontakt_2_Mit_Leere_Daten = baseKontaktBuilder
                .id(2L)
                .festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .faxnummer(List.of())
                .build();

        FirmaKontaktDTO dto_1_Mit_Daten  = baseDTOBuilder.build();
        FirmaKontaktDTO dto_1_Mit_NeueFestNO = baseDTOBuilder.festnetznummer(List.of("02455244242")).build();
        FirmaKontaktDTO dto_2_Mit_Leere_Daten = baseDTOBuilder
                .id(2L)
                .festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .faxnummer(List.of())
                .build();

        FirmaKontaktDTO dto_3_Mit_Leere_Daten = baseDTOBuilder.id(3L).build();





        return Stream.of(
                Arguments.of(kontakt_1_Mit_Daten ,dto_1_Mit_Daten, true) ,
                Arguments.of(kontakt_2_Mit_Leere_Daten,dto_1_Mit_Daten,false) ,
               Arguments.of(kontakt_2_Mit_Leere_Daten,dto_2_Mit_Leere_Daten, true) ,
                Arguments.of(kontakt_2_Mit_Leere_Daten,dto_3_Mit_Leere_Daten, false) ,
               Arguments.of(kontakt_1_Mit_NeueFestNO ,dto_1_Mit_Daten, false ) ,
               Arguments.of(kontakt_1_Mit_NeueFestNO ,dto_1_Mit_NeueFestNO , true )

        );
    }



    @ParameterizedTest
    @MethodSource("boolean_Kontakt_KontaktDTO_Provider")
    @DisplayName("Korrekte Mapping FirmaKontakt zu FirmaKontaktDTO")

    void test_boolean_FirmaKontakt_Zu_FirmaKontaktDTO_Mapping( FirmaKontakt input , FirmaKontaktDTO output,
                                                              boolean shouldEqual){

        FirmaKontaktDTO actual = subject.toDTO(input);

        assertEquals(actual.equals(output), shouldEqual);


    }


    //      FirmaDTO -> Firma

    @ParameterizedTest
    @MethodSource("boolean_Kontakt_KontaktDTO_Provider")
    @DisplayName("Korrekte Mapping FirmaKontaktDTO zu FirmaKontakt")
    void test_boolean_FirmaKontaktDTO_Zu_FirmaKontakt_Mapping(FirmaKontakt output , FirmaKontaktDTO input ,
                                                              boolean shouldEqual){

        FirmaKontakt actual  = subject.toFirmaKontakt(input);

        assertEquals(actual.equals(output), shouldEqual);
    }



    //    FirmaKontaktRequestDTO -> FirmaKontakt
    static Stream<Arguments> boolean_Kontakt_KontaktRequestDTO_Provider(){

        // BaseBuilder
        FirmaKontaktRequestDTO.FirmaKontaktRequestDTOBuilder requestDTOBuilder = FirmaKontaktRequestDTO.builder()
                .festnetznummer(List.of("0353535333"))
                .email(List.of("kontaktdto@email.com"))
                .webseite(List.of("thiswebseite.com"))
                .faxnummer(List.of("03451234567"));


        FirmaKontakt.FirmaKontaktBuilder firmaKontaktBuilder = FirmaKontakt.builder()
                .festnetznummer(List.of("0353535333"))
                .email(List.of("kontaktdto@email.com"))
                .webseite(List.of("thiswebseite.com"))
                .faxnummer(List.of("03451234567"));



        // Objekten

        FirmaKontaktRequestDTO requestDTO_1_Mit_Daten = requestDTOBuilder.build();
        FirmaKontaktRequestDTO requestDTO_1_Mit_Neue_FestNO = requestDTOBuilder
                .festnetznummer(List.of("022222222")).build();
        FirmaKontaktRequestDTO requestDTO_1_Mit_Leere_Daten = requestDTOBuilder
                .festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .faxnummer(List.of())
                .build();

        FirmaKontakt kontakt_1_Mit_Daten = firmaKontaktBuilder.build();
        FirmaKontakt kontakt_1_Mit_Neue_FestNO = firmaKontaktBuilder
                .festnetznummer(List.of("022222222")).build();
        FirmaKontakt kontakt_1_Mit_Leere_Daten = firmaKontaktBuilder
                .festnetznummer(List.of())
                .email(List.of())
                .webseite(List.of())
                .faxnummer(List.of())
                .build();


        return Stream.of(
                Arguments.of(requestDTO_1_Mit_Daten , kontakt_1_Mit_Daten , true) ,
                Arguments.of(requestDTO_1_Mit_Neue_FestNO , kontakt_1_Mit_Daten , false) ,
                Arguments.of(requestDTO_1_Mit_Neue_FestNO ,kontakt_1_Mit_Neue_FestNO , true ),
                Arguments.of(requestDTO_1_Mit_Daten , kontakt_1_Mit_Leere_Daten , false) ,
                Arguments.of(requestDTO_1_Mit_Leere_Daten ,kontakt_1_Mit_Leere_Daten , true )
        );
    }

    @ParameterizedTest
    @MethodSource("boolean_Kontakt_KontaktRequestDTO_Provider")
    @DisplayName("Korrekte Mapping FirmaKontaktRequestDTO zu FirmaKontakt")
    void testPersonKontaktRequestZuPersonKontaktMapping(FirmaKontaktRequestDTO input ,
                                                        FirmaKontakt output, boolean shouldEqual ) {
        //assertEquals(expected , prufeReqZuKontaktGleicheit(firmaKontaktRequestDTO));

        var actual  = subject.toFirmaKontakt(input);

        assertEquals(actual.equals(output), shouldEqual);
    }









}

/**
 *  removed
 *      Prüfe bidirektional kontakt -> DTO -> kontakt
 *      boolean prufeBidirektional(FirmaKontakt firmaKontakt){
 *         FirmaKontaktDTO actual = subject.toDTO(firmaKontakt);
 *         FirmaKontakt bi = subject.toFirmaKontakt(actual);
 *
 *         return  actual.getFestnetznummer().equals(bi.getFestnetznummer()) &&
 *                 actual.getEmail().equals(bi.getEmail()) &&
 *                 actual.getWebseite().equals(bi.getWebseite()) &&
 *                 actual.getFaxnummer().equals(bi.getFaxnummer());
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("firmaKontaktProvider")
 *     @DisplayName("Data Integrität zwischen Mappers: kontakt->dto->kontakt")
 *     void testIntegritaetFirmaKontaktDTO(FirmaKontakt firmaKontakt , boolean expected){
 *         assertEquals(expected ,prufeBidirektional(firmaKontakt));
 *     }
 *
 *
 *     dto->kontakt
 *     static Stream<Arguments> firmaKontaktDTOProvider(){
 *         FirmaKontaktDTO kontaktDTOMitDaten = FirmaKontaktDTO.builder()
 *                 .id(1L)
 *                 .festnetznummer(List.of("0353535333"))
 *                 .email(List.of("kontaktdto@email.com"))
 *                 .webseite(List.of("thiswebseite.com"))
 *                 .faxnummer(List.of("04567453422"))
 *                 .build();
 *
 *         FirmaKontaktDTO kontaktDTOOhneDaten = FirmaKontaktDTO.builder()
 *                 .id(2L)
 *                 .festnetznummer(List.of())
 *                 .email(List.of())
 *                 .webseite(List.of())
 *                 .faxnummer(List.of())
 *                 .build();
 *         FirmaKontaktDTO kontaktDTOOhneFestNo = FirmaKontaktDTO.builder()
 *                 .id(3L)
 *                 .festnetznummer(List.of())
 *                 .email(List.of("kontaktdto@email.com"))
 *                 .webseite(List.of("thiswebseite.com"))
 *                 .faxnummer(List.of("04567453422"))
 *                 .build();
 *         FirmaKontaktDTO kontaktDTOOhneFaxNo = FirmaKontaktDTO.builder()
 *                 .id(1L)
 *                 .festnetznummer(List.of("0353535333"))
 *                 .email(List.of("kontaktdto@email.com"))
 *                 .webseite(List.of("thiswebseite.com"))
 *                 .faxnummer(List.of())
 *                 .build();
 *
 *         return Stream.of(
 *                 Arguments.of(kontaktDTOMitDaten, true) ,
 *                 Arguments.of(kontaktDTOOhneDaten , true) ,
 *                 Arguments.of(kontaktDTOOhneFestNo , true) ,
 *                 Arguments.of(kontaktDTOOhneFaxNo, true)
 *         );
 *
 *     }
 *
 *     boolean prufeDTOzuKontaktGleichheit(FirmaKontaktDTO firmaKontakt){
 *         FirmaKontakt actual = subject.toFirmaKontakt(firmaKontakt);
 *         return actual.getFestnetznummer().equals(firmaKontakt.getFestnetznummer()) &&
 *                 actual.getEmail().equals(firmaKontakt.getEmail()) &&
 *                 actual.getWebseite().equals(firmaKontakt.getWebseite()) &&
 *                 actual.getFaxnummer().equals(firmaKontakt.getFaxnummer());
 *     }
 *
 *     @ParameterizedTest
 *     @MethodSource("firmaKontaktDTOProvider")
 *     @DisplayName("Korrekte Mapping FirmaKontaktDTO zu FirmaKontakt")
 *     void testDTOzuKontkaktMapping(FirmaKontaktDTO firmaKontaktDTO , boolean expected){
 *         assertEquals(expected , prufeDTOzuKontaktGleichheit(firmaKontaktDTO));
 *     }
 *
 *     boolean prufeDTOBidirektional(FirmaKontaktDTO firmaKontaktDTO){
 *         FirmaKontakt actual = subject.toFirmaKontakt(firmaKontaktDTO);
 *         FirmaKontaktDTO bi = subject.toDTO(actual);
 *
 *         return actual.getFestnetznummer().equals(bi.getFestnetznummer()) &&
 *                 actual.getEmail().equals(bi.getEmail()) &&
 *                 actual.getWebseite().equals(bi.getWebseite()) &&
 *                 actual.getFaxnummer().equals(bi.getFaxnummer());
 *     }
 *     @ParameterizedTest
 *     @MethodSource("firmaKontaktDTOProvider")
 *     @DisplayName("Data Integrität zwischen Mappers: dto->kontakt->dto")
 *     void testIntegritaetDTOMapper(FirmaKontaktDTO firmaKontaktDTO ,  boolean expected){
 *         assertEquals(expected , prufeDTOBidirektional(firmaKontaktDTO));
 *     }
 *
 *
 *      static Stream<Arguments> firmaKontaktRequestProvider(){
 *         FirmaKontaktRequestDTO kontaktRequestDTOMitDaten = FirmaKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of("0353535333"))
 *                 .email(List.of("kontaktdto@email.com"))
 *                 .webseite(List.of("thiswebseite.com"))
 *                 .faxnummer(List.of("03451234567"))
 *                 .build();
 *         FirmaKontaktRequestDTO kontaktRequestDTOOhneDaten = FirmaKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of())
 *                 .webseite(List.of())
 *                 .faxnummer(List.of())
 *                 .build();
 *         FirmaKontaktRequestDTO kontaktRequestDTOhneFestNo = FirmaKontaktRequestDTO.builder()
 *                 .festnetznummer(List.of())
 *                 .email(List.of("kontaktdto@email.com"))
 *                 .webseite(List.of("thiswebseite.com"))
 *                 .faxnummer(List.of("03451234567"))
 *                 .build();
 *         return Stream.of(
 *                 Arguments.of(kontaktRequestDTOMitDaten ,  true) ,
 *                 Arguments.of(kontaktRequestDTOOhneDaten , true) ,
 *                 Arguments.of(kontaktRequestDTOhneFestNo , true)
 *         );
 *     }
 *
 *     boolean prufeReqZuKontaktGleicheit(FirmaKontaktRequestDTO firmaKontaktRequestDTO) {
 *
 *         FirmaKontakt actual = subject .toFirmaKontakt(firmaKontaktRequestDTO);
 *
 *         return actual.getFestnetzNummer().equals(firmaKontaktRequestDTO.getFestnetznummer()) &&
 *                 actual.getEmail().equals(firmaKontaktRequestDTO.getEmail()) &&
 *                 actual.getWebseite().equals(firmaKontaktRequestDTO.getWebseite()) &&
 *                 actual.getFaxnummer().equals(firmaKontaktRequestDTO.getFaxnummer());
 *
 *     }
 */