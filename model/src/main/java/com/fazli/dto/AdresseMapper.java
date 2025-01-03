package com.fazli.dto;


import com.fazli.Adresse;
import com.fazli.aspect.LogEntryExit;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.logging.LogLevel;

import java.time.temporal.ChronoUnit;

@Mapper()
public interface AdresseMapper {
    AdresseMapper INSTANCE = Mappers.getMapper(AdresseMapper.class);

    AdresseDTO toDTO(Adresse adresse);

    Adresse toAdresse(AdresseDTO adresseDTO);

    Adresse toAdresse(AdresseRequestDTO adresseDTO);
}

