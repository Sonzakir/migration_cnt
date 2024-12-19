package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FirmaDTO {

    private Long id;
    private String name ;
    private List<AdresseDTO> adresse ;
    private List<String> branche  ;
    private List<FirmaKontaktDTO> kontaktList ;

}


