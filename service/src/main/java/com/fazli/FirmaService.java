package com.fazli;


import com.fazli.aspect.LogEntryExit;
import com.fazli.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FirmaService {

    private final FirmaRepo repository;
    private EntityManager entityManager;



    private FirmaKontaktDTOMapper firmaKontaktDTOMapper = new FirmaKontaktDTOMapper();
//    private AdresseMapper adresseMapper = new AdresseMapper();
    private final AdresseMapper adresseMapper = Mappers.getMapper(AdresseMapper.class);
    private final FirmaDTOMapper firmaDtoMapper = new FirmaDTOMapper(adresseMapper,firmaKontaktDTOMapper);

    private final Logger logger  = LoggerFactory.getLogger(FirmaService.class);

    public FirmaService(FirmaRepo repository,
                        EntityManager entityManager) {
        this.repository = repository;
        this.entityManager=entityManager;
    }







    //                            Hinzufuegen



    // eine Firma Hinzufuegen
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO firmaHinzufuegen(FirmaRequestDTO firmaDTO) {
        Firma f = firmaDtoMapper.toFirma(firmaDTO);
        logger.info("Die Firma RequestDTO  {} wird auf Firma gemapped {}" ,firmaDTO, f);
        Firma firma = repository.save(f);
        logger.info("Die Firma {} wurde gespeichert {} ", firma);
        return firmaDtoMapper.toDTO(firma);
    }





    // fügt Firma mit id eine Adresse hinzu
    @Transactional
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO neueAdresseHinzufuegen(Long firmaID , AdresseRequestDTO adresseDTO){
        Adresse adresse = adresseMapper.toAdresse(adresseDTO);
        logger.info("Die Adresse RequestDTO {} wird auf Adresse gemapped {}" ,adresseDTO, adresse);
        Firma f = repository.findById(firmaID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Adresse> adresses = new ArrayList<>(f.getAdresse());
        logger.info("Die Adresse der Firma {} VOR dem Hinzufügen {} ", f , adresses);
        adresses.add(adresse);
        f.setAdresse(adresses);
        entityManager.persist(f);
        logger.info("Die Adresse der Firma {} NACH dem Hinzufügen {} ", f , f.getAdresse());
        return firmaDtoMapper.toDTO(f);
    }


    // fügt Firma mit der id eine neue Kontakt hinzu
    @Transactional
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO neueKontaktHinzufuegen(Long firmaID,FirmaKontaktRequestDTO firmaKontaktDTO){
        FirmaKontakt kontakt = firmaKontaktDTOMapper.toFirmaKontakt(firmaKontaktDTO);
        logger.info("Die Kontakt RequestDTO {} wird auf Kontakt {} gemapped " , firmaKontaktDTO , kontakt);
        Firma firma =repository.findById(firmaID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<FirmaKontakt> kontaktList = new ArrayList<>(firma.getKontaktList());
        logger.info("Die Kontakten der Firma {} VOR dem Hinzufügen {} ", firma , kontaktList);

        kontaktList.add(kontakt);
        firma.setKontaktList(kontaktList);
        logger.info("Die Kontakten der Firma {} NACH dem Hinzufügen {} ", firma , firma.getKontaktList());

        entityManager.persist(firma);
        return firmaDtoMapper.toDTO(firma);
    }

    // fügt Firma mit der id eine neue Branche hinzu
    // BODY = > ANWALT (kein{},kein "")
    @Transactional
    @LogEntryExit(showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO neueBrancheHinzufuegen(Long firmaID , String branche ){
        Firma firma =repository.findById(firmaID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean brancheExists = Branche.stream().anyMatch(br->br.getName().equals(branche.toUpperCase()));
        if(brancheExists){
            Branche neueBranche = Branche.valueOf(branche.toUpperCase());
            List<Branche> brancheList = new ArrayList<>(firma.getBranchen());
            brancheList.add(neueBranche);
            firma.setBranchen(brancheList);
            logger.info("Die Branche {} wurde an Firma {} hinzugefügt" , branche , firma);
            entityManager.persist(firma);
        }else{
            logger.warn("Es existiert keine solche Branche.");
        }

        return firmaDtoMapper.toDTO(firma);
    }




    //                        Suchmethoden



    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = false, dauer = ChronoUnit.MILLIS)
    public List<FirmaDTO> suche (Long firmaID,
                                 String firmaname,
                                 String plz,
                                 String stadt,
                                 String bundesland,
                                 String branche

    ){


        List<Firma> lst;
        if(firmaID != null) {
            logger.info("Die Firma mit der ID = {} wird gesucht ", firmaID);
            Firma firma = repository.findById(firmaID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            lst = new ArrayList<>(List.of(firma));
        }
        else if(firmaname == null && plz == null && stadt == null && bundesland == null && branche == null) {
            logger.warn("Kein Suchparameter gegeben. Alle Firmen wird zurückgegeben");
            lst = repository.findAll();
        }
        else{
            logger.warn("Kein Firma ID gegeben. Möglicherweise mehrere Firmen wird zurückgegeben");
            logger.info("Die Firma suche mit folgender Schlüsseln wird ausgeführt firmaname = {} , plz = {} , stadt = {} , bundesland = {} , branche = {}",
                    firmaname, plz, stadt, bundesland, branche);
            lst = repository.genericSearch(firmaname,plz,stadt,bundesland,branche);
        }

        return lst.stream()
                .map(firmaDtoMapper::toDTO)
                .collect(Collectors.toList());

    }




    // liefert (alle) adressen [opt] firma id
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public List<AdresseDTO> adresseLiefern(Long firmaID){
        if(firmaID==null){
            logger.warn("Wenn keine ID angegeben wird, wird alle Adressen zurückgegeben");
        }
        else{
            logger.info("Die Adressen der Firma mit ID = {} wird zurückgegeben.", firmaID);
        }
        return repository.firmaAdresseLiefern(firmaID)
                .stream()
                .map(adresseMapper::toDTO)
                .collect(Collectors.toList());
    }

    // liefert (alle) kontakten [opt] firma id
    @LogEntryExit(level= LogLevel.INFO, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public List<FirmaKontaktDTO> firmaKontaktLiefern(Long firmaID){
        if(firmaID==null){
            logger.warn("Wenn keine ID angegeben wird, wird alle gespeicherten Kontakten zurückgegeben");
        }
        else{
            logger.info("Die Kontakten der Firma mit ID = {} wird zurückgegeben.", firmaID);
        }
        return repository.firmaKontaktLiefern(firmaID)
                .stream()
                .map(firmaKontaktDTOMapper::toDTO)
                .collect(Collectors.toList());
    }




//                         Aktualisieren


    //  ändert eine Firma mit der ID
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO firmaAendern(Long personID, FirmaRequestDTO firmaDTO){
        Firma firma=repository.findById(personID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Firma neu = firmaDtoMapper.toFirma(firmaDTO);
        logger.info("Die Firma RequestDTO {} wird auf Firma {} gemapped" , firmaDTO , neu);

        String name= neu.getName();
        List<FirmaKontakt> kontakt = new ArrayList<>(neu.getKontaktList());
        List<Adresse> adresse = new ArrayList<>(neu.getAdresse());
        List<Branche> branche = new ArrayList<>(neu.getBranchen());

        firma.setName(name);
        if(name!=null){
            logger.info("Die Firma Name wurde auf {} geändert" ,name);
        }
        firma.setKontaktList(kontakt);
        if(kontakt!=null){
            logger.info("Die Firma Kontakt wurde auf {} geändert", kontakt);
        }
        firma.setAdresse(adresse);
        if (adresse!=null){
            logger.info("Die Firma Adresse wurde auf {} geändert" ,adresse);
        }
        firma.setBranchen(branche);
        if (branche!=null){
            logger.info("Die Firma Branche wurde auf {} geändert" ,branche);

        }


        entityManager.persist(firma);
        return firmaDtoMapper.toDTO(firma);
    }

    // ändert die Adresse mit aid von Person mit pid
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO firmaAdresseAendern(Long personID , Long adresseID,AdresseRequestDTO adresseDTO){
        Firma firma = repository.findById(personID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Adresse adresse= adresseMapper.toAdresse(adresseDTO);
        logger.info("Die Adresse RequestDTO {} wird auf Adresse {} gemapped", adresseDTO , adresse);
        List<Adresse> adressen = firma.getAdresse().stream()
                .filter(ad->!ad.getId().equals(adresseID))
                .collect(Collectors.toCollection(ArrayList::new));
        logger.info("Die Firma hat vor der Änderung folgender Adresse/n {} .", adressen);
        adressen.add(adresse);
        logger.info("Die Firma hat nach der Änderung folgender Adresse/n", adressen);
        firma.setAdresse(adressen);


        entityManager.persist(firma);
        return firmaDtoMapper.toDTO(firma);
    }


    // ändert die Kontakt mit kid von Firma mit id
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public FirmaDTO firmaKontakAendern(Long personID , Long kontaktID  ,FirmaKontaktRequestDTO firmaKontaktDTO){
        Firma firma=repository.findById(personID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        FirmaKontakt kontakt = firmaKontaktDTOMapper.toFirmaKontakt(firmaKontaktDTO);
        logger.info("Die Firma KontaktRequestDTO {} wird auf Firma {} gemapped. ", firmaKontaktDTO , kontakt);

        List<FirmaKontakt> kontaktList = firma.getKontaktList().stream()
                .filter(kntk->!kntk.getId().equals(kontaktID))
                .collect(Collectors.toCollection(ArrayList::new));
        kontaktList.add(kontakt);
        logger.info("Die Firma Kontakten wurde aktualisiert {} ", kontaktList);
        firma.setKontaktList(kontaktList);



        entityManager.persist(firma);
        return firmaDtoMapper.toDTO(firma);
    }



    //                        Löschmethoden


    //T loesche (alle) Firma [opt] ID,stadt,bundesland
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean firmaLoeschen(Long firmaID, String stadt, String bundesland){
        // ID vorhanden
        if(firmaID!=null){
            if(repository.existsById(firmaID)){
                repository.deleteById(firmaID);
                logger.info("Die Firma mit ID = {} wurde gelöscht" , firmaID);
                return true;
            }
            return false;
        }
        // Loesche Firmen nach stadt und oder bundesland
        var size = repository.count();
        logger.warn("Kein ID wurde angegeben. Möglicherweise mehrere Firmen wird gelöscht");
        List<Firma> lst = repository.genericSearch(null,null,stadt,bundesland,null);
        repository.deleteAll(lst);
        return size>repository.count();
    }

    // löscht die Adresse mit aid von Firma mit id
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean adresseEntfernen(Long firmaID , Long adresseID){
        Firma f = repository.findById(firmaID)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        var size = f.getAdresse().size();
        logger.info("Die Adresse mit ID = {} von der Firma mit ID = {} wird gelöscht", adresseID,firmaID);
        List<Adresse> lst = f.getAdresse().stream()
                .filter(ad->!ad.getId().equals(adresseID))
                .collect(Collectors.toCollection(ArrayList::new));
        f.setAdresse(lst);
        //entityManager.merge(f);
        entityManager.persist(f);
        return f.getAdresse().size() == (size-1);

    }

    //  löscht die Kontakt mit kid von Person mit pid
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public boolean kontaktEntfernen(Long firmaID, Long kontaktID){
        Firma f = repository.findById(firmaID)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        var size = f.getKontaktList().size();
        logger.info("Die Kontakt mit ID= {} von der Firma mit ID = {} wird gelöscht",kontaktID,firmaID);
        List<FirmaKontakt> lst = f.getKontaktList().stream()
                .filter(kntkt->!kntkt.getId().equals(kontaktID))
                .collect(Collectors.toCollection(ArrayList::new));
        f.setKontaktList(lst);
        //entityManager.merge(f);
        entityManager.persist(f);
        return f.getKontaktList().size() == (size-1);
    }






    // eine festneznummerLoeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void festnetzNoLoeschen(Long personID , String festnetz){
        Firma f = repository.findById(personID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<FirmaKontakt> lst = f.getKontaktList();
        for(FirmaKontakt pk : lst){
            List<String> festnetznummerList = pk.getFestnetzNummer();
            festnetznummerList.removeIf(n->n.equals(festnetz));
            pk.setFestnetzNummer(festnetznummerList);
        }
        f.setKontaktList(lst);
        entityManager.merge(f);
    }

    // eine email loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void emailLoeschen(Long personID ,String email){
        Firma f = repository.findById(personID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<FirmaKontakt> lst = f.getKontaktList();
        for(FirmaKontakt pk : lst){
            List<String> emailList = pk.getEmail();
            emailList.removeIf(n->n.equals(email));
            pk.setEmail(emailList);
        }
        f.setKontaktList(lst);
        entityManager.merge(f);
    }

    //eine webseite loeschen
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void webseiteLoeschen(Long personID , String webseite){
        Firma p = repository.findById(personID).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<FirmaKontakt> lst = p.getKontaktList();
        for(FirmaKontakt pk : lst){
            List<String> webseiteList = pk.getWebseite();
            webseiteList.removeIf(n->n.equals(webseite));
            pk.setWebseite(webseiteList);
        }
        p.setKontaktList(lst);
        entityManager.merge(p);
    }

    // loesche eine adresse
    @Transactional
    @LogEntryExit(level= LogLevel.WARN, showArgs = true, showResult = true, dauer = ChronoUnit.MILLIS)
    public void adresseLoeschen(Long personID , Adresse adresse){
        Firma f= repository.findById(personID).orElseThrow(()->new NoSuchElementException("Es gibt keine firma"));
        f.getAdresse().remove(adresse);
        entityManager.merge(f);
    }











}
