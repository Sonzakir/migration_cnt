package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonRequestDTO {

    private String vorname;
    private String nachname;
    private List<AdresseRequestDTO> adresse;
    private List<PersonKontaktRequestDTO> kontaktList;



}
