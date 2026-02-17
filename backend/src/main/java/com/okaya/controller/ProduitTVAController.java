package com.okaya.controller;

import com.okaya.model.ProduitTVA;
import com.okaya.service.ProduitTVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST pour la gestion de la TVA des produits
 */
@RestController
@RequestMapping("/api/produits-tva")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitTVAController {
    
    @Autowired
    private ProduitTVAService produitTVAService;
    
    /**
     * GET /api/produits-tva - Récupérer tous les ProduitTVA
     */
    @GetMapping
    public ResponseEntity<List<ProduitTVA>> getAllProduitTVA() {
        return ResponseEntity.ok(produitTVAService.getAllProduitTVA());
    }
    
    /**
     * GET /api/produits-tva/produit/{produitId} - Récupérer tous les taux de TVA d'un produit
     */
    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<ProduitTVA>> getTVAByProduitId(@PathVariable Long produitId) {
        return ResponseEntity.ok(produitTVAService.getTVAByProduitId(produitId));
    }
    
    /**
     * POST /api/produits-tva - Créer un nouveau ProduitTVA
     */
    @PostMapping
    public ResponseEntity<ProduitTVA> createProduitTVA(@RequestBody ProduitTVA produitTVA) {
        ProduitTVA savedProduitTVA = produitTVAService.saveProduitTVA(produitTVA);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduitTVA);
    }
    
    /**
     * DELETE /api/produits-tva/{id} - Supprimer un ProduitTVA
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitTVA(@PathVariable Long id) {
        produitTVAService.deleteProduitTVA(id);
        return ResponseEntity.noContent().build();
    }
}
