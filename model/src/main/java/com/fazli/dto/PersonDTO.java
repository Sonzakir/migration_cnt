package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonDTO {
    private Long id;
    private String vorname;
    private String nachname;

    private List<AdresseDTO> adresse;
    private List<PersonKontaktDTO> kontaktList;

}
