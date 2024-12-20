package com.fazli;


import com.fazli.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/firma")
public class FirmaController {

    private final Logger logger = LoggerFactory.getLogger(FirmaController.class);


    private FirmaService firmaService;

    public FirmaController(FirmaService firmaService) {
        this.firmaService = firmaService;
    }







    //                  Hinzufuegen



    @PostMapping("")
    public FirmaDTO firmaHinzufuegen(@RequestBody FirmaRequestDTO firmaDTO) {

        logger.info("füge firma hinzu {}", firmaDTO);


        return firmaService.firmaHinzufuegen(firmaDTO);
    }



    // fügt Firma mit id eine Kontakt hinzu
    @PostMapping("/{id}/kontakt")
    public FirmaDTO neueKontaktHinzufuegen(@PathVariable Long id , @RequestBody FirmaKontaktRequestDTO kontakt){
        logger.info("füge neue Kontakt {} für die Firma mit ID = {}",kontakt , id);
        return firmaService.neueKontaktHinzufuegen(id,kontakt);
    }

    // fügt Firma mit id eine Adressse hinzu
    @PostMapping("/{id}/adresse")
    public FirmaDTO neueAdressHinzufuegen(@PathVariable Long id , @RequestBody AdresseRequestDTO adresseDTO){
        logger.info("füge neue Adresse {} für die Firma mit ID = {}" , adresseDTO , id);
        return firmaService.neueAdresseHinzufuegen(id,adresseDTO);
    }


    @PostMapping("/{id}/branche")
    public FirmaDTO neueBrancheHinzufuegen(@PathVariable Long id , @RequestBody String branche){
        logger.info("füge neue Branche {} für die Firma mit ID = {}",branche , id);
        return firmaService.neueBrancheHinzufuegen(id,branche);
    }






    //                              Suchmethoden



    @GetMapping("")
    public List<FirmaDTO> suche(
            @RequestParam(name="id",required = false) Long id,
            @RequestParam(name = "firmaname",required = false)String firmaname,
            @RequestParam(required = false) String plz,
            @RequestParam(required = false) String stadt,
            @RequestParam(required = false) String bundesland,
            @RequestParam(required = false) String branche
    ){
        logger.info("Firmensuche wird ausgeführt id = {} , firamame = {} , " +
                "plz ={} ,stadt = {} , bundesland = {} ,  branche = {} " , id, firmaname, plz, stadt,bundesland, branche);
        return firmaService.suche(id,firmaname,plz,stadt,bundesland,branche);
    }

    // liefert (alle) adressen [opt] firma id
    @GetMapping("/adresse")
    public List<AdresseDTO> adresseLiefern(@RequestParam(required = false)Long id){
        logger.info("Adressen von Firma mit ID = {} wird geliefert." , id);
        if(id==null){
            logger.warn("Es wurde keine ID angegeben, alle Adressen wird zurückgegeben ");
        }
        return firmaService.adresseLiefern(id);
    }


    // liefert (alle) firmakontakten [opt] person id
    @GetMapping("/kontakt")
    public List<FirmaKontaktDTO> firmaKontaktLiefern(@RequestParam(required = false)Long id){
        logger.info("Kontakten von Firma mit ID = {} wird geliefert." , id);
        if(id==null){
            logger.warn("Es wurde keine ID angegeben, alle Kontakten wird zurückgegeben ");
        }
        return firmaService.firmaKontaktLiefern(id);
    }


//                            Aktualisieren


    // ändert eine Firma mit id
    @PutMapping("/{id}")
    public FirmaDTO firmaAendern(@PathVariable Long id ,  @RequestBody FirmaRequestDTO firmaDTO){
        logger.info("Firma mit ID = {} wird durch {} ersetzt." ,id, firmaDTO);
        return firmaService.firmaAendern(id, firmaDTO);
    }

    // ändert die Adresse mit aid von Firma mit firmaID=pid
    @PutMapping("/{pid}/adresse/{aid}")
    public FirmaDTO firmaAdresseAendern(@PathVariable Long pid, @PathVariable Long aid ,
                                        @RequestBody AdresseRequestDTO adresseDTO){
        logger.info("Die Adresse mit ID = {} von der Firma ID = {} wird durch {} ersetzt.",
                aid ,pid , adresseDTO);
        return firmaService.firmaAdresseAendern(pid,aid,adresseDTO);
    }

    // ändert die Kontakt mit kid von Firma mit id
    @PutMapping("/{id}/kontakt/{kid}")
    public FirmaDTO firmaKontaktAendern(@PathVariable Long id, @PathVariable Long kid,
                                        @RequestBody FirmaKontaktRequestDTO firmaKontaktDTO){
        logger.info("Der Kontakt mit ID = {} von der Firma mit ID = {} wird durch {} ersetzt." ,
                kid ,id , firmaKontaktDTO);
        return firmaService.firmaKontakAendern(id,kid,firmaKontaktDTO);
    }







    //                          LoeschMethoden


    // loesche (alle) Firma [opt] ID,stadt,bundesland
    @DeleteMapping("")
    public boolean loeschen(@RequestParam(required = false)Long id,
                            @RequestParam(required = false)String stadt,
                            @RequestParam(required = false) String bundesland){
        if(id!=null){
            logger.warn("Firma mit ID = {} wird entfernt." , id);
        } else if (stadt!=null || bundesland!=null) {
            logger.warn("Mehrere Firmen mit Stadt = {} und Bundesland = {} wird gelöscht.", stadt, bundesland);
        }
        else{
            logger.warn("ALLE Firmen wird gelöscht.");
        }

        return firmaService.firmaLoeschen(id,stadt, bundesland);
    }

    // löscht die Adresse mit aid von Firma mit id
    @DeleteMapping("/{id}/adresse/{aid}")
    public boolean adresseEntfernen(@PathVariable Long id,
                                    @PathVariable Long aid){
        logger.warn("Die Adresse mit ID = {} von der Firma mit ID = {} wird gelöscht", aid, id);
        return firmaService.adresseEntfernen(id, aid);    }

    // löscht die Kontakt mit kid von Firma mit id
    @DeleteMapping("/{id}/kontakt/{kid}")
    public boolean kontaktEntfernen(@PathVariable Long id,
                                    @PathVariable Long kid){
        logger.warn("Die Kontakt mit ID = {} von der Firma mit ID = {} wird gelöscht. ",kid, id);
        return firmaService.kontaktEntfernen(id, kid);
    }












    // eine Festnetznummer Loeschen
    @DeleteMapping("/delete/{id}/festnetzNo")
    public void festnetzNoLoeschen(@PathVariable Long id , @RequestBody String festnetznummer) {
        logger.warn("Festnetznummer {} von der Firma mit ID = {} wird gelöscht.", festnetznummer, id);
        firmaService.festnetzNoLoeschen(id, festnetznummer);
    }


    //Person Email loeschen
    @DeleteMapping("/delete/{id}/email")
    public void emailLoeschen(@PathVariable Long id, @RequestBody String email) {
        logger.warn("Email {} von der Firma mit ID = {} wird gelöscht.", email, id);
        firmaService.emailLoeschen(id, email);
    }


    //Person webseite loeshcen
    @DeleteMapping("/delete/{id}/webseite")
    public void webseiteLoeschen(@PathVariable Long id, @RequestBody String webseite) {
        logger.warn("Webseite {} von der Firma mit ID = {} wird gelöscht.", webseite, id);
        firmaService.webseiteLoeschen(id, webseite);
    }

    //loesche eine Adresse
    @DeleteMapping("/delete/{id}/adresse")
    public void adresseLoeschen(@PathVariable Long id,  @RequestBody Adresse adresse){
        logger.warn("Adressse {} von der Firma mit ID = {} wird gelöscht.", adresse, id);
        firmaService.adresseLoeschen(id,adresse);
    }



}
