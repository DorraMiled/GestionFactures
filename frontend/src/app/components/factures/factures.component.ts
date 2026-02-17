import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FactureService } from '../../services/facture.service';
import { ClientService } from '../../services/client.service';
import { ProduitService } from '../../services/produit.service';
import { ProduitPrixService } from '../../services/produit-prix.service';
import { Facture } from '../../models/facture.model';
import { Client } from '../../models/client.model';
import { Produit } from '../../models/produit.model';
import { LigneFacture } from '../../models/ligne-facture.model';
import { RouterLink } from '@angular/router';

/**
 * Composant pour la gestion des factures
 */
@Component({
  selector: 'app-factures',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './factures.component.html'
})
export class FacturesComponent implements OnInit {
  factures: Facture[] = [];
  clients: Client[] = [];
  produits: Produit[] = [];
  loading = false;
  error = '';
  showForm = false;

  // Formulaire
  formData: any = {
    reference: '',
    dateFacturation: new Date().toISOString().split('T')[0],
    dateEcheance: '',
    clientId: '',
    lignes: []
  };

  // Ligne de facture en cours
  currentLigne: any = {
    produitId: '',
    quantite: 1,
    prixInfo: null  // Informations sur le prix actif
  };

  constructor(
    private factureService: FactureService,
    private clientService: ClientService,
    private produitService: ProduitService,
    private prixService: ProduitPrixService
  ) {}

  ngOnInit(): void {
    this.loadFactures();
    this.loadClients();
    this.loadProduits();
  }

  loadFactures(): void {
    this.loading = true;
    this.factureService.getAll().subscribe({
      next: (data) => {
        this.factures = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des factures';
        this.loading = false;
      }
    });
  }

  loadClients(): void {
    this.clientService.getAll().subscribe({
      next: (data) => this.clients = data,
      error: (err) => console.error(err)
    });
  }

  loadProduits(): void {
    this.produitService.getAll().subscribe({
      next: (data) => this.produits = data,
      error: (err) => console.error(err)
    });
  }

  /**
   * Charger le prix actif pour un produit à une date donnée
   */
  loadPrixActif(): void {
    if (this.currentLigne.produitId && this.formData.dateFacturation) {
      this.prixService.getPrixActif(
        this.currentLigne.produitId,
        this.formData.dateFacturation
      ).subscribe({
        next: (prix) => {
          this.currentLigne.prixInfo = prix;
        },
        error: (err) => {
          this.currentLigne.prixInfo = null;
          this.error = 'Aucun prix actif trouvé pour ce produit à cette date';
        }
      });
    }
  }

  /**
   * Ajouter une ligne à la facture
   */
  addLigne(): void {
    if (this.currentLigne.produitId && this.currentLigne.quantite > 0) {
      const produit = this.produits.find(p => p.id == this.currentLigne.produitId);
      if (produit) {
        this.formData.lignes.push({
          produit: produit,
          quantite: this.currentLigne.quantite
        });
        this.currentLigne = { produitId: '', quantite: 1, prixInfo: null };
      }
    }
  }

  /**
   * Supprimer une ligne
   */
  removeLigne(index: number): void {
    this.formData.lignes.splice(index, 1);
  }

  /**
   * Créer une facture
   */
  createFacture(): void {
    if (!this.formData.clientId || this.formData.lignes.length === 0) {
      this.error = 'Veuillez sélectionner un client et ajouter au moins une ligne';
      return;
    }

    // Préparer les données pour l'API (envoyer seulement les IDs)
    const facture: any = {
      reference: this.formData.reference,
      dateFacturation: this.formData.dateFacturation,
      dateEcheance: this.formData.dateEcheance || null,
      client: { id: this.formData.clientId },
      lignes: this.formData.lignes.map((ligne: any) => ({
        produit: { id: ligne.produit.id },
        quantite: ligne.quantite
      }))
    };

    this.factureService.create(facture).subscribe({
      next: () => {
        this.loadFactures();
        this.resetForm();
        this.showForm = false;
      },
      error: (err) => {
        console.error('Erreur complète:', err);
        this.error = 'Erreur lors de la création de la facture: ' + (err.error?.message || err.message || 'Erreur inconnue');
      }
    });
  }

  /**
   * Supprimer une facture
   */
  deleteFacture(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette facture ?')) {
      this.factureService.delete(id).subscribe({
        next: () => this.loadFactures(),
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  /**
   * Réinitialiser le formulaire
   */
  resetForm(): void {
    this.formData = {
      reference: '',
      dateFacturation: new Date().toISOString().split('T')[0],
      dateEcheance: '',
      clientId: '',
      lignes: []
    };
    this.currentLigne = { produitId: '', quantite: 1, prixInfo: null };
    this.error = '';
  }

  /**
   * Obtenir le nom du produit
   */
  getProduitNom(produitId: number): string {
    const produit = this.produits.find(p => p.id == produitId);
    return produit ? produit.nom : 'Inconnu';
  }
}
