package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité Produit
 * Représente un produit du catalogue
 * Le nom et le prix peuvent changer dans le temps
 */
@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String reference;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(length = 1000)
    private String description;
}
