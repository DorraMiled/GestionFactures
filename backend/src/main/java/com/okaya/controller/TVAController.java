package com.okaya.controller;

import com.okaya.model.TVA;
import com.okaya.service.TVAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST pour la gestion des taux de TVA
 */
@RestController
@RequestMapping("/api/tva")
@CrossOrigin(origins = "http://localhost:4200")
public class TVAController {
    
    @Autowired
    private TVAService tvaService;
    
    /**
     * GET /api/tva - Récupérer tous les taux de TVA
     */
    @GetMapping
    public ResponseEntity<List<TVA>> getAllTVA() {
        return ResponseEntity.ok(tvaService.getAllTVA());
    }
    
    /**
     * GET /api/tva/{id} - Récupérer un taux de TVA par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TVA> getTVAById(@PathVariable Long id) {
        return tvaService.getTVAById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/tva - Créer un nouveau taux de TVA
     */
    @PostMapping
    public ResponseEntity<TVA> createTVA(@RequestBody TVA tva) {
        TVA savedTVA = tvaService.saveTVA(tva);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTVA);
    }
    
    /**
     * DELETE /api/tva/{id} - Supprimer un taux de TVA
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTVA(@PathVariable Long id) {
        tvaService.deleteTVA(id);
        return ResponseEntity.noContent().build();
    }
}
