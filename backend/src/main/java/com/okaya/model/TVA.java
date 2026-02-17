package com.okaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Entité TVA
 * Représente un taux de TVA défini par période
 * Permet de gérer les changements de taux de TVA dans le temps
 */
@Entity
@Table(name = "tva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(precision = 5, scale = 2, nullable = false)
    private java.math.BigDecimal taux;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    private Date dateFin;
}
