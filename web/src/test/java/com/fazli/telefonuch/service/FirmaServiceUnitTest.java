package com.fazli.telefonuch.service;


import com.fazli.*;
import com.fazli.dto.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FirmaServiceUnitTest {
    private FirmaService testSubject;

    private FirmaRepo firmaRepoMock;
    private EntityManager emMock;

    @BeforeEach
    void setUp() {
        firmaRepoMock = Mockito.mock(FirmaRepo.class);
        emMock = Mockito.mock(EntityManager.class);
        testSubject = new FirmaService(firmaRepoMock, emMock);
    }

    //      Hinzufugen

    @Test
    void test_Firma_Hinzufuegen(){
        FirmaRequestDTO reqDTO = FirmaRequestDTO.builder()
                .name("Adesso SE")
                .adresse(List.of(new AdresseRequestDTO("Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branche(List.of("SOFTWARE"))
                .kontaktList(List.of(new FirmaKontaktRequestDTO(
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();
        Firma repoInput = Firma.builder()
                .name("Adesso SE")
                .adresse(List.of(new Adresse(null ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt(null ,
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();
        Firma repoOutput = Firma.builder()
                .id(31L)
                .name("Adesso SE")
                .adresse(List.of(new Adresse(13L ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();

        Mockito.when(firmaRepoMock.save(repoInput)).thenReturn(repoOutput);

        FirmaDTO output = testSubject.firmaHinzufuegen(reqDTO);

        assertEquals("Adesso SE",output.getName());
        assertEquals("0243535353" , output.getKontaktList().get(0).getFestnetznummer().get(0));
    }
    @Test
    void test_NeueAdresse_Hinzufuegen(){
        AdresseRequestDTO neuReqDTO =  AdresseRequestDTO.builder()
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("45234")
                .stadt("Bochum")
                .bundesland("Nordrhein-Westfallen")
                .build();
        Firma repoOutput = Firma.builder()
                .id(31L)
                .name("Adesso SE")
                .adresse(List.of(new Adresse(13L ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.findById(repoOutput.getId())).thenReturn(Optional.of(repoOutput));


        FirmaDTO output = testSubject.neueAdresseHinzufuegen(repoOutput.getId() , neuReqDTO);


        assertEquals(2, output.getAdresse().size());

    }
    @Test
    void test_NeueKontakte_Hizufuegen(){
        FirmaKontakt kontakt1 = new FirmaKontakt(
                List.of("0243535388"), List.of("firma@email.com"),
                List.of("firma.com") , List.of("0234673382")
        );
        Firma firma = Firma.builder()
                .id(31L)
                .name("Adesso SE")
                .adresse(List.of(new Adresse(13L ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(kontakt1))
                .build();

        FirmaKontaktRequestDTO kontaktRequestDTO = new FirmaKontaktRequestDTO(
                List.of("08884325637"), List.of("backupfirma@email.com"),
                List.of("firma.com/intern") , List.of()
        );

        Mockito.when(firmaRepoMock.findById(firma.getId())).thenReturn(Optional.of(firma));

        FirmaDTO output = testSubject.neueKontaktHinzufuegen(firma.getId(), kontaktRequestDTO);

        assertEquals(2, output.getKontaktList().size());

    }
    @Test
    void test_NeueBranche_Hizufuegen(){
        Firma firma = Firma.builder()
                .id(33L).name("RechtAnwalt Mauritz")
                .adresse(List.of())
                .kontaktList(List.of())
                .branchen(List.of())
                .build();

        Mockito.when(firmaRepoMock.findById(firma.getId())).thenReturn(Optional.of(firma));

        FirmaDTO actual = testSubject.neueBrancheHinzufuegen(33L, "ANWALT");

        assertEquals("ANWALT", actual.getBranche().get(0).toString());
        assertEquals(1 ,actual.getBranche().size());
    }


    //      Suchen
    @Test
    void test_Suche(){
        Firma repoOutput = Firma.builder()
                .id(31L)
                .name("Adesso SE")
                .adresse(List.of(new Adresse(13L ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();
        //Mockito.when(firmaRepoMock.genericSearch(null,null,null,null,null))
        //        .thenReturn(List.of(repoOutput));
        Mockito.when(firmaRepoMock.genericSearch("Adesso SE",null,null,null,null))
                .thenReturn(List.of(repoOutput));

        List<FirmaDTO> actual = testSubject.suche(null ,"Adesso SE" ,null,null,null, null);

        assertEquals(1, actual.size());
        assertEquals("Adesso SE",actual.get(0).getName());
    }
    @Test
    void test_Adressen_Liefern(){
        Firma repoOutput = Firma.builder()
                .id(31L)
                .name("Adesso SE")
                .adresse(List.of(new Adresse(13L ,"Adessoplz.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@adesso.com") ,
                        List.of("addeso.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.firmaAdresseLiefern(31L)).thenReturn(repoOutput.getAdresse());

        List<AdresseDTO> actual = testSubject.adresseLiefern(31L);

        assertEquals(1, actual.size());
        assertEquals("Adessoplz.", actual.get(0).getStrasse());
    }
    @Test
    void test_Kontakte_Liefern(){
        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfallen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.firmaKontaktLiefern(31L)).thenReturn(beispeilFirma.getKontaktList());

        List<FirmaKontaktDTO> actual = testSubject.firmaKontaktLiefern(31L);
        assertEquals(1, actual.size());
        assertEquals("02224536262", actual.get(0).getFaxnummer().get(0));
    }

    //     Aktualisieren
    @Test
    void test_Firma_Aendern(){
        FirmaRequestDTO neuFirma = FirmaRequestDTO.builder()
                .name("Aktualisierte Firma")
                .kontaktList(List.of(new FirmaKontaktRequestDTO(List.of("0243434332"),List.of("firma@email.com"),List.of(""),
                                List.of("02353535822"))))
                .adresse(List.of(new AdresseRequestDTO("Neustr.","22","45464","Berlin","Berlin")))
                .branche(List.of("ANWALT"))
                .build();
        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.findById(31L)).thenReturn(Optional.of(beispeilFirma));

        FirmaDTO actual = testSubject.firmaAendern(beispeilFirma.getId(),neuFirma);

        assertEquals("Aktualisierte Firma",actual.getName());
        assertEquals("45464", actual.getAdresse().get(0).getPlz());
        assertEquals("0243434332",actual.getKontaktList().get(0).getFestnetznummer().get(0));


    }
    @Test
    void test_Adresse_Aendern(){
        AdresseRequestDTO neu = new AdresseRequestDTO("Neustr.","22","45464","Berlin","Berlin");
        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.findById(31L)).thenReturn(Optional.of(beispeilFirma));

        FirmaDTO actual = testSubject.firmaAdresseAendern(31L,13L ,neu);

        assertEquals(1, actual.getAdresse().size());
        assertEquals("Berlin",actual.getAdresse().get(0).getStadt());
        assertNotEquals("Universität Str.",actual.getAdresse().get(0).getStrasse());

    }
    @Test
    void test_Kontakt_Aendern(){
        FirmaKontaktRequestDTO kontaktReqDTO = new FirmaKontaktRequestDTO(
                List.of("042525212"), List.of("firmafirma@outlook.com"),List.of("www.firmaneu.com"),List.of());

        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();

        Mockito.when(firmaRepoMock.findById(31L)).thenReturn(Optional.of(beispeilFirma));

        FirmaDTO actual = testSubject.firmaKontakAendern(31L,15L,kontaktReqDTO);

        assertEquals(1, actual.getKontaktList().size());
        assertNotEquals("0243535353",actual.getKontaktList().get(0).getFestnetznummer().get(0));
        assertEquals("firmafirma@outlook.com",actual.getKontaktList().get(0).getEmail().get(0));
    }

    //      Löschen
    @Test
    void test_Firma_Loeschen_Mit_ID(){
        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.existsById(31L)).thenReturn(true);
        //TODO: MOCK deletebyID?

        boolean b = testSubject.firmaLoeschen(31L,"Dortmund",null);
        assertTrue(b);
    }
    /**
     * METHODE WURDE GEÄNDERT
     * TODO: Macht es sinn Unit Test für diese Methode schreiben?
    @Test
    void test_Firma_Loeschen(){
        Firma beispeilFirma = Firma.builder()
                .id(31L)
                .name(List.of("Beispiel Firma"))
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Dortmund",
                        "Nordhrein-Westfalen")))
                .branchen(List.of(Branche.SOFTWARE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Firma firma = new Firma();
        Mockito.when(firmaRepoMock.search(31L, null,null,null,null,null))
                .thenReturn(List.of(beispeilFirma));
        Mockito.when(firmaRepoMock.search(null, null,null,null,null,null))
                .thenReturn(List.of(beispeilFirma,firma));

        boolean b = testSubject.firmaLoeschen(31L,null,null);

        assertTrue(b);
    }
     */
    @Test
    void test_Adresse_Entfernen(){
        Firma beispeilFirma = Firma.builder()
                .id(12L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Berlin",
                        "Berlin")))
                .branchen(List.of(Branche.ANWALT))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();

        Mockito.when(firmaRepoMock.findById(12L)).thenReturn(Optional.of(beispeilFirma));

        boolean actual = testSubject.adresseEntfernen(12L ,13L);

        assertTrue(actual);
        assertEquals(0, beispeilFirma.getAdresse().size());

    }
    @Test
    void test_Kontakt_Entfernen(){
        Firma beispeilFirma = Firma.builder()
                .id(12L)
                .name("Beispiel Firma")
                .adresse(List.of(new Adresse(13L ,"Universität Str.","55","56432","Berlin",
                        "Berlin")))
                .branchen(List.of(Branche.APOTHEKE))
                .kontaktList(List.of(new FirmaKontakt( 15L ,
                        List.of("0243535353") , List.of("info@firma.com") ,
                        List.of("firmaweb.com"),List.of("02224536262")
                )))
                .build();
        Mockito.when(firmaRepoMock.findById(12L)).thenReturn(Optional.of(beispeilFirma));

        boolean actual = testSubject.kontaktEntfernen(12L,15L);

        assertTrue(actual);
        assertEquals(0, beispeilFirma.getKontaktList().size());


    }

}