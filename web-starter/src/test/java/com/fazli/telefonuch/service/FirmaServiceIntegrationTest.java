package com.fazli.telefonuch.service;


import com.fazli.Branche;
import com.fazli.FirmaRepo;
import com.fazli.FirmaService;
import com.fazli.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ComponentScan("com.fazli")
@EntityScan(basePackages = {"com.fazli"})

class FirmaServiceIntegrationTest {



    @Autowired
    private FirmaService testSubject;
    @Autowired
    private FirmaRepo firmaRepo;


    @BeforeEach
    void setUp() {

        firmaRepo.deleteAll();

        FirmaRequestDTO adessoRequestDTO = new FirmaRequestDTO(
                "adesso SE",
                List.of(new AdresseRequestDTO("Adessoplz.","1","44269","Dortmund","Nordrhein-Westfalen")),
                List.of(String.valueOf(Branche.SOFTWARE)),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("0123345834"),
                        List.of(""),
                        List.of(""),
                        List.of("")
                ))
        );

        FirmaRequestDTO AOK_NORTHWEST = new FirmaRequestDTO(
                "AOK NORTHWEST",
                List.of(new AdresseRequestDTO("Friedrich-Ebert-Platz","2","58095","Hagen","Nordrhein-Westfalen")),
                List.of(String.valueOf(Branche.VERSICHERUNG)),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("080002655000"),     // festnetz
                        List.of("info@aok.de"),     // email
                        List.of("www.aok.de"),                // webseite
                        List.of("05347867564")                 // faxno
                ))
        );
        FirmaRequestDTO VONOVIA = new FirmaRequestDTO(
                "Vonovia",
                List.of(new AdresseRequestDTO("Universitätstr.","133","44803","Bochum","Nordrhein-Westfalen")),
                List.of(String.valueOf(Branche.WOHNUNGSWIRTSCHAFT)),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("080002535403"),     // festnetz
                        List.of("info@vonovia.de"),     // email
                        List.of("www.vonovia.de"),                // webseite
                        List.of("053478335469")                 // faxno
                ))
        );


        testSubject.firmaHinzufuegen(adessoRequestDTO);
        testSubject.firmaHinzufuegen(AOK_NORTHWEST);
        testSubject.firmaHinzufuegen(VONOVIA);

    }

    @Test
    void sollte_eine_FirmaHinzufuegen() {

        int anzahlBevor = testSubject.suche(null,null,null,null,null,null).size();
        assertTrue(anzahlBevor > 0);

        FirmaRequestDTO mauritzRequestDTO = new FirmaRequestDTO(
                "Andreas Mauritz Rechtsanwälte",
                List.of(new AdresseRequestDTO("Poststr.","12","73033","Göppingen","Baden-Württemberg")),
                List.of(String.valueOf(Branche.ANWALT)),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("0345345834"),
                        List.of("mauritz@anwalt.de"),
                        List.of("rechtsanwaltmauritz.com"),
                        List.of("022331281995")
                ))
        );

        FirmaDTO firmaResponseDTO = testSubject.firmaHinzufuegen(mauritzRequestDTO);
        int anzahlDanach = testSubject.suche(null,null,null,null,null,null).size();
        assertNotNull(firmaResponseDTO);
        assertEquals(1, anzahlDanach-anzahlBevor);
    }

    @Test
    void sollte_eine_NeueAdresse_Hinzufuegen() {

        Long firmaId = testSubject.suche(null, "adesso SE", null, "Dortmund", null, null)
                .get(0).getId();
        AdresseRequestDTO neueAdresse = new AdresseRequestDTO("Neue Strasse", "10", "12345", "München", "Bayern");

        FirmaDTO aktualisierteFirma = testSubject.neueAdresseHinzufuegen(firmaId, neueAdresse);


        List<AdresseDTO> addressList = aktualisierteFirma.getAdresse();
        assertNotNull(addressList);
        assertEquals(2, addressList.size());
        assertTrue(addressList.stream().anyMatch(
                adresse -> adresse.getStrasse().equals("Neue Strasse") &&
                        adresse.getHausNo().equals("10") &&
                        adresse.getPlz().equals("12345") &&
                        adresse.getStadt().equals("München") &&
                        adresse.getBundesland().equals("Bayern")
        ));

    }



    @Test
    void sollte_NeueKontakt_Hinzufuegen() {
        Long firmaId = testSubject.suche(null, "adesso SE", null, "Dortmund", null, null)
                .get(0).getId();

        FirmaKontaktRequestDTO kontaktRequestDTO = new FirmaKontaktRequestDTO(
                List.of("012392331"),
                List.of("adesso@info.de"),
                List.of("www.adesso.de"),
                List.of("0198763728")
        );

        FirmaDTO aktualisierteAdesso = testSubject.neueKontaktHinzufuegen(firmaId,kontaktRequestDTO);

        List<FirmaKontaktDTO> firmaKontaktDTOList = aktualisierteAdesso.getKontaktList();
        assertNotNull(firmaKontaktDTOList);
        assertTrue(firmaKontaktDTOList.stream().anyMatch(
                firmaKontaktDTO -> firmaKontaktDTO.getFestnetznummer().stream().anyMatch(fest->fest.equals("012392331")) &&
                        firmaKontaktDTO.getEmail().stream().anyMatch(email->email.equals("adesso@info.de")) &&
                        firmaKontaktDTO.getWebseite().stream().anyMatch(webseite->webseite.equals("www.adesso.de")) &&
                        firmaKontaktDTO.getFaxnummer().stream().anyMatch(fax->fax.equals("0198763728"))
        ));


    }




    @ParameterizedTest
    @CsvSource(
            {
                    "apotheke", "APOTHEKE" ,
                    "software", "SOFTWARE", "SoFtWaRe",
                    "arzt", "ARZT",
                    "versicherung","VERSICHERUNG",
                    "WOHNUNGSWIRTSCHAFT" , "wOhNungsWirtschaft"
            }
    )
    void sollte_NeueBranche_Hinzufuegen(String branche) {
        FirmaRequestDTO mauritzRequestDTO = new FirmaRequestDTO(
                "Andreas Mauritz Rechtsanwälte",
                List.of(new AdresseRequestDTO("Poststr.","12","73033","Göppingen","Baden-Württemberg")),
                List.of(),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("0345345834"),
                        List.of("mauritz@anwalt.de"),
                        List.of("rechtsanwaltmauritz.com"),
                        List.of("022331281995")
                ))
        );
        int bevor = mauritzRequestDTO.getBranche().size();
        Long id = testSubject.firmaHinzufuegen(mauritzRequestDTO).getId();

        FirmaDTO aktualisierteFirma = testSubject.neueBrancheHinzufuegen(id,branche);

        assertNotNull(aktualisierteFirma);
        assertNotNull(aktualisierteFirma.getBranche());
        List<String> brancheList = testSubject.suche(id,null,null,null,null,null)
                .get(0).getBranche();
        assertEquals(1, brancheList.size()-bevor);

    }

    @ParameterizedTest
    @CsvSource({
            "_ANWALT","ANVALT", "Pädagoge"
    })
    void sollte_neueBranche_NICHT_Hinzufuegen(String branche) {
        FirmaDTO vonovia = testSubject.suche(null, "vonovia" , null, null , null, null)
                .get(0);
        int bevor = vonovia.getBranche().size();

        FirmaDTO aktualisierteVonovia = testSubject.neueBrancheHinzufuegen(vonovia.getId(),branche);

        int danach = aktualisierteVonovia.getBranche().size();

        assertEquals(bevor,danach);
        assertNotNull(aktualisierteVonovia.getBranche());
        assertNotNull(aktualisierteVonovia);


    }

    @Test
    void test_suche() {

        List<FirmaDTO> lst = testSubject.suche(null,null,null,"Recklinghausen",null,null);
        assertEquals(0,lst.size());
        lst = testSubject.suche(null,null,null,"Dortmund",null,null);
        assertEquals(1,lst.size());

    }


    @Test
    void test_suche_2(){
        List<FirmaDTO> alleFirmen= testSubject.suche(null,null,null,null,null,null);
        List<FirmaDTO> firmen_In_Dortmund = testSubject.suche(null,null,null,"Dortmund",null,null);
        List<FirmaDTO> firmen_Dortmund_Software = testSubject.suche(null,null,null,"Dortmund",
                null,"SOFTWARE");
        List<FirmaDTO> firmen_In_Hamburg = testSubject.suche(null,null,null,"Hamburg",null,null);
        List<FirmaDTO> adesso = testSubject.suche(null,"Adesso",null,null,null,null);

        assertEquals(3, alleFirmen.size());
        assertEquals(1, firmen_In_Dortmund.size());
        assertEquals(firmen_Dortmund_Software.get(0) ,firmen_In_Dortmund.get(0));
        assertEquals(0,firmen_In_Hamburg.size());
        assertEquals(true,alleFirmen.contains(firmen_In_Dortmund.get(0)));

    }


    @ParameterizedTest
    @CsvSource({
            "ADESSO SE" , "adesso SE", "AdESSO se"
    })
    void test_suche_Name_Case_Sensitivity(String firmaname ){

        List<FirmaDTO> actual = testSubject.suche(null,firmaname,null,null,null,null);

        assertEquals(1,actual.size());
    }

    @ParameterizedTest
    @CsvSource({
            "AD", "von","AOK"
    })
    void test_suche_Nach_NameMatching(String firmaname ){
        List<FirmaDTO> actual = testSubject.suche(null,firmaname,null,null,null,null);

        assertEquals(1,actual.size());
    }

    @Test
    void sollte_Adressen_Liefern() {
        List<AdresseDTO> lst = testSubject.adresseLiefern(null);
        assertNotNull(lst);
        assertEquals(3,lst.size());

        Long adessoID = testSubject.suche(null,"adesso SE",null,"Dortmund",null,null)
                .get(0).getId();
        List<AdresseDTO> adessoAdresse = testSubject.adresseLiefern(adessoID);
        assertEquals(1,adessoAdresse.size());
    }

    @Test
    void firmaKontaktLiefern() {
        int alleKontaken = testSubject.firmaKontaktLiefern(null).size();

        Long vonoviaID = testSubject.suche(null,"Vonovia",null,null,null,null)
                .get(0).getId();



        testSubject.neueKontaktHinzufuegen(vonoviaID, new FirmaKontaktRequestDTO(
                List.of("0456856483"),
                List.of(""),
                List.of("kontakt@vonovia.de"),
                List.of(""))
        );

        int aktualisierteKontakten = testSubject.firmaKontaktLiefern(null).size();
        assertEquals(3,alleKontaken);
        assertNotNull(vonoviaID);
        assertEquals(1,aktualisierteKontakten-alleKontaken);
        assertEquals(2, testSubject.firmaKontaktLiefern(vonoviaID).size());



    }


    @Test
    void sollte_firmaAendern() {
        FirmaRequestDTO neuVonovia = new FirmaRequestDTO(
                "Vonovia Unternehmenszentrale",
                List.of(new AdresseRequestDTO("Universitätstr.","133","44803","Bochum","Nordrhein-Westfalen")),
                List.of(String.valueOf(Branche.WOHNUNGSWIRTSCHAFT)),
                List.of(new FirmaKontaktRequestDTO(
                        List.of("080002535403"),     // festnetz
                        List.of("info@vonovia.de"),     // email
                        List.of("www.vonovia.de"),                // webseite
                        List.of("053478335469")                 // faxno
                ))
        );
        FirmaDTO vonovia = testSubject.suche(null,"Vonovia",null,null,null,null).get(0);

        testSubject.firmaAendern(vonovia.getId() , neuVonovia);

        FirmaDTO aktualisierteVonovia = testSubject.suche(null,"Vonovia Unternehmenszentrale",null,null,null,null).get(0);

        assertNotNull(vonovia);
        assertEquals("Vonovia Unternehmenszentrale",aktualisierteVonovia.getName());


    }



    @Test
    void sollte_firmaAdresseAendern() {
        FirmaDTO vonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        long pid  = vonovia.getId();
        long aid = vonovia.getAdresse().get(0).getId();
        AdresseRequestDTO adREQ =new AdresseRequestDTO("Brückstr.","133","44803","Bochum","Nordrhein-Westfalen");

        FirmaDTO aktualisierteVonovia = testSubject.firmaAdresseAendern(pid , aid , adREQ);

        assertNotNull(aktualisierteVonovia);
        assertEquals(1,aktualisierteVonovia.getAdresse().size());
        assertTrue(aktualisierteVonovia.getAdresse().stream().anyMatch(
                adresseDTO -> adresseDTO
                        .getStrasse().equals("Brückstr.") &&
                        adresseDTO.getHausNo().equals("133")
        ));
    }

    @Test
    void firmaKontakAendern() {

        FirmaDTO vonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        long pid  = vonovia.getId();
        long kid = vonovia.getKontaktList().get(0).getId();


        FirmaKontaktRequestDTO neuKontakt = new FirmaKontaktRequestDTO(
                List.of("033337777222"),     // festnetz
                List.of("test@vonovia.de"),     // email
                List.of("www.test_vonovia.de"),                // webseite
                List.of("053478335469"));

        FirmaDTO aktuell = testSubject.firmaKontakAendern(pid ,kid , neuKontakt);
        assertEquals(1, aktuell.getKontaktList().size());
        assertTrue(aktuell.getKontaktList().stream().anyMatch(
                firmaKontaktDTO -> firmaKontaktDTO.getEmail().get(0).equals("test@vonovia.de")
        ));

    }

    @Test
    void sollte_Eine_FirmaLoeschen_Nach_Stadt() {
        List<FirmaDTO> bevor = testSubject.suche(null,null,null,null,null,null);

        boolean actual =testSubject.firmaLoeschen(null,"Dortmund",null);

        List<FirmaDTO> danach = testSubject.suche(null,null,null,null,null,null);
        assertTrue(actual);
        assertEquals(1, bevor.size()-danach.size());
    }

    @Test
    void sollte_Firma_Nicht_Loeschen_Nach_NichtExistierendeStadt() {
        List<FirmaDTO> anzahlDerFirmen = testSubject.suche(null,null,null,null,null,null);
        String nichtExistierendeStadt = "Göppingen";

        boolean actual = testSubject.firmaLoeschen(null,nichtExistierendeStadt,null);

        List<FirmaDTO> anzahlFirmenDANACH = testSubject.suche(null,null,null,null,null,null);
        assertFalse(actual);
        assertEquals(anzahlDerFirmen.size(), anzahlFirmenDANACH.size());
    }

    //TODO: Postman -> funktioniert ; HIER: Firma wird nicht gespeichert
   @Test
   void sollte_Mehrere_Firmen_Loeschen_NachStadt(){
       // Gegeben
        FirmaRequestDTO reqDTO1 =new FirmaRequestDTO("Firma1",List.of(
               new AdresseRequestDTO("str1","23","41786","München","Bayern")),
               List.of(),List.of());
       FirmaRequestDTO reqDTO2 = new FirmaRequestDTO("Firma2",List.of(
               new AdresseRequestDTO("str2","55","42187","München","Bayern"))
               ,List.of(),List.of());
       FirmaDTO dto1 = testSubject.firmaHinzufuegen(reqDTO1);
       FirmaDTO dto2 = testSubject.firmaHinzufuegen(reqDTO2);

       List<FirmaDTO> anzahlDerFirmen = testSubject.suche(null,null,null,"München",null,null);

       // Wenn
       boolean actual = testSubject.firmaLoeschen(null,"München",null);

       // Dann
       List<FirmaDTO> anzahlFirmenDANACH = testSubject.suche(null,null,null,"München",null,null);
       assertTrue(actual);
       assertEquals(2,anzahlDerFirmen.size()-anzahlFirmenDANACH.size());
       assertEquals(0,anzahlFirmenDANACH.size());
   }




    @Test
    void sollte_Eine_FirmaLoeschen_Nach_Bundesland(){
        FirmaRequestDTO firmaRequestDTO = FirmaRequestDTO.builder()
                .name("Firma Berlin")
                .adresse(List.of(new AdresseRequestDTO("","","","Berlin","Berlin")))
                .branche(List.of("ANWALT"))
                .kontaktList(List.of())
                .build();
        testSubject.firmaHinzufuegen(firmaRequestDTO);
        List<FirmaDTO> bevor = testSubject.suche(null,null,null,null,"Berlin",null);

        boolean actual = testSubject.firmaLoeschen(null,null,"Berlin");

        List<FirmaDTO> danach = testSubject.suche(null,null,null,null,"Berlin",null);
        assertTrue(actual);
        assertEquals(1,bevor.size()-danach.size());
    }



    @Test
    void sollte_MehrereFirmen_Loeschen_Nach_Bundesland(){

        List<FirmaDTO> bevor = testSubject.suche(null,null,null,null,"Nordrhein-Westfalen",null);

        boolean actual = testSubject.firmaLoeschen(null,null,"Nordrhein-Westfalen");

        List<FirmaDTO> danach = testSubject.suche(null,null,null,null,"Nordhein-Westfalen",null);

        assertTrue(actual);
        assertEquals(3,bevor.size());
        assertEquals(0, danach.size());
    }




    @Test
    void sollte_Firma_Nicht_Loeschen_Nach_Nicht_ExistierendeID(){
        FirmaRequestDTO firmaRequestDTO = new FirmaRequestDTO("Firma4",List.of(),List.of(),List.of());
        testSubject.firmaHinzufuegen(firmaRequestDTO);

        long nichtExistierendeID = 1L;
        while(firmaRepo.findById(nichtExistierendeID).isPresent()){nichtExistierendeID++;}

        boolean actual = testSubject.firmaLoeschen(nichtExistierendeID,null,null);

        assertFalse(actual);
        assertEquals(4,firmaRepo.count());
    }


    @Test
    void sollte_adresseEntfernen() {
        FirmaDTO vonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        long id  = vonovia.getId();
        long aid = vonovia.getAdresse().get(0).getId();
        AdresseRequestDTO adREQ =new AdresseRequestDTO("Brückstr.","133","44803","Bochum","Nordrhein-Westfalen");
        testSubject.neueAdresseHinzufuegen(id , adREQ);


        boolean actual =testSubject.adresseEntfernen(id,aid);

        assertTrue(actual);
        FirmaDTO aktualisierteVonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        assertEquals(1,aktualisierteVonovia.getAdresse().size());

    }



    @Test
    void sollte_Adressen_Mit_FalschenID_Nicht_Entfernen(){
        FirmaRequestDTO requestDTO1 = new FirmaRequestDTO("Firma1",List.of(
                new AdresseRequestDTO("Bspstr.","55","44563","Bochum","Nordrhein-Westfalen")
        )
                ,List.of(),List.of());
        FirmaRequestDTO requestDTO2 = new FirmaRequestDTO("Firma2",List.of(
                new AdresseRequestDTO("Wasserstr.","12","49567","Berlin","Berlin")
        ),List.of(),List.of());

        FirmaDTO firmaDTO1 = testSubject.firmaHinzufuegen(requestDTO1);
        FirmaDTO firmaDTO2 = testSubject.firmaHinzufuegen(requestDTO2);

        // Versucht, die Adresse mit ID von dto2 aus Adressen von dto1 löschen
        boolean actual = testSubject.adresseEntfernen(firmaDTO1.getId() , firmaDTO2.getAdresse().get(0).getId());

        // aktualisiere DTO's
        FirmaDTO aktualisierteFirma1 = testSubject.suche(firmaDTO1.getId(),null,null,null,null,null).get(0);
        FirmaDTO aktualisierteFirma2 = testSubject.suche(firmaDTO2.getId(),null,null,null,null,null).get(0);

        assertFalse(actual);
        assertEquals(1,aktualisierteFirma1.getAdresse().size());
        assertEquals("Bspstr.",aktualisierteFirma1.getAdresse().get(0).getStrasse());
        assertEquals(1,aktualisierteFirma2.getAdresse().size());
        assertEquals("49567",aktualisierteFirma2.getAdresse().get(0).getPlz());
    }

    @Test
    void sollte_AndereAdressen_Nicht_Entfernen(){
        //Wenn eine adresse gelöscht wird-> muss die anderen unverändert bleiben
        FirmaRequestDTO requestDTO1 = new FirmaRequestDTO("Firma1",List.of(
                new AdresseRequestDTO("Bspstr.","55","44563","Bochum","Nordrhein-Westfalen"),
                new AdresseRequestDTO("Str2","12","49567","Berlin","Berlin"),
                new AdresseRequestDTO("Str3","88","44789","Dortmund","Nordrhein-Westfalen")
        )
                ,List.of(),List.of());
        FirmaDTO firmaDTO1 = testSubject.firmaHinzufuegen(requestDTO1);

        boolean actual = testSubject.adresseEntfernen(firmaDTO1.getId() , firmaDTO1.getAdresse().get(0).getId());

        FirmaDTO aktualisierteFirma1 = testSubject.suche(firmaDTO1.getId(),null,null,null,null,null).get(0);
        assertTrue(actual);
        assertEquals(2,aktualisierteFirma1.getAdresse().size());
        boolean str2_exists = aktualisierteFirma1.getAdresse().stream().anyMatch(ad->ad.getStrasse().equals("Str2"));
        assertTrue(str2_exists);
        boolean str3_exists = aktualisierteFirma1.getAdresse().stream().anyMatch(ad->ad.getStrasse().equals("Str3"));
        assertTrue(str3_exists);

    }

    @Test
    void sollte_Firma_Nicht_Entfernen(){
        // Wenn einzige Adresse einer Firma gelöscht wird -> Firma sollte nicht gelöscht werden
        FirmaRequestDTO requestDTO = new FirmaRequestDTO("Firma1", List.of(
                new AdresseRequestDTO("Str1","9","48123","Berlin","Berlin")),
                List.of(),List.of());
        FirmaDTO dto = testSubject.firmaHinzufuegen(requestDTO);

        boolean b = testSubject.adresseEntfernen(dto.getId() , dto.getAdresse().get(0).getId());
        FirmaDTO actualFirma= testSubject.suche(dto.getId(),null,null,null,null,null).get(0);

        assertTrue(b);
        assertNotNull(actualFirma);
        assertEquals( 0 , actualFirma.getAdresse().size());
        assertEquals("Firma1",actualFirma.getName());
    }

    @Test
    void sollte_KontaktEntfernen() {
        FirmaDTO vonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        long id  = vonovia.getId();
        long kid = vonovia.getKontaktList().get(0).getId();

        boolean b =testSubject.kontaktEntfernen( id , kid );

        assertTrue(b);
        FirmaDTO aktualisierteVonovia = testSubject.suche(null,"Vonovia" ,
                null , null, null,null).get(0);
        assertEquals(0,aktualisierteVonovia.getKontaktList().size());
    }

    @Test
    void sollte_Kontakt_MitFalschenID_Nicht_Entfernen(){
        FirmaRequestDTO requestDTO1 = new FirmaRequestDTO("Firma1",List.of(),List.of(),
                List.of(new FirmaKontaktRequestDTO(List.of("0154626263"),List.of("firma1@email.com")
                        ,List.of("www.firma.com"),List.of("04534325456"))));
        FirmaRequestDTO requestDTO2 = new FirmaRequestDTO("Firma2",List.of(),List.of(),
                List.of(new FirmaKontaktRequestDTO(List.of("01765438978"),List.of("firma2@email.com")
                        ,List.of("www.firma2.com"),List.of("04563738282"))));
        FirmaDTO dto1 = testSubject.firmaHinzufuegen(requestDTO1);
        FirmaDTO dto2 = testSubject.firmaHinzufuegen(requestDTO2);

        // kontakt entfernen mit falscehn id
        boolean actual = testSubject.kontaktEntfernen(dto1.getId(),dto2.getKontaktList().get(0).getId());

        FirmaDTO aktualisierteDTO1 = testSubject.suche(dto1.getId(),null , null,null,null,null).get(0);
        FirmaDTO aktualisiertDTO2 = testSubject.suche(dto2.getId(),null , null,null,null,null).get(0);
        // Die FirmaKontakt sollte nicht gelöscht werden
        // FirmaKontakt von firma 1 -> da kid nicht existiert sollte die existierende Kontakt nicht gelöscht
        assertFalse(actual);
        assertEquals("0154626263",aktualisierteDTO1.getKontaktList().get(0).getFestnetznummer().get(0));
        // FirmaKontakt von firma 2 , muss unverändert bleiben
        assertEquals("01765438978",aktualisiertDTO2.getKontaktList().get(0).getFestnetznummer().get(0));
    }

    @Test
    void sollte_Anderen_Kontakten_Unveraendert_Bleiben(){
        // Wenn eine Firma Kontakt gelöscht wird, muss die anderen Kontakten unverändert bleiben
        FirmaRequestDTO firma1 = new FirmaRequestDTO("Apple Inc",List.of(),List.of("SOFTWARE"),
                List.of(new FirmaKontaktRequestDTO(List.of("0234556672565"),List.of("Apple1@email,com")
                        ,List.of("www.apple.com"),List.of("04534325456")) ,
                        new FirmaKontaktRequestDTO(List.of("023455688888"),List.of("Apple2@email,com")
                                ,List.of("www.apple2.com"),List.of("04534327777") )
                        ));
        FirmaDTO dto1 = testSubject.firmaHinzufuegen(firma1);

        // Lösche  erste Firma kontakt
        boolean actual = testSubject.kontaktEntfernen(dto1.getId(),dto1.getKontaktList().get(0).getId());
        FirmaDTO aktualisierteDTO1 = testSubject.suche(dto1.getId(),null , null,null,null,null).get(0);

        assertTrue(actual);
        assertEquals(1, aktualisierteDTO1.getKontaktList().size());
        assertEquals("023455688888",aktualisierteDTO1.getKontaktList().get(0).getFestnetznummer().get(0));
    }
}