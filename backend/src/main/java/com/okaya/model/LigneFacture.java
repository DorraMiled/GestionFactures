package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entité LigneFacture
 * Représente une ligne dans une facture
 * Stocke toutes les informations du produit au moment de la facturation
 * pour figer la facture dans le temps
 */
@Entity
@Table(name = "lignes_facture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneFacture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relation avec Facture (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "facture_id", nullable = false)
    @JsonIgnore // Éviter la récursion lors de la sérialisation JSON
    private Facture facture;
    
    // Relation avec Produit (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    // Désignation du produit au moment de la facturation (figée)
    @Column(nullable = false)
    private String designation;
    
    // Prix unitaire HT au moment de la facturation (figé)
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal prixUnitaireHT;
    
    @Column(nullable = false)
    private Integer quantite;
    
    // Taux de TVA au moment de la facturation (figé)
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal tauxTVA;
    
    // Totaux calculés et figés
    @Column(precision = 10, scale = 2)
    private BigDecimal totalHT;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal totalTTC;
}
