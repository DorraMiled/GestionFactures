package com.okaya.controller;

import com.okaya.model.Produit;
import com.okaya.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST pour la gestion des produits
 * Expose les endpoints API pour les produits
 */
@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitController {
    
    @Autowired
    private ProduitService produitService;
    
    /**
     * GET /api/produits - Récupérer tous les produits
     */
    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }
    
    /**
     * GET /api/produits/{id} - Récupérer un produit par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/produits - Créer un nouveau produit
     */
    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitService.saveProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
    }
    
    /**
     * PUT /api/produits/{id} - Mettre à jour un produit
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.getProduitById(id)
            .map(existingProduit -> {
                produit.setId(id);
                return ResponseEntity.ok(produitService.saveProduit(produit));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE /api/produits/{id} - Supprimer un produit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
