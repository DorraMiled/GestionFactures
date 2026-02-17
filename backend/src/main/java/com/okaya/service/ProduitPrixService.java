package com.okaya.service;

import com.okaya.model.ProduitPrix;
import com.okaya.repository.ProduitPrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des prix des produits
 */
@Service
public class ProduitPrixService {
    
    @Autowired
    private ProduitPrixRepository produitPrixRepository;
    
    /**
     * Récupérer tous les prix
     */
    public List<ProduitPrix> getAllPrix() {
        return produitPrixRepository.findAll();
    }
    
    /**
     * Récupérer tous les prix d'un produit
     */
    public List<ProduitPrix> getPrixByProduitId(Long produitId) {
        return produitPrixRepository.findByProduitId(produitId);
    }
    
    /**
     * Récupérer le prix actif d'un produit à une date donnée
     */
    public Optional<ProduitPrix> getPrixActif(Long produitId, Date date) {
        return produitPrixRepository.findPrixActif(produitId, date);
    }
    
    /**
     * Créer ou mettre à jour un prix
     */
    public ProduitPrix saveProduitPrix(ProduitPrix produitPrix) {
        return produitPrixRepository.save(produitPrix);
    }
    
    /**
     * Supprimer un prix
     */
    public void deleteProduitPrix(Long id) {
        produitPrixRepository.deleteById(id);
    }
}
