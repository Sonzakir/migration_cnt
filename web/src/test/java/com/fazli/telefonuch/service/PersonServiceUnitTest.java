package com.fazli.telefonuch.service;


import com.fazli.*;
import com.fazli.dto.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceUnitTest {

    private PersonService testSubject;

    private PersonRepo personRepoMock;
    private EntityManager emMock;

    @BeforeEach
    void setUp() {
        personRepoMock = Mockito.mock(PersonRepo.class);
        emMock = Mockito.mock(EntityManager.class);
        testSubject = new PersonService(personRepoMock, emMock);
    }

    @Test
    void foo() {
        Adresse adresse1 = new Adresse();
        adresse1.setId(999L);
        Adresse adresse2 = new Adresse();
        adresse2.setId(100L);



        List<Adresse> adresses = List.of(adresse1, adresse2);






        Person p = new Person();
        p.setId(500L);
        p.setAdresse(adresses);

        Mockito.when(personRepoMock.findById(p.getId())).thenReturn(Optional.of(p));
        Mockito.doNothing().when(emMock).persist(p);

        testSubject.adresseEntfernen(p.getId(), adresse1.getId());

        assertEquals(p.getAdresse().size(), 1);
        assertEquals(p.getAdresse().get(0).getId(), 100L);
    }

    @Test
    void test_PersonHinzufuegen(){

        // Arrange
        PersonRequestDTO personRequestDTO = PersonRequestDTO.builder()
                .vorname("Sebastian")
                .nachname("Holler")
                .adresse(List.of(new AdresseRequestDTO("Bspstr.","55","44555","Berlin","Berlin")))
                .kontaktList(List.of(new PersonKontaktRequestDTO( List.of("023465757"), List.of("sebastion@email.com") ,
                                List.of("sebastianholler.com") ,  List.of("0237373737"))))
                .build();
        // Da RequestDTO kein ID hat, wird hier als null implementieren , danach implementieren wir ID händisch
        Person person = Person.builder()
                .vorname("Sebastian")
                .nachname("Holler")
                .adresse(List.of(new Adresse(null,"Bspstr.","55","44555","Berlin","Berlin")))
                .kontaktList(List.of(new PersonKontakt(null, List.of("023465757"), List.of("sebastion@email.com") ,
                                List.of("sebastianholler.com") ,  List.of("0237373737"))))
                .build();

        Person fin = Person.builder()
                .id(2L)
                .vorname("Sebastian")
                .nachname("Holler")
                .adresse(List.of(new Adresse(2L,"Bspstr.","55","44555","Berlin","Berlin")))
                .kontaktList(List.of(new PersonKontakt(5L, List.of("023465757"), List.of("sebastion@email.com") ,
                                List.of("sebastianholler.com") ,  List.of("0237373737"))))
                .build();

        Mockito.when(personRepoMock.save(person)).thenReturn(fin);

        // Act
        PersonDTO personDTO = this.testSubject.einePersonHinzufuegen(personRequestDTO);
        // Assert
        assertEquals(2L, personDTO.getId());
        assertEquals("Sebastian", personDTO.getVorname());
        assertEquals("Holler", personDTO.getNachname());
        assertEquals(fin.getAdresse().get(0).getStrasse() ,
                personDTO.getAdresse().get(0).getStrasse());

    }

    @Test
    void test_Neue_Adresse_Hinzufuegen(){
        // Arrange
        AdresseRequestDTO adresseRequestDTO = AdresseRequestDTO.builder()
                .strasse("Beispielstr.")
                .hausNo("55")
                .plz("44567")
                .stadt("Dortmund")
                .bundesland("Nordrhein-Westfalen")
                .build();
        Person person = Person.builder()
                .id(15L)
                .vorname("Maximilian")
                .nachname("Müller")
                .adresse(List.of(new Adresse("Hauptstr.","22","33445","Berlin","Berlin")))
                .kontaktList(List.of())
                .build();

        Mockito.when(personRepoMock.findById(person.getId())).thenReturn(Optional.of(person));
        Mockito.doNothing().when(emMock).persist(person);

        // Act
        PersonDTO output = testSubject.neueAdresseHinzufuegen(person.getId() , adresseRequestDTO);

        // Assert
        assertEquals(2, output.getAdresse().size());
    }


    @Test
    void test_Neue_Kontakt_Hinzufuegen(){
        PersonKontaktRequestDTO kontaktRequestDTO = PersonKontaktRequestDTO.builder()
                .festnetznummer(List.of("02763455675"))
                .email(List.of("person1@email.com"))
                .webseite(List.of("person1.com"))
                .mobilnummern(List.of("04535678943"))
                .build();
        // PersonKontakt output
        PersonKontakt kontakt = PersonKontakt.builder()
                .id(143L)
                .festnetznummer(List.of("02763455675"))
                .email(List.of("person1@email.com"))
                .webseite(List.of("person1.com"))
                .mobilNummern(List.of("04535678943"))
                .build();
        Person person = Person.builder()
                .id(12L)
                .vorname("Person1")
                .nachname("PersonNachname")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();

        Mockito.when(personRepoMock.findById(person.getId())).thenReturn(Optional.of(person));
        Mockito.doNothing().when(emMock).persist(person);

        PersonDTO output = testSubject.neueKontaktHinzufuegen(person.getId(), kontaktRequestDTO);

        assertEquals(1 , output.getKontaktList().size());
        assertEquals("02763455675", output.getKontaktList().get(0).getFestnetznummer().get(0));
    }

    @Test
    void test_Suche_Nach_ID(){
        Person person = Person.builder()
                .id(2L)
                .vorname("Friedrich")
                .nachname("Schmier")
                .adresse(List.of(new Adresse(2L,"Beispielstr.","55","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("friedrich@gmail.com"),
                        List.of("friedrich.com"),List.of())))
                .build();
        Mockito.when(personRepoMock.findById(person.getId())).thenReturn(Optional.of(person));
        //Mockito.doNothing().when(personRepoMock.deleteById(person.getId()));

        List<PersonDTO> output = testSubject.suche(person.getId(), null,null,null,null);

        assertEquals(1,output.size());
        assertEquals("Friedrich",output.get(0).getVorname());
        assertEquals("Schmier",output.get(0).getNachname());
    }

    @Test
    void test_Suche_Nach_Name(){
        Person person = Person.builder()
                .id(2L)
                .vorname("Friedrich")
                .nachname("Schmier")
                .adresse(List.of(new Adresse(2L,"Beispielstr.","55","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("friedrich@gmail.com"),
                        List.of("friedrich.com"),List.of())))
                .build();
        Mockito.when(personRepoMock.suche("Friedrich","Schmier", null,null))
                .thenReturn(List.of(person));

        List<PersonDTO> actual = testSubject.suche(null,"Friedrich","Schmier",null,null);

        assertEquals(1,actual.size());
        assertEquals("Friedrich",actual.get(0).getVorname());
    }

    @Test
    void test_Suche_Ohne_Parametern(){
        Person person = Person.builder()
                .id(2L)
                .vorname("Friedrich")
                .nachname("Schmier")
                .adresse(List.of(new Adresse(2L,"Beispielstr.","55","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("friedrich@gmail.com"),
                        List.of("friedrich.com"),List.of())))
                .build();
        Person person2 = Person.builder()
                .id(12L)
                .vorname("Person1")
                .nachname("PersonNachname")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();

        Mockito.when(personRepoMock.findAll()).thenReturn(List.of(person,person2));

        List<PersonDTO> actual = testSubject.suche(null,null,null,null,null);

        assertEquals(2,actual.size());

    }

    @Test
    void test_Suche_Mit_Leere_Repo(){
        Mockito.when(personRepoMock.findAll()).thenReturn(List.of());

        List<PersonDTO> actual = testSubject.suche(null,null,null,null,null);

        assertEquals(0,actual.size());
    }


    @Test
    void test_AdresseLiefern(){
        Person person = Person.builder()
                .id(2L)
                .vorname("Friedrich")
                .nachname("Schmier")
                .adresse(List.of(new Adresse(2L,"Beispielstr.","55","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("friedrich@gmail.com"),
                        List.of("friedrich.com"),List.of())))
                .build();
        Mockito.when(personRepoMock.searchAdresse(2L)).thenReturn(List.of(person.getAdresse().get(0)));

        List<AdresseDTO> output = testSubject.adresseLiefern(person.getId());
        assertEquals(1, output.size());
        assertEquals("Beispielstr." ,  output.get(0).getStrasse());
    }

    @Test
    void test_AdresseLiefern_Mit_LeerenAdresseDaten(){
        Person person = Person.builder()
                .id(2L)
                .vorname("Friedrich")
                .nachname("Schmier")
                .adresse(List.of())
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("friedrich@gmail.com"),
                        List.of("friedrich.com"),List.of())))
                .build();

        Mockito.when(personRepoMock.searchAdresse(2L)).thenReturn(List.of());

        List<AdresseDTO> actual = testSubject.adresseLiefern(person.getId());

        assertNotNull(actual);
        assertEquals(0,actual.size());
    }

    @Test
    void test_Adresse_Liefern_Mit_MehrereAdresseDaten(){
        Person person = new Person();
        person.setId(2L);
        Adresse adresse1 = new Adresse("Str1","1","44556","Berlin","Berlin");
        Adresse adresse2 = new Adresse("Str2","2","44336","München","Bayern");
        person.setAdresse(List.of(adresse1,adresse2));

        Mockito.when(personRepoMock.searchAdresse(2L)).thenReturn(List.of(adresse1,adresse2));

        List<AdresseDTO> actual = testSubject.adresseLiefern(person.getId());

        assertNotNull(actual);
        assertEquals(2,actual.size());
        assertEquals("Str1", actual.get(0).getStrasse());
    }


    // Aktualisieren

    @Test
    void test_PersonAendern(){
        Person person = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();

        PersonRequestDTO neuePerson = PersonRequestDTO.builder()
                .vorname("Jakob")
                .nachname("Schmalstieg")
                .adresse(List.of(new AdresseRequestDTO("Prinz-Regentstr.","100","44673","Bochum",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontaktRequestDTO(List.of("034526346326"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();

        Mockito.when(personRepoMock.findById(133L)).thenReturn(Optional.of(person));

        PersonDTO output = testSubject.personAendern(133L,neuePerson);

        assertEquals("Jakob" , output.getVorname());
        assertEquals("Schmalstieg" , output.getNachname());
        assertEquals("Prinz-Regentstr.",output.getAdresse().get(0).getStrasse());
        assertEquals("034526346326" , output.getKontaktList().get(0).getFestnetznummer().get(0));
        assertEquals(1, output.getKontaktList().size());
        assertEquals(1, output.getAdresse().size());

    }

    @Test
    void test_PersonAendern_Mit_LeerenPersonDaten(){
        Person person = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();

        PersonRequestDTO neuePerson = PersonRequestDTO.builder()
                .vorname("")
                .nachname("")
                .adresse(List.of())
                .kontaktList(List.of())
                .build();
        Mockito.when(personRepoMock.findById(133L)).thenReturn(Optional.of(person));


        PersonDTO output = testSubject.personAendern(133L,neuePerson);

      assertNotNull(output);
      assertNotEquals(output, person);
    }

    @Test
    void test_Person_Adresse_Aendern(){
        Person person = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();
        AdresseRequestDTO adresseRequestDTO = AdresseRequestDTO.builder()
                .strasse("Neustr.")
                .hausNo("45")
                .plz("45342")
                .stadt("Berlin")
                .bundesland("Berlin")
                .build();
        Mockito.when(personRepoMock.findById(133L)).thenReturn(Optional.of(person));

        PersonDTO output = testSubject.personAdresseAendern(133L,2L,adresseRequestDTO);

        assertEquals(1, output.getAdresse().size());
        assertEquals("Neustr.", output.getAdresse().get(0).getStrasse());

    }

    @Test
    void test_PersonKontakt_Aendern(){
        Person person = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();

        PersonKontaktRequestDTO requestDTO = PersonKontaktRequestDTO.builder()
                .festnetznummer(List.of("0345252522"))
                .email(List.of("person@email.com"))
                .webseite(List.of("person.com"))
                .mobilnummern(List.of("034236423"))
                .build();
        Mockito.when(personRepoMock.findById(133L)).thenReturn(Optional.of(person));

        PersonDTO output = testSubject.personKontaktAendern(133L , 16L, requestDTO);

        assertEquals(1 ,output.getKontaktList().size());
        assertEquals("0345252522", output.getKontaktList().get(0).getFestnetznummer().get(0));
    }

    @Test
    void test_Person_Loeschen_Nach_ID(){
        Person person = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                        List.of(),List.of("01828383920"))))
                .build();
        Mockito.when(personRepoMock.existsById(133L)).thenReturn(true);

        boolean actual = testSubject.loeschen(133L,null,null,null,null);

        assertTrue(actual);
    }

    @Test
    void test_Person_Loeschen_Nach_Vorname() {
        Person mara = Person.builder()
                .id(133L)
                .vorname("Mara")
                .nachname("Günther")
                .adresse(List.of(new Adresse(2L, "Hellweg.", "77", "47321", "Dortmund",
                        "Nordrhein-Westfallen")))
                .kontaktList(List.of(new PersonKontakt(16L, List.of("0453635342"), List.of("günthermara@gmail.com"),
                        List.of(), List.of("01828383920"))))
                .build();
        Mockito.when(personRepoMock.count()).thenReturn(5L);
        Mockito.when(personRepoMock.suche("Mara",null,null,null))
                .thenReturn(List.of(mara));
        Mockito.when(personRepoMock.suche(null,null,null,null))
                .thenReturn(List.of(new Person(),new Person(),new Person(),new Person()));

        boolean actual = testSubject.loeschen(null, "Mara",null,null,null);

        assertTrue(actual);
    }

    @Test
    void test_Adresse_Entfernen(){
        Adresse ad1 = new Adresse(2L,"Hellweg.","77","47321","Dortmund", "Nordrhein-Westfallen");
        Adresse ad2 = new Adresse(3L,"Prinz-Regentstr.","100","44673","Bochum", "Nordrhein-Westfallen");
        List<Adresse> adresses = List.of(ad1,ad2);

        Person p = new Person();
        p.setId(133L);
        p.setAdresse(adresses);

        Mockito.when(personRepoMock.findById(133L)).thenReturn(Optional.of(p));
        Mockito.doNothing().when(emMock).persist(p);

        Boolean b = testSubject.adresseEntfernen(133L , 3L);

        assertTrue(b);
        assertEquals(1 , p.getAdresse().size());
        assertEquals(false , p.getAdresse().get(0).getStrasse().equals("Prinz-Regentstr."));
    }



    @Test
    void test_Kontakt_Entfernen(){
        PersonKontakt p1 = new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
                List.of(),List.of("01828383920"));
        PersonKontakt p2 = new PersonKontakt(19L ,List.of("05432652762"),List.of("güntherbackup@hotmail.com"),
                List.of("günther.blogspot.com"),List.of());
        List<PersonKontakt> kontaktList = List.of(p1,p2);

        Person gunther = new Person();
        gunther.setId(33L);
        gunther.setKontaktList(kontaktList);

        Mockito.when(personRepoMock.findById(gunther.getId())).thenReturn(Optional.of(gunther));
        Mockito.doNothing().when(emMock).persist(gunther);

        Boolean output = testSubject.kontaktEntfernen(33L , 16L);

        assertEquals(true, output);
        assertEquals(1 , gunther.getKontaktList().size());
        assertEquals("05432652762", gunther.getKontaktList().get(0).getFestnetzNummer().get(0));

    }

    /**
     *
     @Test
     void test_Person_Loeschen(){
     Person person = Person.builder()
     .id(133L)
     .name(List.of("Mara"))
     .nachname(List.of("Günther"))
     .adresse(List.of(new Adresse(2L,"Hellweg.","77","47321","Dortmund",
     "Nordrhein-Westfallen")))
     .kontaktList(List.of(new PersonKontakt(16L ,List.of("0453635342"),List.of("günthermara@gmail.com"),
     List.of(),List.of("01828383920"))))
     .build();
     Mockito.when(personRepoMock.suche(null,"Mara",null,null,null))
     .thenReturn(List.of(person));
     Mockito.when(personRepoMock.suche(null,null,null,null,null))
     .thenReturn(List.of(new Person(),new Person()));

     Boolean b = testSubject.loeschen(null, "Mara",null, null,null);

     assertTrue(b);

     }
     */
}