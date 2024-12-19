package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirmaRequestDTO {

    private String name ;
    private List<AdresseRequestDTO> adresse ;
    private List<String> branche  ;
    private List<FirmaKontaktRequestDTO> kontaktList ;


}
