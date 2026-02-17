package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

/**
 * Entité Facture
 * Une facture appartient à un seul client
 * Une facture reste figée dans le temps (pas de modification après création)
 */
@Entity
@Table(name = "factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String reference;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateFacturation;
    
    @Temporal(TemporalType.DATE)
    private Date dateEcheance;
    
    // Relation avec Client (Many-to-One)
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    // Relation avec LigneFacture (One-to-Many)
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacture> lignes;
    
    // Totaux calculés et stockés pour figer la facture
    @Column(precision = 10, scale = 2)
    private BigDecimal totalHT;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal totalTVA;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal totalTTC;
}
