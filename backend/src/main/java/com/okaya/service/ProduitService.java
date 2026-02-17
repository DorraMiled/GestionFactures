package com.okaya.service;

import com.okaya.model.Produit;
import com.okaya.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des produits
 */
@Service
public class ProduitService {
    
    @Autowired
    private ProduitRepository produitRepository;
    
    /**
     * Récupérer tous les produits
     */
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
    
    /**
     * Récupérer un produit par son ID
     */
    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }
    
    /**
     * Récupérer un produit par sa référence
     */
    public Optional<Produit> getProduitByReference(String reference) {
        return produitRepository.findByReference(reference);
    }
    
    /**
     * Créer ou mettre à jour un produit
     */
    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }
    
    /**
     * Supprimer un produit
     */
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}
