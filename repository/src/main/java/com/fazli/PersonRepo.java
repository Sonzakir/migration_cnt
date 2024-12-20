package com.fazli;

import com.fazli.Adresse;
import com.fazli.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {






    @Query("SELECT p FROM Person p " +
            "LEFT JOIN p.adresse ad " +
            "WHERE " +
            "(:vorname IS NULL OR lower(p.vorname) like lower(concat('%',cast(:vorname as string),'%')) ) AND " +
            "(:nachname IS NULL OR lower(p.nachname) like lower(concat('%',cast(:nachname as string ) ,'%') ) ) AND " +
            "(:stadt IS NULL OR (lower(ad.stadt) LIKE lower(concat('%',cast(:stadt as string),'%' ) ) )) AND " +
            "(:bundesland IS NULL OR (lower(ad.bundesland) LIKE lower(concat('%',cast(:bundesland as string),'%' ) ))) ")
    List<Person> suche(@Param("vorname") String vorname, @Param("nachname") String nachname
            , @Param("stadt")String stadt, @Param("bundesland")String bundesland);


    @Query("SELECT p.adresse FROM Person p " +
            "WHERE (:id IS NULL OR p.id=:id )")
    List<Adresse> searchAdresse(@Param("id") Long id);


}
