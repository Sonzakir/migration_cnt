package com.fazli.telefonuch.service;


import com.fazli.PersonRepo;
import com.fazli.PersonService;
import com.fazli.dto.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // org.hibernate.LazyInitializationException
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService testSubject;
    @Autowired
    private PersonRepo personRepo;




    @BeforeEach
    void setUp(){

        personRepo.deleteAll();

        PersonRequestDTO personMe = new PersonRequestDTO(
               "Fazli Soner" ,  "Kiraz",
                List.of(new AdresseRequestDTO("Kuhlenlkötterweg","3","44795", "Bochum","Nordrhein-Westfallen"))
                ,List.of(new PersonKontaktRequestDTO(
                List.of("034527272"),List.of("sonerkiraz2000@hotmail.com"),
                List.of("sonerkiraz.com"),List.of("01765436789"))
        ));

        PersonRequestDTO friedrichRequestDTO = new PersonRequestDTO(
                "Friedrich", "Schmier" ,
                List.of(new AdresseRequestDTO("Universitätstr.","66","41882", "Dortmund","Nordrhein-Westfallen"))
                ,List.of(new PersonKontaktRequestDTO(
                List.of(""),List.of("friedrichschmier@hotmail.com"),
                List.of("schmierfriedrich.com"),List.of("01776588323"))
        ));


        PersonRequestDTO maraRequestDTO = new PersonRequestDTO(
                "Mara" , "Schwarzwald",
                List.of(new AdresseRequestDTO("Rotebühlstr.","101","70178 ", "Stuttgart","Baden-Württemberg"))
                ,List.of(new PersonKontaktRequestDTO(
                List.of("0711 65 67 97-60 "),List.of("maraschwarz@gmail.com"),
                List.of("maraschwarzwald.com"),List.of(""))
        ));

        testSubject.einePersonHinzufuegen(personMe);
        testSubject.einePersonHinzufuegen(friedrichRequestDTO);
        testSubject.einePersonHinzufuegen(maraRequestDTO);

        int size = testSubject.suche(null,null,null,null,null).size();
        assertEquals(3,size);



    }

    @Test
    void sollte_Eine_PersonHinzufuegen() {

        // Gegeben
        Long anzahlPersonBevor = testSubject.anzahlDerPersonen();
        PersonRequestDTO marioRequestDTO = new PersonRequestDTO(
                "Mario" , "Hönig",
                List.of(new AdresseRequestDTO("Hallesche Str.","102","44143 ", "Dortmund","Baden-Württemberg"))
                ,List.of(new PersonKontaktRequestDTO(
                List.of("023115620840 "),List.of("mariohönig@gmail.com"),
                List.of(""),List.of("01678953424"))
        ));

        // Wenn
        PersonDTO marioResponseDTO = testSubject.einePersonHinzufuegen(marioRequestDTO);

        // Dann

        assertNotNull(marioResponseDTO);
        assertEquals("Mario",marioResponseDTO.getVorname());
        assertEquals("Hönig",marioResponseDTO.getNachname());
        Long anzahlPersonDanach = testSubject.anzahlDerPersonen();
        assertEquals(1, anzahlPersonDanach - anzahlPersonBevor);
    }




    @Test
    void sollte_Eine_neueAdresseHinzufuegen_Nach_PersonID() {


        // Gegeben
        AdresseRequestDTO adresseRequestDTO = new AdresseRequestDTO(
                "Goppstr.","2","33678","Duisburg","Baden-Württemberg"
        );

        Long maraID = testSubject.suche(null,"mara",null,null,null).get(0).getId();

        // Wenn
        PersonDTO aktualisierteMara = testSubject.neueAdresseHinzufuegen(maraID,adresseRequestDTO);


        // Dann
        List<PersonDTO> lst = testSubject.suche(maraID,null,null,"Duisburg",null);
        assertEquals(1,lst.size());

    }

    @Test
    void sollte_Eine_neueAdresseHinzufuegen_Nach_PersonID_2(){
        // Gegeben
        AdresseRequestDTO adresseRequestDTO =  new AdresseRequestDTO("Berliner Allee", "99", "40212", "Düsseldorf", "Nordrhein-Westfalen");
        PersonRequestDTO johannRequestDTO = new PersonRequestDTO(
                "Johann", "Müller",
                List.of(new AdresseRequestDTO("Hauptstraße", "45", "50667", "Köln", "Nordrhein-Westfalen")),
                List.of(new PersonKontaktRequestDTO(
                        List.of("02211234567"), // Festnetznummer
                        List.of("johann.mueller@example.com"), // Email
                        List.of("https://muellerjohann.de"), // Webseite
                        List.of("01511223344") // Mobilnummer
                ))
        );
        PersonDTO johannDTO = testSubject.einePersonHinzufuegen(johannRequestDTO);
        Long id = johannDTO.getId();

        // Wenn
        PersonDTO aktualisierteJohann = testSubject.neueAdresseHinzufuegen( id , adresseRequestDTO);

        // Dann
        assertEquals(2,aktualisierteJohann.getAdresse().size());

    }


    @Test
    void neueKontaktHinzufuegen() {
        
        PersonKontaktRequestDTO personKontaktRequestDTO = new PersonKontaktRequestDTO(
                List.of("0176538495") , //Festnetz
                List.of("backupfriedrich@gmail.com") , //email
                List.of("friedrich.blogspot.com") , // webseite
                List.of("01789875422")   // mobilnummer
        );
        PersonDTO friedrich = testSubject.suche(null,"friedrich",null,null,null).get(0);
        int bevor = friedrich.getKontaktList().size();

        PersonDTO aktualisierteFriedrich = testSubject.neueKontaktHinzufuegen(friedrich.getId(),personKontaktRequestDTO);
        aktualisierteFriedrich = testSubject.suche(null,"friedrich",null,null,null).get(0);

        int danach = aktualisierteFriedrich.getKontaktList().size();
        assertEquals(1,danach-bevor);
        assertTrue(aktualisierteFriedrich.getKontaktList().stream().anyMatch(
                personKontaktDTO -> personKontaktDTO.getFestnetznummer()
                        .stream().anyMatch(num->num.equals("0176538495"))&&
                        personKontaktDTO.getEmail().stream()
                                .anyMatch(email->email.equals("backupfriedrich@gmail.com")) &&
                        personKontaktDTO.getWebseite().stream()
                                .anyMatch(web->web.equals("friedrich.blogspot.com")) &&
                        personKontaktDTO.getMobilnummern().stream()
                                .anyMatch(mobil->mobil.equals("01789875422"))

        ));

    }


    @Test
    void sollte_Person_Liefern_Nach_Stadt(){

        PersonDTO actual =  testSubject.suche(null,null,null,"Stuttgart",null)
                .get(0);

        assertNotNull(actual);
        assertEquals("Stuttgart", actual.getAdresse().get(0).getStadt());
    }

    @Test
    void sollte_keine_PersonLiefern_Nach_Nicht_Existierende_Stadt() {
        List<PersonDTO> actual = testSubject.suche(null,null,null,"Recklinghausen",null);
        assertEquals(0,actual.size());

    }

    @ParameterizedTest
    @CsvSource({
            "Boc","BOCHum","Dort","DORTMUn","Stut","STUTT"
    })
    void test_suche_Nach_Stadt_String_Marching(String stadt){
        List<PersonDTO> actual = testSubject.suche(null,null,null,stadt,null);
        assertEquals(1,actual.size());

    }

    @Test
    void sollte_PersonLiefern_Nach_Name(){

        List<PersonDTO> actual= testSubject.suche(null,"Mara",null,null,null);

        assertEquals(1,actual.size());
    }

    @ParameterizedTest
    @CsvSource({
            "mara","MARA", "mArA", "Soner", "FAZLI"
    })
    void test_suche_Name_Case_Sensitivity(String vorname){
        List<PersonDTO> actual = testSubject.suche(null,vorname,null,null,null);
        assertEquals(1,actual.size());
    }

    @Test
    void sollte_Keine_PersonLiefern_Nach_Nicht_Existierende_Name(){
        List<PersonDTO> lst= testSubject.suche(null,"Florian",null,null,null);

        assertEquals(0,lst.size());
    }

    @Test
    void sollte_Alle_AdressenLiefern() {
        List<AdresseDTO> lst = testSubject.adresseLiefern(null);
        assertNotNull(lst);
        assertEquals(3,lst.size());
    }

    @Test
    void sollte_PersonAdressenLiefern_Nach_ID(){
        Long fazliID = testSubject.suche(null,"fazli",null,null,null)
                .get(0).getId();

        List<AdresseDTO> fazliAdresse = testSubject.adresseLiefern(fazliID);

        assertEquals(1,fazliAdresse.size());

    }


    @Test
    void sollte_Eine_PersonAendern_Nach_ID() {

        // Gegeben
        Long friedrichId = testSubject.suche(null,"Friedrich",null,null,null)
                .get(0).getId();

        PersonRequestDTO marioRequestDTO = new PersonRequestDTO(
                "Mario" , "Hönig",
                List.of(new AdresseRequestDTO("Hallesche Str.","102","44143 ", "Dortmund","Baden-Württemberg"))
                ,List.of(new PersonKontaktRequestDTO(
                List.of("023115620840 "),List.of("mariohönig@gmail.com"),
                List.of(""),List.of("01678953424"))
        ));

        // Wenn
        PersonDTO personDTO = testSubject.personAendern(friedrichId,marioRequestDTO);

        // Dann
        Integer nachAnderung = testSubject.suche(null,"Mario",null,null,null)
                .size();
        Integer friedrich = testSubject.suche(null,"Friedrich",null,null,null).size();
        assertEquals(1,nachAnderung);
        assertEquals(0,friedrich);



    }

    @Test
    void sollte_EinePersonAdresseAendern_Nach_ID_und_AID() {
        AdresseRequestDTO neueAdresse = new AdresseRequestDTO("Neustr.","55" ,"45986","Duisburg","Nordrhein-Westfalen");
        PersonDTO pdto = testSubject.suche(null,"Fazli",null , null , null).get(0);
        Long pid = pdto.getId();
        Long aid = pdto.getAdresse().get(0).getId();

        PersonDTO neuPDTO =  testSubject.personAdresseAendern(pid,aid,neueAdresse);

        assertNotNull(neuPDTO);
        assertEquals("Neustr.",neuPDTO.getAdresse().get(0).getStrasse());
    }

    @Test
    void sollte_einePersonKontaktAendern_Nach_ID_und_KID() {
        PersonKontaktRequestDTO personKontaktRequestDTO = new PersonKontaktRequestDTO(
                List.of("034626262"),
                List.of("neu@gmail.com"),
                List.of("www.personneuweb.com") ,
                List.of("05647378211")
        );
        PersonDTO pdto = testSubject.suche(null,"Fazli",null , null , null).get(0);
        Long pid = pdto.getId();
        Long kid = pdto.getKontaktList().get(0).getId();

        PersonDTO aktualisiertepdto = testSubject.personKontaktAendern(pid , kid , personKontaktRequestDTO);

        assertNotNull(aktualisiertepdto);
        assertEquals(1,aktualisiertepdto.getKontaktList().size());
        assertEquals("034626262" , aktualisiertepdto.getKontaktList().get(0).getFestnetznummer().get(0));FirmaKontaktRequestDTO firmaKontaktRequestDTO = new FirmaKontaktRequestDTO(
                List.of("034626262"),
                List.of("neu@gmail.com"),
                List.of("www.personneuweb.com") ,
                List.of("05647378211")
        );

    }

    @Test
    void sollte_eine_Personloeschen_Nach_ID(){
        PersonRequestDTO personRequestDTO = PersonRequestDTO.builder()
                .vorname("Anja")
                .nachname("Jäger")
                .adresse(List.of(new AdresseRequestDTO("Markstr.","78","43897","Duisburg","Nordrhein-Westfalen")))
                .kontaktList(List.of()).build();
        PersonDTO anjaDTO = testSubject.einePersonHinzufuegen(personRequestDTO);

        boolean actual = testSubject.loeschen(anjaDTO.getId(),null,null,null,null);

        long anja = testSubject.suche(null,"Anja",null,null,null).size();
        assertTrue(actual);
        assertEquals(0,anja);
    }

    @Test
    void sollte_Eine_PersonLoeschen_Nach_Name() {

        boolean b = testSubject.loeschen(null,"friedrich",null,null,null);

        List<PersonDTO> danach = testSubject.suche(null,"friedrich",null,null,null);
        assertTrue(b);
        assertEquals(0,danach.size());

    }

    @Test
    void sollte_Keine_PersonLoeschen_Nach_Nicht_Existierende_Name(){
        int anzahlDerPersonen = testSubject.suche(null,null,null,null,null).size();
        String nichtExistierendeName = "Jörg";

        boolean actual = testSubject.loeschen(null, nichtExistierendeName,null,null,null);

        int anzahlPersonDANACH = testSubject.suche(null,null,null,null,null).size();
        assertFalse(actual);
        assertEquals(0, anzahlDerPersonen-anzahlPersonDANACH);
    }


    @Test
    void sollte_eine_AdresseEntfernen_Nach_ID_und_AdresseID() {

        PersonDTO maraDTO = testSubject.suche(null,"mara",null,null,null).get(0);
        Long id = maraDTO.getId();
        Long aid = maraDTO.getAdresse().get(0).getId();


        testSubject.adresseEntfernen(id,aid);

        PersonDTO aktualisierteMara = testSubject.suche(id,null,null,null , null).get(0);

        assertEquals(0,aktualisierteMara.getAdresse().size());

    }

    @Test
    void sollte_Adresse_Mit_FalschenID_Nicht_Entfernen() {
        PersonRequestDTO requestDTO1 = new PersonRequestDTO("Person1","Nachname1",
                List.of(new AdresseRequestDTO("Strasse1","33","41782","Berlin","Berlin"))
                ,List.of());
        PersonRequestDTO requestDTO2 = new PersonRequestDTO("Person2","Nachname2",
                List.of(new AdresseRequestDTO("Strasse2","55","41782","München","Bayern"))
                ,List.of());
        PersonDTO personDTO1 = testSubject.einePersonHinzufuegen(requestDTO1);
        PersonDTO personDTO2 = testSubject.einePersonHinzufuegen(requestDTO2);

        // Versucht die Adresse mit ID von dto2 aus Adressen von dto1 löschen
        boolean actual = testSubject.adresseEntfernen(personDTO1.getId(),personDTO2.getAdresse().get(0).getId());

        assertFalse(actual);
        assertEquals(1, personDTO2.getAdresse().size());
        assertEquals(1,personDTO1.getAdresse().size());
        assertEquals("Berlin",personDTO1.getAdresse().get(0).getStadt());
    }

    @Test
    void sollte_AndereAdressen_Nicht_Entfernen(){
        // Wenn eine Adresse aus Person gelöscht wird -> bleibt anderen unverändert
        PersonRequestDTO personDTO = new PersonRequestDTO("Vorname","Nachname",
                List.of(new AdresseRequestDTO("Str1","55","17483","Berlin","Berlin"),
                        new AdresseRequestDTO("Str2","56","31673","München","Bayern"),
                        new AdresseRequestDTO("Str3", "8","35151","Berlin","Berlin")),
                List.of());
        PersonDTO dto1 = testSubject.einePersonHinzufuegen(personDTO);

        boolean actual = testSubject.adresseEntfernen(dto1.getId(),dto1.getAdresse().get(0).getId());

        assertTrue(actual);
        PersonDTO aktualisiertePerson = testSubject.suche(dto1.getId(),null,null,null , null)
                .get(0);
        assertEquals(2,aktualisiertePerson.getAdresse().size());
        boolean adresse2Existis = personDTO.getAdresse().stream().anyMatch(
                ad->ad.getStrasse().equals("Str2"));
        assertTrue(adresse2Existis);
        boolean adresse3Existis = personDTO.getAdresse().stream().anyMatch(
                ad->ad.getStrasse().equals("Str3")
        );
        assertTrue(adresse3Existis);

    }

    @Test
    void sollte_Person_Nicht_Entfernen() {
        // Wenn einzige Adresse einer Person gelöscht wird -> Person sollte nicht gelöscht werden
        PersonRequestDTO requestDTO = new PersonRequestDTO("Person1","Person1Nachname",
                List.of(new AdresseRequestDTO("Str1","9","48123","Berlin","Berlin"))
        ,List.of());
        PersonDTO personDTO = testSubject.einePersonHinzufuegen(requestDTO);

        boolean b = testSubject.adresseEntfernen(personDTO.getId(),personDTO.getAdresse().get(0).getId());
        PersonDTO actualPerson = testSubject.suche(personDTO.getId(),null,null,null,null).get(0);

        assertTrue(b);
        assertNotNull(actualPerson);
        assertEquals(0, actualPerson.getAdresse().size());
        assertEquals("Person1", actualPerson.getVorname());
    }



    @Test
    void sollte_KontaktEntfernen() {

        PersonDTO friedrich = testSubject.suche(null,"friedrich",null,null,null).get(0);
        Long id = friedrich.getId();
        Long kid = friedrich.getKontaktList().get(0).getId();

        testSubject.kontaktEntfernen( id , kid );

        PersonDTO aktualisierteFriedrich = testSubject.suche(null,"friedrich",null,null,null).get(0);
        assertEquals(0,aktualisierteFriedrich.getKontaktList().size());
    }

    @Test
    void sollte_Kontakt_MitFalschenID_Nicht_Entfernen(){
        PersonRequestDTO requestDTO = new PersonRequestDTO("Person1","PersonNachname",
                List.of(),List.of(
                        new PersonKontaktRequestDTO(List.of("01735156272"),List.of("person@email.com"),
                                List.of("www.person.com"),List.of("01752341789"))));
        PersonDTO personDTO = testSubject.einePersonHinzufuegen(requestDTO);
        PersonKontaktDTO friedrichKontakt = testSubject.suche(null,"Friedrich",null,null,null)
                .get(0).getKontaktList().get(0);

        boolean b = testSubject.kontaktEntfernen(personDTO.getId() , friedrichKontakt.getId());
        PersonDTO actualPerson = testSubject.suche(personDTO.getId(),null,null,null,null).get(0);
        PersonDTO actualFriedrich = testSubject.suche(null,"Friedrich",null,null,null)
                .get(0);


        assertFalse(b);
        assertNotNull(actualPerson);
        assertNotNull(actualFriedrich);
        assertEquals(1,actualPerson.getKontaktList().size());
        assertEquals(1,actualFriedrich.getKontaktList().size());
        assertEquals("01735156272",actualPerson.getKontaktList().get(0).getFestnetznummer().get(0));
    }

    @Test
    void sollte_Anderen_Kontakten_Unveraendert_Bleiben(){
        // Wenn eine Person gelöscht wird, muss die anderen Kontakten unverändert bleiben
        PersonRequestDTO requestDTO = new PersonRequestDTO("PersonVorname","PersonNachname", List.of(),
                List.of(new PersonKontaktRequestDTO(List.of("015252521"),List.of("person@email.com"),
                        List.of("www.person.com"),List.of("01752341789")),
                        new PersonKontaktRequestDTO(List.of("0162626272"),List.of("person@gmail.com"),
                                List.of("www.person2.com"),List.of())));
        PersonDTO personDTO = testSubject.einePersonHinzufuegen(requestDTO);

        //Lösche erste PersonKontakt
        boolean actual = testSubject.kontaktEntfernen(personDTO.getId(),personDTO.getKontaktList().get(0).getId());
        PersonDTO actualPerson = testSubject.suche(personDTO.getId(),null,null,null,null).get(0);

        assertTrue(actual);
        assertNotNull(actualPerson);
        assertEquals(1, actualPerson.getKontaktList().size());
        assertEquals("0162626272",actualPerson.getKontaktList().get(0).getFestnetznummer().get(0));
    }




}