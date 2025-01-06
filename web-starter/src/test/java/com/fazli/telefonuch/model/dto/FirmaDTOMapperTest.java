package com.fazli.telefonuch.model.dto;


import com.fazli.Adresse;
import com.fazli.Branche;
import com.fazli.Firma;
import com.fazli.FirmaKontakt;
import com.fazli.dto.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FirmaDTOMapperTest {

    private final AdresseMapper adresseMapper = Mappers.getMapper(AdresseMapper.class);
    private final FirmaKontaktDTOMapper firmaKontaktDTOMapper = Mappers.getMapper(FirmaKontaktDTOMapper.class);
    private final FirmaDTOMapper subject = Mappers.getMapper(FirmaDTOMapper.class);





    static Stream<Arguments> boolean_Firma_FirmaDTO_Provider() {

        // BaseBuilder
        Firma.FirmaBuilder firmaBuilder = Firma.builder()
                .id(23L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(1L,"Bspstr.","3","33789","Dortmund","Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 2L,List.of("0345345333"),List.of("firma@gmail.com"),List.of("firma.com") ,  List.of("0345234445"))));


        FirmaDTO.FirmaDTOBuilder firmaDTOBuilder = FirmaDTO.builder()
                .id(23L)
                .name("Beispiel Firma")
                .adresse(List.of(new AdresseDTO(1L,"Bspstr.","3","33789","Dortmund","Nordhrein-Westfalen")))
                .branche(List.of("SOFTWARE"))
                .kontaktList(List.of(new FirmaKontaktDTO( 2L,List.of("0345345333"),List.of("firma@gmail.com"),List.of("firma.com") ,  List.of("0345234445"))));


        // Objekten

        // Firma
        Firma firma_23_Mit_Daten = firmaBuilder.build();
        Firma firma_23_Mit_NeuName = firmaBuilder.name("Rechtsanwalt").build();
        Firma firma_23_Mit_NeueBranche = firmaBuilder.branchen(List.of(Branche.ANWALT)).build();
        Firma firma_23_Ohne_Daten = firmaBuilder.
                name("")
                .adresse(List.of())
                .branchen(List.of())
                .kontaktList(List.of()).build();


        // FirmaDTO
        FirmaDTO dto_23_Mit_Daten = firmaDTOBuilder.build();
        FirmaDTO dto_23_Mit_NeueName = firmaDTOBuilder.name("Rechtsanwalt").build();
        FirmaDTO dto_23_Mit_NeueBranche = firmaDTOBuilder.branche(List.of("ANWALT")).build();
        FirmaDTO dto_23_Ohne_Daten = firmaDTOBuilder.name("")
                .adresse(List.of())
                .branche(List.of())
                .kontaktList(List.of()).build();
        FirmaDTO dto_24_Mit_Daten = firmaDTOBuilder.id(24L).build(); // Verschiedene ID

        return Stream.of(
                Arguments.of(firma_23_Mit_Daten , dto_23_Mit_Daten , true) ,
                Arguments.of(firma_23_Mit_Daten , dto_23_Mit_NeueName , false) ,
                Arguments.of(firma_23_Mit_NeuName , dto_23_Mit_NeueName , true) ,
                Arguments.of(firma_23_Mit_NeueBranche , dto_23_Mit_NeueName , false) ,
                Arguments.of(firma_23_Mit_NeueBranche , dto_23_Mit_NeueBranche , true) ,
                Arguments.of(firma_23_Ohne_Daten , dto_23_Mit_Daten , false) ,
                Arguments.of(firma_23_Ohne_Daten , dto_23_Ohne_Daten , true) ,
                Arguments.of(firma_23_Ohne_Daten , dto_24_Mit_Daten , false)
        );
    }

    // Firma -> FirmaDTO

    @ParameterizedTest
    @MethodSource("boolean_Firma_FirmaDTO_Provider")
    @DisplayName("Korrekte Mapping FirmaDTO zu Firma")
    void  test_Firma_FirmaDTO_Mapping(Firma input, FirmaDTO output, boolean shouldEqual) {

        FirmaDTO actual = subject.toDTO(input);
        assertEquals(actual.equals(output), shouldEqual);
    }

    // FirmaDTO -> Firma

    @ParameterizedTest
    @MethodSource("boolean_Firma_FirmaDTO_Provider")
    @DisplayName("Korrekte Mapping FirmaDTO zu Firma")
    void test_FirmaDTO_Firma_Mapping(Firma output , FirmaDTO input , boolean shouldEqual) {
        Firma actual = subject.toFirma(input);

        assertEquals(actual.equals(output), shouldEqual);
    }

    // FirmaRequestDTO -> Firma
    static Stream<Arguments> boolean_FirmaRequestDTO_Firma_Provider() {

        // BaseBuilder

        FirmaRequestDTO.FirmaRequestDTOBuilder requestDTOBuilder = FirmaRequestDTO.builder()
                .name("Beispiel Firma")
                .adresse(List.of(new AdresseRequestDTO("Bspstr.", "3", "33789", "Dortmund", "Nordhrein-Westfalen")))
                .branche(List.of("SOFTWARE"))
                .kontaktList(List.of(new FirmaKontaktRequestDTO(List.of("0345345333"), List.of("firma@gmail.com"), List.of("firma.com"), List.of("0345234445"))));
        Firma.FirmaBuilder firmaBuilder = Firma.builder()
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse("Bspstr.","3","33789","Dortmund","Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( List.of("0345345333"),List.of("firma@gmail.com"),List.of("firma.com") ,  List.of("0345234445"))));


        // Objekten

        // FirmaRequestDTO
        FirmaRequestDTO requestDTO_1_Mit_Daten = requestDTOBuilder.build();
        FirmaRequestDTO requestDTO_1_Mit_NeueName = requestDTOBuilder.name("Rechtsanwalt Günther Erling ")
                .build();
        FirmaRequestDTO requestDTO_1_Mit_NeueBranche = requestDTOBuilder.branche(List.of("ANWALT")).build();
        FirmaRequestDTO requestDTO_1_Ohne_Daten = requestDTOBuilder
                .name("")
                .adresse(List.of())
                .branche(List.of())
                .kontaktList(List.of())
                .build();

        //Firma
        Firma firma_1_Mit_Daten = firmaBuilder.build();
        Firma firma_1_Mit_NeueName = firmaBuilder.name("Rechtsanwalt Günther Erling ").build();
        Firma firma_1_Mit_NeueBranche = firmaBuilder.branchen(List.of(Branche.ANWALT)).build();
        Firma firma_1_Ohne_Daten = firmaBuilder.name("")
                .adresse(List.of())
                .branchen(List.of())
                .kontaktList(List.of())
                .build();
        return Stream.of(
                Arguments.of(requestDTO_1_Mit_Daten , firma_1_Mit_Daten , true),
                Arguments.of(requestDTO_1_Mit_NeueName , firma_1_Mit_Daten , false) ,
                Arguments.of(requestDTO_1_Mit_NeueName , firma_1_Mit_NeueName , true) ,
                Arguments.of(requestDTO_1_Mit_NeueBranche , firma_1_Mit_Daten , false) ,
                Arguments.of(requestDTO_1_Mit_NeueBranche , firma_1_Mit_NeueBranche , true) ,
                Arguments.of(requestDTO_1_Ohne_Daten , firma_1_Mit_Daten , false),
                Arguments.of(requestDTO_1_Ohne_Daten , firma_1_Ohne_Daten , true)
        );
    }

    @ParameterizedTest
    @MethodSource("boolean_FirmaRequestDTO_Firma_Provider")
    @DisplayName("Korrekte Mapping FirmaRequestDTO zu Firma")
    void test_FirmaDTO_Firma_Mapping(FirmaRequestDTO input ,Firma output ,  boolean shouldEqual) {
        Firma actual = subject.toFirma(input);
        assertEquals(actual.equals(output), shouldEqual);
    }












}
