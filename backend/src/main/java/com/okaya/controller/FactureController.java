package com.okaya.controller;

import com.okaya.model.Facture;
import com.okaya.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST pour la gestion des factures
 * Expose les endpoints API pour les factures
 */
@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "http://localhost:4200")
public class FactureController {
    
    @Autowired
    private FactureService factureService;
    
    /**
     * GET /api/factures - Récupérer toutes les factures
     */
    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }
    
    /**
     * GET /api/factures/{id} - Récupérer une facture par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/factures/client/{clientId} - Récupérer toutes les factures d'un client
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Facture>> getFacturesByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(factureService.getFacturesByClientId(clientId));
    }
    
    /**
     * POST /api/factures - Créer une nouvelle facture
     * La facture est automatiquement figée avec les prix et TVA actuels
     */
    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        Facture savedFacture = factureService.createFacture(facture);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFacture);
    }
    
    /**
     * DELETE /api/factures/{id} - Supprimer une facture
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }
}
