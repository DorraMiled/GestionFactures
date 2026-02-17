package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Entité ProduitTVA
 * Associe un produit à un taux de TVA pour une période donnée
 * Permet de gérer les changements de TVA applicables aux produits
 */
@Entity
@Table(name = "produits_tva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitTVA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relation avec Produit (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    // Relation avec TVA (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "tva_id", nullable = false)
    private TVA tva;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    private Date dateFin;
}
