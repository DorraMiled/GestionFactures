package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité Client
 * Représente un client de l'entreprise
 */
@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String codeClient;
    
    @Column(nullable = false)
    private String nom;
    
    private String adresse;
    
    private String ville;
    
    private String codePostal;
    
    private String pays;
}
