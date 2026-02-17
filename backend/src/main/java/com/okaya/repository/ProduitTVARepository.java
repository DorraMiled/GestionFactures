package com.okaya.repository;

import com.okaya.model.ProduitTVA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité ProduitTVA
 * Permet l'accès aux données de TVA des produits
 */
@Repository
public interface ProduitTVARepository extends JpaRepository<ProduitTVA, Long> {
    
    // Rechercher tous les taux de TVA d'un produit
    List<ProduitTVA> findByProduitId(Long produitId);
    
    // Rechercher le taux de TVA actif d'un produit à une date donnée
    @Query("SELECT pt FROM ProduitTVA pt WHERE pt.produit.id = :produitId " +
           "AND pt.dateDebut <= :date " +
           "AND (pt.dateFin IS NULL OR pt.dateFin >= :date)")
    Optional<ProduitTVA> findTVAActive(@Param("produitId") Long produitId, 
                                       @Param("date") Date date);
}
