package com.okaya.repository;

import com.okaya.model.TVA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Optional;

/**
 * Repository pour l'entité TVA
 * Permet l'accès aux données des taux de TVA
 */
@Repository
public interface TVARepository extends JpaRepository<TVA, Long> {
    
    // Rechercher le taux de TVA actif à une date donnée
    @Query("SELECT t FROM TVA t WHERE t.dateDebut <= :date " +
           "AND (t.dateFin IS NULL OR t.dateFin >= :date)")
    Optional<TVA> findTVAActive(@Param("date") Date date);
}
