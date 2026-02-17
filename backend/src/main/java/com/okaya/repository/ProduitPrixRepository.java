package com.okaya.repository;

import com.okaya.model.ProduitPrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité ProduitPrix
 * Permet l'accès aux données des prix des produits
 */
@Repository
public interface ProduitPrixRepository extends JpaRepository<ProduitPrix, Long> {
    
    // Rechercher tous les prix d'un produit
    List<ProduitPrix> findByProduitId(Long produitId);
    
    // Rechercher le prix actif d'un produit à une date donnée
    @Query("SELECT pp FROM ProduitPrix pp WHERE pp.produit.id = :produitId " +
           "AND pp.dateDebut <= :date " +
           "AND (pp.dateFin IS NULL OR pp.dateFin >= :date)")
    Optional<ProduitPrix> findPrixActif(@Param("produitId") Long produitId, 
                                        @Param("date") Date date);
}
