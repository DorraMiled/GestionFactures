package com.okaya.controller;

import com.okaya.model.Client;
import com.okaya.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST pour la gestion des clients
 * Expose les endpoints API pour les clients
 */
@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    /**
     * GET /api/clients - Récupérer tous les clients
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
    
    /**
     * GET /api/clients/{id} - Récupérer un client par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/clients - Créer un nouveau client
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
    
    /**
     * PUT /api/clients/{id} - Mettre à jour un client
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.getClientById(id)
            .map(existingClient -> {
                client.setId(id);
                return ResponseEntity.ok(clientService.saveClient(client));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE /api/clients/{id} - Supprimer un client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
