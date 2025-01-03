package com.fazli;


import com.fazli.aspect.LogEntryExit;
import com.fazli.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/person")
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService service;

    public PersonController(PersonService service)
    {
        this.service = service;

    }



    //                                      Hinzufuegen

    // Eine Person Hinzufügen
    @PostMapping("")
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO einePersonHinzufuegen(@RequestBody PersonRequestDTO person){
        logger.info("Person {} wird hinzugefügt" , person);
        return service.einePersonHinzufuegen(person);
    }

    // fügt Person mit id eine Adresse hinzu
    // neue Adresse Hinzufuegen    -http://localhost:8080/person/254/adresse
    @PostMapping("/{id}/adresse")
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO neueAdressHinzufuegen(@PathVariable Long id , @RequestBody AdresseRequestDTO adresseDTO){
        logger.info("Füge neue adresse für person {} hinzu: {}", id, adresseDTO);
        return service.neueAdresseHinzufuegen(id,adresseDTO);
    }

    // fügt Person mit id eine Kontakte hinzu
    // neue Kontakthinzufuegen
    @PostMapping("/{id}/kontakt")
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO neueKontaktHinzufuegen(@PathVariable Long id , @RequestBody PersonKontaktRequestDTO personKontaktDTO){
        logger.info("Der Kontakt {} wird für die Person mit ID = {} hinzugefügt.",personKontaktDTO,id);
        return service.neueKontaktHinzufuegen(id,personKontaktDTO);
    }


    // Liste von Personen hinzufuegen
    @PostMapping("/personen/hinzufuegen")
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public List<Person> listPersonHinzufuegen(@RequestBody List<Person> personList){
        logger.info("Eine Liste von Personen wird hinzugefügt.");
        return service.saveall(personList);
    }


    //                              Suchmethoden


    // generische Suche
    @GetMapping("")
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = false, dauer = ChronoUnit.MILLIS)
    public List<PersonDTO> suche(
            @RequestParam(required = false) Long id ,
            @RequestParam(required = false) String vorname,
            @RequestParam(required = false) String nachname ,
            @RequestParam(required = false) String stadt,
            @RequestParam(required = false) String bundesland
    ){
        logger.info("Personensuche wird ausgeführt id = {} , vorname = {} , nachname = {} , " +
                        "stadt = {} , bundesland = {}" ,
                id,vorname,nachname,stadt,bundesland);
        return service.suche(id,vorname,nachname,stadt,bundesland);
    }

    // liefert alle adressen

    // liefert alle Adressen für Person mit id = id
    @GetMapping("/adresse")
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public List<AdresseDTO> adresseLiefern(@RequestParam(required = false) Long id){
        logger.info("Adressen von der Person mit ID = {} wird geliefert." , id);
        if(id==null){
            logger.warn("Es wurde keine ID angegeben, alle Adressen wird zurückgegeben ");
        }
        return service.adresseLiefern(id);
    }











    //                          Aktualisieren

    // ändert eine Person mit der ID
    @PutMapping("/{id}")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personAendern(@PathVariable Long id , @RequestBody PersonRequestDTO personDTO){
        logger.info("Person mit ID = {} wird durch {} ersetzt." ,id, personDTO);
        return service.personAendern(id,personDTO);
    }

    // ändert die Adresse mit id={aid} von Person mit id={id}
    @PutMapping("/{pid}/adresse/{aid}")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personAdresseAendern(@PathVariable Long pid , @PathVariable Long aid ,
                                          @RequestBody AdresseRequestDTO adresseDTO){
        logger.info("Die Adresse mit ID = {} von der Person ID = {} wird durch {} ersetzt.",
                aid ,pid , adresseDTO);
        return service.personAdresseAendern(pid,aid,adresseDTO);
    }

    //  ändert die Personkontakt mit id={kid} von Person mit id={id}
    @PutMapping("/{id}/kontakt/{kid}")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public PersonDTO personKontaktAendern(@PathVariable Long id, @PathVariable Long kid ,
                                          @RequestBody PersonKontaktRequestDTO personKontaktDTO){
        logger.info("Der Kontakt mit ID = {} von der Firma mit ID = {} wird durch {} ersetzt." ,
                kid ,id , personKontaktDTO);
        return service.personKontaktAendern(id,kid,personKontaktDTO);
    }

    //                          Löschmethoden

    // generisches löschen
    @DeleteMapping("")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean loeschen ( @RequestParam(required = false) Long id ,
                              @RequestParam(required = false) String vorname,
                              @RequestParam(required = false) String nachname ,
                              @RequestParam(required = false) String stadt,
                              @RequestParam(required = false) String bundesland){
        if(id!=null){
            logger.warn("Person mit ID = {} wird entfernt." , id);
        } else if (stadt!=null || bundesland!=null) {
            logger.warn("Mehrere Personen mit Stadt = {} und Bundesland = {} , vorname = {} , nachname = {} wird gelöscht.", stadt, bundesland,vorname,nachname);
        }
        else {
            logger.warn("ALLE Personen wird gelöscht.");
        }

        return service.loeschen(id,vorname,nachname,stadt,bundesland);
    }

    // löscht die Adresse mit aid von Person mit pid
    @DeleteMapping("/{pid}/adresse/{aid}")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean adresseEntfernen(@PathVariable Long pid,
                                    @PathVariable Long aid){
        logger.warn("Die Adresse mit ID = {} von der Person mit ID = {} wird gelöscht", aid, pid);

        return service.adresseEntfernen(pid,aid);
    }

    //  löscht die Kontakt mit kid von Person mit pid
    @DeleteMapping("/{pid}/kontakt/{kid}")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean kontaktEntfernen(@PathVariable Long pid,
                                    @PathVariable Long kid){
        logger.warn("Die Kontakt mit ID = {} von der Person mit ID = {} wird gelöscht. ",kid, pid);

        return service.kontaktEntfernen(pid,kid);
    }







    //Person eine Vorname Loeschen
    //{"name": "Soner"}
    @DeleteMapping("/delete/{id}/vorname")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteVorname(@PathVariable Long id, @RequestBody String vorname) {
        logger.warn("Vorname {} von der Person mit ID = {} wird gelöscht.", vorname, id);
        service.vornameLoeschen(id, vorname);
    }
    //Person Nachname Loeschen
    //{"nachname":"mayer"}
    @DeleteMapping("/delete/{id}/nachname")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteNachname(@PathVariable Long id, @RequestBody String nachname) {
        logger.warn("Nachanme {} von der Person mit ID = {} wird gelöscht.", nachname, id);
        service.nachnameLoeschen(id, nachname);
    }

    //Person Telefonnummer Loeschen
    //{"nummer": "0858488329"}
    @DeleteMapping("/delete/{id}/mobilNo")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteMobilNo(@PathVariable Long id, @RequestBody String mobilnummer) {
        logger.warn("Mobilnummer {} von der Person mit ID = {} wird gelöscht.", mobilnummer, id);
        service.mobilNoLoeschen(id, mobilnummer);
    }
    //Person Festnetznummer Loeschen
    //{"festnetznummer": "047475757"}
    @DeleteMapping("/delete/{id}/festnetzNo")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteFestnetzNo(@PathVariable Long id , @RequestBody String festnetznummer) {
        logger.warn("Festnetznummer {} von der Person mit ID = {} wird gelöscht.", festnetznummer, id);
        service.festnetzNoLoeschen(id, festnetznummer);
    }

    //Person Email loeschen
    //{"email":"deletethis@gmail.com"}
    @DeleteMapping("/delete/{id}/email")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteEmail(@PathVariable Long id, @RequestBody String email) {
        logger.warn("Email {} von der Person mit ID = {} wird gelöscht.", email, id);
        service.emailLoeschen(id, email);
    }

    //Person webseite loeshcen
    //{"webseite": "deletehiswebseite.com"}
    @DeleteMapping("/delete/{id}/webseite")
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void deleteWebseite(@PathVariable Long id, @RequestBody String webseite) {
        logger.warn("Webseite {} von der Person mit ID = {} wird gelöscht.", webseite, id);

        service.webseiteLoeschen(id, webseite);
    }

}
