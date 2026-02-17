package com.okaya.service;

import com.okaya.model.Facture;
import com.okaya.model.LigneFacture;
import com.okaya.model.Client;
import com.okaya.model.Produit;
import com.okaya.model.ProduitPrix;
import com.okaya.model.ProduitTVA;
import com.okaya.repository.FactureRepository;
import com.okaya.repository.ClientRepository;
import com.okaya.repository.ProduitRepository;
import com.okaya.repository.ProduitPrixRepository;
import com.okaya.repository.ProduitTVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des factures
 * Gère la création de factures figées dans le temps
 */
@Service
public class FactureService {
    
    @Autowired
    private FactureRepository factureRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private ProduitPrixRepository produitPrixRepository;
    
    @Autowired
    private ProduitTVARepository produitTVARepository;
    
    /**
     * Récupérer toutes les factures
     */
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }
    
    /**
     * Récupérer une facture par son ID
     */
    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }
    
    /**
     * Récupérer toutes les factures d'un client
     */
    public List<Facture> getFacturesByClientId(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }
    
    /**
     * Créer une nouvelle facture
     * Cette méthode fige tous les éléments de la facture au moment de la création
     */
    @Transactional
    public Facture createFacture(Facture facture) {
        // Charger le client complet depuis la base de données
        if (facture.getClient() != null && facture.getClient().getId() != null) {
            Client client = clientRepository.findById(facture.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + facture.getClient().getId()));
            facture.setClient(client);
        }
        
        // Calculer les lignes de facture avec les prix et TVA actuels
        for (LigneFacture ligne : facture.getLignes()) {
            // Charger le produit complet depuis la base de données
            if (ligne.getProduit() != null && ligne.getProduit().getId() != null) {
                Produit produit = produitRepository.findById(ligne.getProduit().getId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + ligne.getProduit().getId()));
                ligne.setProduit(produit);
            }
            
            // Récupérer le prix actif du produit
            Optional<ProduitPrix> prixActif = produitPrixRepository
                .findPrixActif(ligne.getProduit().getId(), facture.getDateFacturation());
            
            if (prixActif.isPresent()) {
                ligne.setPrixUnitaireHT(prixActif.get().getPrixHT());
            } else {
                throw new RuntimeException("Aucun prix actif trouvé pour le produit: " + ligne.getProduit().getNom() + " à la date: " + facture.getDateFacturation());
            }
            
            // Récupérer le taux de TVA actif du produit
            Optional<ProduitTVA> tvaActive = produitTVARepository
                .findTVAActive(ligne.getProduit().getId(), facture.getDateFacturation());
            
            if (tvaActive.isPresent()) {
                ligne.setTauxTVA(tvaActive.get().getTva().getTaux());
            } else {
                throw new RuntimeException("Aucune TVA active trouvée pour le produit: " + ligne.getProduit().getNom() + " à la date: " + facture.getDateFacturation());
            }
            
            // Figer la désignation du produit
            ligne.setDesignation(ligne.getProduit().getNom());
            
            // Calculer les totaux de la ligne
            ligne.setTotalHT(ligne.getPrixUnitaireHT()
                .multiply(BigDecimal.valueOf(ligne.getQuantite())));
            
            BigDecimal montantTVA = ligne.getTotalHT()
                .multiply(ligne.getTauxTVA())
                .divide(BigDecimal.valueOf(100));
            
            ligne.setTotalTTC(ligne.getTotalHT().add(montantTVA));
            ligne.setFacture(facture);
        }
        
        // Calculer les totaux de la facture
        BigDecimal totalHT = facture.getLignes().stream()
            .map(LigneFacture::getTotalHT)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalTTC = facture.getLignes().stream()
            .map(LigneFacture::getTotalTTC)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        facture.setTotalHT(totalHT);
        facture.setTotalTTC(totalTTC);
        facture.setTotalTVA(totalTTC.subtract(totalHT));
        
        // Sauvegarder la facture (et les lignes en cascade)
        return factureRepository.save(facture);
    }
    
    /**
     * Supprimer une facture
     */
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }
}
