package com.okaya.service;

import com.okaya.model.Client;
import com.okaya.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des clients
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    /**
     * Récupérer tous les clients
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    /**
     * Récupérer un client par son ID
     */
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
    
    /**
     * Récupérer un client par son code
     */
    public Optional<Client> getClientByCode(String codeClient) {
        return clientRepository.findByCodeClient(codeClient);
    }
    
    /**
     * Créer ou mettre à jour un client
     */
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Supprimer un client
     */
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
