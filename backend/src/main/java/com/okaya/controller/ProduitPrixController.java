package com.okaya.controller;

import com.okaya.model.ProduitPrix;
import com.okaya.service.ProduitPrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Controller REST pour la gestion des prix des produits
 */
@RestController
@RequestMapping("/api/produits-prix")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitPrixController {
    
    @Autowired
    private ProduitPrixService produitPrixService;
    
    /**
     * GET /api/produits-prix - Récupérer tous les prix
     */
    @GetMapping
    public ResponseEntity<List<ProduitPrix>> getAllPrix() {
        return ResponseEntity.ok(produitPrixService.getAllPrix());
    }
    
    /**
     * GET /api/produits-prix/produit/{produitId} - Récupérer tous les prix d'un produit
     */
    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<ProduitPrix>> getPrixByProduitId(@PathVariable Long produitId) {
        return ResponseEntity.ok(produitPrixService.getPrixByProduitId(produitId));
    }
    
    /**
     * GET /api/produits-prix/actif/{produitId}/{date} - Récupérer le prix actif d'un produit
     */
    @GetMapping("/actif/{produitId}/{date}")
    public ResponseEntity<ProduitPrix> getPrixActif(
            @PathVariable Long produitId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return produitPrixService.getPrixActif(produitId, date)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/produits-prix - Créer un nouveau prix
     */
    @PostMapping
    public ResponseEntity<ProduitPrix> createProduitPrix(@RequestBody ProduitPrix produitPrix) {
        ProduitPrix savedPrix = produitPrixService.saveProduitPrix(produitPrix);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrix);
    }
    
    /**
     * DELETE /api/produits-prix/{id} - Supprimer un prix
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitPrix(@PathVariable Long id) {
        produitPrixService.deleteProduitPrix(id);
        return ResponseEntity.noContent().build();
    }
}
