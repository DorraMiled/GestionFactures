package com.okaya.repository;

import com.okaya.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour l'entité Produit
 * Permet l'accès aux données des produits
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    
    // Rechercher un produit par sa référence
    Optional<Produit> findByReference(String reference);
    
    // Vérifier si un produit existe par sa référence
    boolean existsByReference(String reference);
}
