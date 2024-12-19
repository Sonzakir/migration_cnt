package com.fazli.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdresseDTO {
    private Long id;
    private String strasse;
    private String hausNo;
    private String plz;
    private String stadt;
    private String bundesland;


}
