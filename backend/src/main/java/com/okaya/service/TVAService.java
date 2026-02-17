package com.okaya.service;

import com.okaya.model.TVA;
import com.okaya.repository.TVARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des taux de TVA
 */
@Service
public class TVAService {
    
    @Autowired
    private TVARepository tvaRepository;
    
    /**
     * Récupérer tous les taux de TVA
     */
    public List<TVA> getAllTVA() {
        return tvaRepository.findAll();
    }
    
    /**
     * Récupérer un taux de TVA par son ID
     */
    public Optional<TVA> getTVAById(Long id) {
        return tvaRepository.findById(id);
    }
    
    /**
     * Récupérer le taux de TVA actif à une date donnée
     */
    public Optional<TVA> getTVAActive(Date date) {
        return tvaRepository.findTVAActive(date);
    }
    
    /**
     * Créer ou mettre à jour un taux de TVA
     */
    public TVA saveTVA(TVA tva) {
        return tvaRepository.save(tva);
    }
    
    /**
     * Supprimer un taux de TVA
     */
    public void deleteTVA(Long id) {
        tvaRepository.deleteById(id);
    }
}
