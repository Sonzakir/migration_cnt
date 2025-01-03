package com.fazli;

import com.fazli.aspect.LogEntryExit;
import com.fazli.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepo repository;

    private final EntityManager entityManager;

    private final AdresseMapper adresseMapper = new AdresseMapper();
    private final PersonKontaktDTOMapper personKontaktDTOMapper = new PersonKontaktDTOMapper();
    private final PersonDTOMapper personDTOMapper = new PersonDTOMapper(adresseMapper, personKontaktDTOMapper);

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    public PersonService(PersonRepo repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }




//                              Hinzufuegen
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO einePersonHinzufuegen(PersonRequestDTO personDTO) {
        Person person = personDTOMapper.ReqtoPerson(personDTO);
        logger.info("Die Person RequestDTO  {} wird auf Person gemapped {}" ,personDTO, person);
        Person fin  = repository.save(person);
        logger.info("Die Person {} wurde gespeichert {} ", fin);
        return personDTOMapper.toDTO(fin);
    }

    // fügt Person mit id eine Adresse hinzu
    @Transactional
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO neueAdresseHinzufuegen(Long id , AdresseRequestDTO adresseDTO){
        Adresse adresse = adresseMapper.toAdresse(adresseDTO);
        logger.info("Die Adresse RequestDTO {} wird auf Adresse gemapped {}" ,adresseDTO, adresse);
        Person person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Adresse> mutableAdresse = new ArrayList<>(person.getAdresse());
        logger.info("Die Adresse der Person {} VOR dem Hinzufügen {} ", person , mutableAdresse);
        mutableAdresse.add(adresse);
        person.setAdresse(mutableAdresse);
        logger.info("Die Adresse der Person {} NACH dem Hinzufügen {} ", person , person.getAdresse());


        entityManager.persist(person);
        return personDTOMapper.toDTO(person);

    }

    // fügt Person mit der id eine neue Kontakt hinzu
    @Transactional
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO neueKontaktHinzufuegen(Long id, PersonKontaktRequestDTO personKontaktDTO){
        PersonKontakt kontakt = personKontaktDTOMapper.toPersonKontakt(personKontaktDTO);
        logger.info("Die Kontakt RequestDTO {} wird auf Kontakt {} gemapped " , personKontaktDTO , kontakt);

        Person person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<PersonKontakt> mutablePersonKontakt = new ArrayList<>(person.getKontaktList());
        logger.info("Die Kontakten der Person {} VOR dem Hinzufügen {} ", person , mutablePersonKontakt);

        mutablePersonKontakt.add(kontakt);
        person.setKontaktList(mutablePersonKontakt);
        logger.info("Die Kontakten der Person {} NACH dem Hinzufügen {} ", person , person.getKontaktList());

        entityManager.persist(person);
        return personDTOMapper.toDTO(person);
    }

    public List<Person> saveall(List<Person>personList){
        return repository.saveAll(personList);
    }





    //                      Suchmethoden
    // generische suche
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = false, dauer = ChronoUnit.MILLIS)
    public List<PersonDTO> suche(Long id , String vorname, String nachname,
                                 String stadt, String bundesland){
        List<Person> lst;
        if(id!=null){
            logger.info("Die Person mit der ID = {} wird gesucht ", id);
            Person p = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            lst = new ArrayList<>(List.of(p));
        }else if(vorname==null && nachname==null && stadt==null && bundesland==null){
            logger.warn("Kein Suchparameter gegeben. Alle Personen wird zurückgegeben");
            lst = repository.findAll();
        }else{
            logger.warn("Kein Person ID gegeben. Möglicherweise mehrere Person wird zurückgegeben");
            logger.info("Die Person suche mit folgender Schlüsseln wird ausgeführt vorname = {} , nachname = {} , stadt = {} , bundesland = {}" ,
                    vorname, nachname, stadt, bundesland);
            lst = repository.suche(vorname, nachname, stadt, bundesland);
        }

        return lst.stream()
                .map(personDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    // liefert adresse [opt] id
    // http://localhost:8080/person/adresse ?id=302
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public List<AdresseDTO> adresseLiefern(Long id){
        logger.warn("Wenn keine ID angegeben wird, wird alle gespeichertem Adressen zurückgegeben");
        logger.info("Die Adressen der Person mit ID = {} wird zurückgegeben.", id);
        return repository.searchAdresse(id).stream().map(adresseMapper::toDTO).collect(Collectors.toList());
    }






    //                         Aktualisieren



    // A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: com.fazli.Telefonbuch_Spring.model.Person.adresse
    //  ändert eine Person mit der ID
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personAendern(Long id , PersonRequestDTO personDTO){
        Person person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Person neu = personDTOMapper.ReqtoPerson(personDTO);
        logger.info("Die Person RequestDTO {} wird auf Firma {} gemapped" , personDTO , neu);



        String vorname = neu.getVorname();
        String nachname = neu.getNachname();
        List<PersonKontakt> kontakten = new ArrayList<>(neu.getKontaktList());
        List<Adresse> adresses = new ArrayList<>(neu.getAdresse());


        person.setVorname(vorname);
        person.setNachname(nachname);
        person.setAdresse(adresses);
        person.setKontaktList(kontakten);

        entityManager.persist(person);

        logger.info("Die Person Vorname wurde auf {} geändert" ,vorname);
        logger.info("Die Person Nachname wurde auf {} geändert" ,nachname);
        logger.info("Die Person Kontakt wurde auf {} geändert", kontakten);
        logger.info("Die Person Adresse wurde auf {} geändert" ,adresses);


        return personDTOMapper.toDTO(person);

    }

    // ändert die Adresse mit aid von Person mit pid
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personAdresseAendern(Long pid , Long aid , AdresseRequestDTO adresseDTO){
        Person person = repository.findById(pid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Adresse adresse= adresseMapper.toAdresse(adresseDTO);
        logger.info("Die Adresse RequestDTO {} wird auf Adresse {} gemapped", adresseDTO , adresse);


        List<Adresse> lst =person.getAdresse().stream()
                .filter(adresse1 -> !adresse1.getId().equals(aid))
                .collect(Collectors.toCollection(ArrayList::new));
        logger.info("Die Person hat vor der Änderung folgender Adresse/n {} .", lst);

        lst.add(adresse);
        logger.info("Die Person hat vor der Änderung folgender Adresse/n {} .", lst);

        person.setAdresse(lst);


        entityManager.persist(person);
        return personDTOMapper.toDTO(person);
    }

    // ändert die Kontakt mit kid von Person mit id
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personKontaktAendern(Long id, Long kid, PersonKontaktRequestDTO personKontaktDTO){
        Person person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PersonKontakt kontakt = personKontaktDTOMapper.toPersonKontakt(personKontaktDTO);
        logger.info("Die Person KontaktRequestDTO {} wird auf Firma {} gemapped. ", personKontaktDTO , kontakt);

        List<PersonKontakt> lst = person.getKontaktList().stream()
                .filter(kntkt->!kntkt.getId().equals(kid))
                .collect(Collectors.toCollection(ArrayList::new));
        lst.add(kontakt);
        logger.info("Die Person Kontakten wurde aktualisiert {} ", lst);
        person.setKontaktList(lst);

        entityManager.persist(person);
        return personDTOMapper.toDTO(person);
    }


    //                        Löschmethoden



    // generisches löschen
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean loeschen(Long id , String vorname, String nachname,
                            String stadt, String bundesland){
        if(id!=null){
            if (repository.existsById(id)){
                repository.deleteById(id);
                logger.info("Die Person mit ID = {} wurde gelöscht" , id);
                return true;
            }
            return false;
        }
        // Loesche Person nach Name, Stadt, Bundesland
        var size = repository.count();
        List<Person> lst = repository.suche(vorname, nachname, stadt, bundesland);
        logger.warn("Kein ID wurde angegeben. Möglicherweise mehrere Person wird gelöscht");
        repository.deleteAll(lst);
        return repository.suche(null,null,null,null).size() < size;
    }


    // löscht die Adresse mit aid von Person mit pid
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean adresseEntfernen(Long pid, Long aid){
        Person p = repository.findById(pid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        var size = p.getAdresse().size();
        logger.info("Die Adresse mit ID = {} von der Person mit ID = {} wird gelöscht", aid,pid);
        List<Adresse> newAdresses = p.getAdresse().stream()
                .filter(a -> !a.getId().equals(aid))
                .collect( Collectors.toCollection(ArrayList::new));
        p.setAdresse(newAdresses);



        entityManager.persist(p);
        return p.getAdresse().size()== (size-1);
    }

    //  löscht die Kontakt mit kid von Person mit pid
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean kontaktEntfernen(Long pid, Long kid){
        Person p = repository.findById(pid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        var size = p.getKontaktList().size();
        logger.info("Die Kontakt mit ID= {} von der Person mit ID = {} wird gelöscht",kid,pid);
        List<PersonKontakt> newList = p.getKontaktList().stream()
                .filter(pkontakt-> !pkontakt.getId().equals(kid))
                .collect(Collectors.toCollection(ArrayList::new));
        p.setKontaktList(newList);


        entityManager.persist(p);
        return p.getKontaktList().size()== (size-1);
    }







    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void loeshcePersonNachID(Long id) {
        Person p = repository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(p);
    }





    // eine Vorname loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void vornameLoeschen(Long id ,  String vorname){
        Person p = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        p.setVorname(vorname);
        entityManager.merge(p);
    }

    // eine Nachname loeachen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void nachnameLoeschen(Long id ,  String nachname){
        Person p=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        p.setNachname(nachname);
        entityManager.merge(p);
    }

    // eine mobilnummer loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void mobilNoLoeschen(Long id , String mobilnummer){
        Person p = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<PersonKontakt> lst = p.getKontaktList();
        for(PersonKontakt pk : lst){
            List<String> ml =pk.getMobilNummern();
            ml.removeIf(n->n.equals(mobilnummer));
            pk.setMobilNummern(ml);
        }
        p.setKontaktList(lst);
        Person persisted = entityManager.merge(p);


    }

    // eine festneznummerLoeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void festnetzNoLoeschen(Long id , String festnetz){
        Person p = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<PersonKontakt> lst = p.getKontaktList();
        for(PersonKontakt pk : lst){
            List<String> festnetznummerList = pk.getFestnetzNummer();
            festnetznummerList.removeIf(n->n.equals(festnetz));
            pk.setFestnetzNummer(festnetznummerList);
        }
        p.setKontaktList(lst);
        entityManager.merge(p);
    }

    // eine email loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void emailLoeschen(Long id , String email){
        Person p = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<PersonKontakt> lst = p.getKontaktList();
        for(PersonKontakt pk : lst){
            List<String> emailList = pk.getEmail();
            emailList.removeIf(n->n.equals(email));
            pk.setEmail(emailList);
        }
        p.setKontaktList(lst);
        entityManager.merge(p);
    }

    //eine webseite loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void webseiteLoeschen(Long id , String webseite){
        Person p = repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<PersonKontakt> lst = p.getKontaktList();
        for(PersonKontakt pk : lst){
            List<String> webseiteList = pk.getWebseite();
            webseiteList.removeIf(n->n.equals(webseite));
            pk.setWebseite(webseiteList);
        }
        p.setKontaktList(lst);
        entityManager.merge(p);
    }

    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public Long anzahlDerPersonen(){
        return repository.count();
    }








}
