package com.fazli.telefonuch.model.dto;

import com.fazli.PersonKontakt;
import com.fazli.dto.PersonKontaktDTO;
import com.fazli.dto.PersonKontaktDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PersonKontaktDTOMapperTest {

    PersonKontaktDTOMapper testSubject = new PersonKontaktDTOMapper();

    @Test
    void testToDTO() {
        //given
        PersonKontakt personKontakt = new PersonKontakt();
        personKontakt.setFestnetzNummer(List.of("01626622"));
        personKontakt.setEmail(List.of("email@gmail.com"));
        personKontakt.setWebseite(List.of("webseite.com"));
        personKontakt.setMobilNummern(List.of("0338383833"));

        //when
        PersonKontaktDTO actual = testSubject.toDTO(personKontakt);

        // then
        assertEquals(personKontakt.getEmail(), actual.getEmail());
        assertEquals(personKontakt.getWebseite(), actual.getWebseite());
        assertEquals(personKontakt.getFestnetzNummer() , actual.getFestnetznummer());
        assertEquals(personKontakt.getMobilNummern(),  actual.getMobilnummern());
    }






}