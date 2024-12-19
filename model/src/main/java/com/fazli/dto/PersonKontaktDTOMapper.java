package com.fazli.dto;


import com.fazli.PersonKontakt;

public class PersonKontaktDTOMapper  {


    public PersonKontaktDTO toDTO(PersonKontakt personKontakt) {
        return new PersonKontaktDTO(
                personKontakt.getId(),
                personKontakt.getFestnetzNummer(),
                personKontakt.getEmail(),
                personKontakt.getWebseite(),
                personKontakt.getMobilNummern()
        );
    }

    public PersonKontakt toPersonKontakt(PersonKontaktDTO personKontaktDTO) {
        return new PersonKontakt(
                personKontaktDTO.getId(),
                personKontaktDTO.getFestnetznummer(),
                personKontaktDTO.getEmail(),
                personKontaktDTO.getWebseite(),
                personKontaktDTO.getMobilnummern()
        );
    }

    public PersonKontakt toPersonKontakt(PersonKontaktRequestDTO personKontaktDTO) {
        return new PersonKontakt(
                personKontaktDTO.getFestnetznummer(),
                personKontaktDTO.getEmail(),
                personKontaktDTO.getWebseite(),
                personKontaktDTO.getMobilnummern()
        );
    }
}
