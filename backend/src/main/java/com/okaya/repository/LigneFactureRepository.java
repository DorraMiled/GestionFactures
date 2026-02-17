package com.okaya.repository;

import com.okaya.model.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour l'entité LigneFacture
 * Permet l'accès aux données des lignes de facture
 */
@Repository
public interface LigneFactureRepository extends JpaRepository<LigneFacture, Long> {
    
    // Rechercher toutes les lignes d'une facture
    List<LigneFacture> findByFactureId(Long factureId);
}
