package com.fazli;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FirmaRepo extends JpaRepository<Firma, Long> {




    @Query("SELECT f FROM Firma f " +
            "LEFT JOIN f.adresse ad " +
            "LEFT JOIN f.branchen br " +
            "WHERE " +
            "(:firmaname is null or lower(f.name)  like lower(concat('%',cast(:firmaname as string),'%') )  ) AND  " +
            "(:stadt is null or lower(ad.stadt) like lower(concat('%',cast(:stadt as string),'%'))   ) AND " +
            " (:bundesland is null or lower(ad.bundesland) like lower(concat('%',cast(:bundesland as string),'%') )   ) AND " +
            "(:plz is null or cast(:plz as string) like ad.plz ) AND " +
            "(:branche is null or lower(br) like lower(cast(:branche as string) ) ) "
    )
    List<Firma> genericSearch(
            @Param("firmaname") String firmaname,
            @Param("plz")String plz,
            @Param("stadt") String stadt ,
            @Param("bundesland") String bundesland,
            @Param("branche") String branche
    );




    @Query("SELECT f.adresse FROM Firma f " +
            "WHERE (:id is null or f.id=:id) ")
    List<Adresse> firmaAdresseLiefern(@Param("id") Long id);

    @Query("SELECT f.kontaktList FROM Firma f " +
            "WHERE(:id is null or f.id=:id)")
    List<FirmaKontakt> firmaKontaktLiefern(@Param("id")Long id);




}

