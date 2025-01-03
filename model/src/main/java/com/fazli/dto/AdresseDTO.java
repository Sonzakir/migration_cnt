package com.fazli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdresseDTO {
    private Long id;
    private String strasse;
    private String hausNo;
    private String plz;
    private String stadt;
    private String bundesland;


}
