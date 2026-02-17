package com.okaya.repository;

import com.okaya.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour l'entité Client
 * Permet l'accès aux données des clients
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    // Rechercher un client par son code
    Optional<Client> findByCodeClient(String codeClient);
    
    // Vérifier si un client existe par son code
    boolean existsByCodeClient(String codeClient);
}
