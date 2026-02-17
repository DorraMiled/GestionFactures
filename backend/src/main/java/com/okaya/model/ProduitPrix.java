package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité ProduitPrix
 * Représente l'historique des prix d'un produit
 * Permet de gérer les changements de prix dans le temps
 */
@Entity
@Table(name = "produits_prix")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitPrix {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relation avec Produit (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal prixHT;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    private Date dateFin;
}
