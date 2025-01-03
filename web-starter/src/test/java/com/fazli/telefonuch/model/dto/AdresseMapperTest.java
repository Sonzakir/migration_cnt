package com.fazli.telefonuch.model.dto;


import com.fazli.Adresse;
import com.fazli.dto.AdresseDTO;
import com.fazli.dto.AdresseMapper;
import com.fazli.dto.AdresseRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class AdresseMapperTest {

    // subject
    private final AdresseMapper subject = Mappers.getMapper(AdresseMapper.class); // Get the MapStruct implementation



    //                                  Adresse -> AdresseDTO

    static Stream<Arguments> adresse_AdresseDTO_provider(){


        Adresse.AdresseBuilder adresseBaseBuilder = Adresse.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("56749")
                .stadt("Dortmund")
                .bundesland("Nordrhein-Westfalen");

        AdresseDTO.AdresseDTOBuilder adresseDTOBaseBuilder= AdresseDTO.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("56749")
                .stadt("Dortmund")
                .bundesland("Nordrhein-Westfalen");
        // Objekten
        Adresse adresse_1_MitDaten = adresseBaseBuilder.build();

        adresseBaseBuilder.strasse("Neuestr.");
        adresseBaseBuilder.hausNo("55");
        Adresse adresse_2_MitDaten = adresseBaseBuilder.build();

        AdresseDTO adresseDTO_1_MitDaten = adresseDTOBaseBuilder.build();

        adresseDTOBaseBuilder.strasse("Neuestr.");
        adresseDTOBaseBuilder.hausNo("55");
        AdresseDTO adresseDTO_2_MitDaten = adresseDTOBaseBuilder.build();


        return Stream.of(
                Arguments.of(adresse_1_MitDaten , adresseDTO_1_MitDaten) ,
                Arguments.of(adresse_2_MitDaten , adresseDTO_2_MitDaten)
        );
    }

    /**
     * Geg. Adresse und AdresseDTO input -> prüft ob die Adresse identisch zu AdresseDTO ist
     * @param input
     * @param expected
     */

    @ParameterizedTest
    @MethodSource("adresse_AdresseDTO_provider")
    void testAdresseZuAdresseDTOMapping(Adresse input ,
                                        AdresseDTO expected) {
        AdresseDTO actual  = subject.toDTO(input);

        assertEquals(actual,expected);
    }




    static Stream<Arguments> test_boolean_Adresse_AdresseDTO_Provider(){

        // BaseBuilder
        Adresse.AdresseBuilder adresseBaseBuilder = Adresse.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("56749")
                .stadt("Dortmund")
                .bundesland("Nordrhein-Westfalen");

        AdresseDTO.AdresseDTOBuilder adresseDTOBaseBuilder= AdresseDTO.builder().
                id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("56749")
                .stadt("Dortmund")
                .bundesland("Nordrhein-Westfalen");


        // Adresse
        Adresse adresse_1_MitDaten = adresseBaseBuilder.build();

        adresseBaseBuilder.strasse("Neuestr.");
        Adresse adresse_2_MitDaten = adresseBaseBuilder.build();


        //AdresseDTO
        AdresseDTO adresseDTO_1_MitDaten = adresseDTOBaseBuilder.build();

        adresseDTOBaseBuilder.strasse("Neuestr.");
        AdresseDTO adresseDTO_2_MitDaten = adresseDTOBaseBuilder.build();

        adresseDTOBaseBuilder.id(300L);
        AdresseDTO adresseDTO_3_MitNeuID = adresseDTOBaseBuilder.build();



        return Stream.of(
                Arguments.of(adresse_1_MitDaten , adresseDTO_1_MitDaten,true) ,
                Arguments.of(adresse_1_MitDaten , adresseDTO_2_MitDaten , false) ,
                Arguments.of(adresse_2_MitDaten ,  adresseDTO_2_MitDaten , true) ,
                Arguments.of(adresse_2_MitDaten , adresseDTO_3_MitNeuID , false)
        );
    }


    /**
     * Überprüft die toDTO() Methode, die Adresse in AdresseDTO umwandelt.
     * Geg: Adresse und AdressoDTO -> prüft ob die Gleichheit 'shouldEqual' entspricht
     * @param input
     * @param output
     * @param shouldEqual true-> AdresseDTO-Adresse -> identisch , false-> nicht identisch
     */
    @ParameterizedTest
    @MethodSource("test_boolean_Adresse_AdresseDTO_Provider")
    @DisplayName("Gegeben AdresseDTO und AdresseDTO, überprüft ob die Mapping Adresse->AdresseDTO gleich sein soll ")
    void test_boolean_Adresse_Zu_AdresseDTO_Mapping(Adresse input , AdresseDTO output , boolean shouldEqual){

        var actual = subject.toDTO(input);
        // input =? output
        assertEquals( actual.equals( output ) , shouldEqual );
    }



    //                           AdresseDTO -> Adresse

    /**
     * Überprüft die toAdresse() Methode, die AdresseDTO in Adresse umwandelt.
     *      * Geg: Adresse und AdressoDTO -> prüft ob die Gleichheit 'shouldEqual' entspricht
     * @param output
     * @param input
     * @param shouldEqual
     */
    @ParameterizedTest
    @MethodSource("test_boolean_Adresse_AdresseDTO_Provider")
    @DisplayName("Gegeben AdresseDTO und AdresseDTO, überprüft ob die Mapping AdresseDTO->Adresse gleich sein soll ")
    void test_boolean_AdresseDTO_Zu_Adresse_Mapping(Adresse output , AdresseDTO input , boolean shouldEqual){

        Adresse actual = subject.toAdresse(input);

        assertEquals( actual.equals( output ) , shouldEqual );

    }


    //                       AdresseRequestDTO -> Adresse

    static Stream<Arguments> test_boolean_AdresseRequestDTO_Adresse_Provider(){
        // Base AdressRequestDTO builder
        AdresseRequestDTO.AdresseRequestDTOBuilder requestDTOBaseBuilder = AdresseRequestDTO.builder()
                .strasse("Beispielstr.")
                .hausNo("55")
                .stadt("Duisburg")
                .plz("77777")
                .bundesland("Nordrhein-Westfalen");

        // Base Adresse builder
        Adresse.AdresseBuilder adresseBaseBuilder = Adresse.builder()
                .strasse("Beispielstr.")
                .hausNo("55")
                .stadt("Duisburg")
                .plz("77777")
                .bundesland("Nordrhein-Westfalen");

        //Objekten
        AdresseRequestDTO requestDTO_1_Mit_Daten = requestDTOBaseBuilder.build();
        AdresseRequestDTO requestDTO_1_Ohne_Daten = requestDTOBaseBuilder.strasse(".")
                .hausNo("")
                .stadt("")
                .plz("")
                .bundesland("")
                .build();

        Adresse adresse_1_Mit_Daten = adresseBaseBuilder.build();
        Adresse adresse_1_Ohne_Daten = adresseBaseBuilder
                .strasse(".")
                .hausNo("")
                .stadt("")
                .plz("")
                .bundesland("")
                .build();

        return Stream.of(
                Arguments.of(requestDTO_1_Mit_Daten ,  adresse_1_Mit_Daten ,  true) ,
                Arguments.of(requestDTO_1_Mit_Daten ,adresse_1_Ohne_Daten , false) ,
                Arguments.of(requestDTO_1_Ohne_Daten , adresse_1_Ohne_Daten , true )
        );
    }

    @ParameterizedTest
    @MethodSource("test_boolean_AdresseRequestDTO_Adresse_Provider")
    @DisplayName("Gegeben AdresseRequestDTO und Adresse, überprüft ob die Mapping AdresseRequestDTO->Adresse gleich sein soll")
    void test_boolean_AdresseRequestDTO_Adresse_Mapping(AdresseRequestDTO input , Adresse output, boolean shouldEqual){

        var actual = subject.toAdresse(input);

        assertEquals( actual.equals( output ) , shouldEqual );
    }














    static Stream<Arguments> adresseRequestDTOProvider(){
        AdresseRequestDTO reuqestDTOmitDaten  = AdresseRequestDTO.builder()
                .strasse("Beispielstr.")
                .hausNo("55")
                .stadt("Duisburg")
                .plz("77777")
                .bundesland("Nordrhein-Westfalen").build();

        return Stream.of(
                Arguments.of(reuqestDTOmitDaten ,  true)
        );
    }

    boolean prufeRequestZuAdresseGleicheit(AdresseRequestDTO requestDTO){

        Adresse actual = subject.toAdresse(requestDTO);

        return actual.getStrasse().equals(requestDTO.getStrasse()) &&
                actual.getHausNo().equals(requestDTO.getHausNo()) &&
                actual.getPlz().equals(requestDTO.getPlz()) &&
                actual.getStadt().equals(requestDTO.getStadt()) &&
                actual.getBundesland().equals(requestDTO.getBundesland());
    }

    @ParameterizedTest
    @MethodSource("adresseRequestDTOProvider")
    @DisplayName("Korrekte Mapping AdresseRequestDTO -> Adresse ")
    void testRequestDTOzuAdresseGleichheit(AdresseRequestDTO requestDTO, boolean expected){
        assertEquals( expected , prufeRequestZuAdresseGleicheit(requestDTO));
    }

    static Stream<Arguments> provideAdresseUndDTO(){
        Adresse adresseMitDaten = Adresse.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();

        AdresseDTO adresseDTOMitDaten = AdresseDTO.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        AdresseDTO adresseDTOMitVerschiedeneStrasse = AdresseDTO.builder()
                .id(2L)
                .strasse("Kuhlenkötterweg ")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        AdresseDTO adresseDTOMitVerschiedeneID = AdresseDTO.builder()
                .id(2L)
                .strasse("Kuhlenkötterweg ")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();



        return Stream.of(
                Arguments.of(adresseMitDaten ,adresseDTOMitDaten , true) ,
                Arguments.of(adresseMitDaten ,adresseDTOMitVerschiedeneStrasse,false) ,
                Arguments.of(adresseMitDaten , adresseDTOMitVerschiedeneID , false)
        );
    }


    boolean prufeAdresseZuAdresseDTOGleicheit(Adresse adresse , AdresseDTO adresseDTO){
        return adresse.getId().equals(adresseDTO.getId()) &&
                adresse.getStrasse().equals(adresseDTO.getStrasse()) &&
                adresse.getHausNo().equals(adresseDTO.getHausNo()) &&
                adresse.getPlz().equals(adresseDTO.getPlz()) &&
                adresse.getStadt().equals(adresseDTO.getStadt()) &&
                adresse.getBundesland().equals(adresseDTO.getBundesland());
    }

    @ParameterizedTest
    @MethodSource("provideAdresseUndDTO")
    @DisplayName("Prüft die Gleichheit zwischen geg. Adresse und AdresseDTO")
    void testGleicheAdresseUndDTO(Adresse adresse , AdresseDTO adresseDTO , boolean expected){
        assertEquals(expected ,prufeAdresseZuAdresseDTOGleicheit(adresse,adresseDTO));
    }

    static Stream<Arguments> provideListOfAdresseUndDTO(){
        Adresse adresseMitDaten = Adresse.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        Adresse adresseDortmund = Adresse.builder()
                .id(100L)
                .strasse("Adessoplz.")
                .hausNo("43")
                .plz("43245")
                .stadt("Dortmund")
                .bundesland("Nordhein-Westfalen")
                .build();

        AdresseDTO adresseDTOMitDaten = AdresseDTO.builder()
                .id(1L)
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        AdresseDTO adresseDTOMitVerschiedeneStrasse = AdresseDTO.builder()
                .id(2L)
                .strasse("Kuhlenkötterweg ")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        AdresseDTO adresseDTOMitVerschiedeneID = AdresseDTO.builder()
                .id(2L)
                .strasse("Kuhlenkötterweg ")
                .hausNo("55")
                .plz("177")
                .stadt("Bochum")
                .bundesland("Nordhein-Westfalen")
                .build();
        AdresseDTO adresseDTODortmund = AdresseDTO.builder()
                .id(100L)
                .strasse("Adessoplz.")
                .hausNo("43")
                .plz("43245")
                .stadt("Dortmund")
                .bundesland("Nordhein-Westfalen")
                .build();



        return Stream.of(
                Arguments.of(List.of(adresseMitDaten) ,List.of(adresseDTOMitDaten) , true),
                Arguments.of(List.of(adresseMitDaten) ,
                        List.of(adresseDTOMitDaten, adresseDTOMitVerschiedeneID) , false ) ,
                Arguments.of(List.of(adresseMitDaten,adresseDortmund) ,
                        List.of(adresseDTOMitDaten,adresseDTODortmund) , false  )
        );


    }

    boolean prufeListAdresseUndAdresseDTO(List<Adresse> adresseList , List<AdresseDTO> adresseDTOList){
        boolean fin = true;
        // Prüfe jede Adresse in listen
        for(Adresse ad : adresseList ){
            if(adresseList.size()!=adresseDTOList.size()){
                fin = false;
                break;
            }
            for(AdresseDTO dto : adresseDTOList ){
                fin &= prufeAdresseZuAdresseDTOGleicheit(ad,dto);
            }
        }
        return fin;
    }

    @ParameterizedTest
    @MethodSource("provideListOfAdresseUndDTO")
    @DisplayName("Prüft die Gleichheit zwischen geg: Liste von Adressen und Liste von AdresseDTO")
    void testGleichheitListAdresseUndDTO(List<Adresse> adresseList ,  List<AdresseDTO> adresseDTOList ,
                                         boolean expected){
        assertEquals( expected ,  prufeListAdresseUndAdresseDTO(adresseList,adresseDTOList));
    }







}