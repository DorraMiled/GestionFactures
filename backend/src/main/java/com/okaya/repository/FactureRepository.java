package com.okaya.repository;

import com.okaya.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Facture
 * Permet l'accès aux données des factures
 */
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    
    // Rechercher une facture par sa référence
    Optional<Facture> findByReference(String reference);
    
    // Rechercher toutes les factures d'un client
    List<Facture> findByClientId(Long clientId);
    
    // Vérifier si une facture existe par sa référence
    boolean existsByReference(String reference);
}
