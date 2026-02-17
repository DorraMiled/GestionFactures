package com.okaya.service;

import com.okaya.model.ProduitTVA;
import com.okaya.repository.ProduitTVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion de la TVA des produits
 */
@Service
public class ProduitTVAService {
    
    @Autowired
    private ProduitTVARepository produitTVARepository;
    
    /**
     * Récupérer tous les enregistrements ProduitTVA
     */
    public List<ProduitTVA> getAllProduitTVA() {
        return produitTVARepository.findAll();
    }
    
    /**
     * Récupérer tous les taux de TVA d'un produit
     */
    public List<ProduitTVA> getTVAByProduitId(Long produitId) {
        return produitTVARepository.findByProduitId(produitId);
    }
    
    /**
     * Récupérer le taux de TVA actif d'un produit à une date donnée
     */
    public Optional<ProduitTVA> getTVAActive(Long produitId, Date date) {
        return produitTVARepository.findTVAActive(produitId, date);
    }
    
    /**
     * Créer ou mettre à jour un ProduitTVA
     */
    public ProduitTVA saveProduitTVA(ProduitTVA produitTVA) {
        return produitTVARepository.save(produitTVA);
    }
    
    /**
     * Supprimer un ProduitTVA
     */
    public void deleteProduitTVA(Long id) {
        produitTVARepository.deleteById(id);
    }
}
