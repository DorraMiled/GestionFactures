import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProduitService } from '../../services/produit.service';
import { ProduitPrixService, ProduitPrix } from '../../services/produit-prix.service';
import { ProduitTVAService, ProduitTVA } from '../../services/produit-tva.service';
import { TvaService } from '../../services/tva.service';
import { Produit } from '../../models/produit.model';
import { TVA } from '../../models/tva.model';

/**
 * Composant pour la gestion complète du catalogue
 * Produits + Prix + TVA
 */
@Component({
  selector: 'app-catalogue',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalogue.component.html'
})
export class CatalogueComponent implements OnInit {
  // Produits
  produits: Produit[] = [];
  selectedProduit: Produit | null = null;
  
  // Prix
  prixList: ProduitPrix[] = [];
  
  // TVA
  tvaList: TVA[] = [];
  produitTvaList: ProduitTVA[] = [];
  
  // Formulaires
  formProduit: Produit = { reference: '', nom: '', description: '' };
  formPrix: any = { prixHT: 0, dateDebut: new Date().toISOString().split('T')[0], dateFin: '' };
  formTVA: any = { tvaId: '', dateDebut: new Date().toISOString().split('T')[0], dateFin: '' };
  
  // États
  isEditingProduit = false;
  showPrixForm = false;
  showTVAForm = false;
  loading = false;
  error = '';

  constructor(
    private produitService: ProduitService,
    private prixService: ProduitPrixService,
    private produitTVAService: ProduitTVAService,
    private tvaService: TvaService
  ) {}

  ngOnInit(): void {
    this.loadProduits();
    this.loadTVA();
  }

  loadProduits(): void {
    this.loading = true;
    this.produitService.getAll().subscribe({
      next: (data) => {
        this.produits = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des produits';
        this.loading = false;
      }
    });
  }

  loadTVA(): void {
    this.tvaService.getAll().subscribe({
      next: (data) => this.tvaList = data,
      error: (err) => console.error(err)
    });
  }

  loadPrixForProduit(produitId: number): void {
    this.prixService.getByProduitId(produitId).subscribe({
      next: (data) => this.prixList = data,
      error: (err) => this.error = 'Erreur lors du chargement des prix'
    });
  }

  loadTVAForProduit(produitId: number): void {
    this.produitTVAService.getByProduitId(produitId).subscribe({
      next: (data) => this.produitTvaList = data,
      error: (err) => this.error = 'Erreur lors du chargement de la TVA'
    });
  }

  selectProduit(produit: Produit): void {
    this.selectedProduit = produit;
    this.loadPrixForProduit(produit.id!);
    this.loadTVAForProduit(produit.id!);
    this.showPrixForm = false;
    this.showTVAForm = false;
  }

  saveProduit(): void {
    if (this.isEditingProduit && this.formProduit.id) {
      this.produitService.update(this.formProduit.id, this.formProduit).subscribe({
        next: () => {
          this.loadProduits();
          this.resetProduitForm();
        },
        error: (err) => this.error = 'Erreur lors de la mise à jour'
      });
    } else {
      this.produitService.create(this.formProduit).subscribe({
        next: () => {
          this.loadProduits();
          this.resetProduitForm();
        },
        error: (err) => this.error = 'Erreur lors de la création'
      });
    }
  }

  editProduit(produit: Produit): void {
    this.formProduit = { ...produit };
    this.isEditingProduit = true;
    this.selectedProduit = null;
  }

  deleteProduit(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
      this.produitService.delete(id).subscribe({
        next: () => {
          this.loadProduits();
          if (this.selectedProduit?.id === id) {
            this.selectedProduit = null;
          }
        },
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  savePrix(): void {
    if (!this.selectedProduit) return;

    const prix: ProduitPrix = {
      produit: { id: this.selectedProduit.id },
      prixHT: this.formPrix.prixHT,
      dateDebut: this.formPrix.dateDebut,
      dateFin: this.formPrix.dateFin || null
    };

    this.prixService.create(prix).subscribe({
      next: () => {
        this.loadPrixForProduit(this.selectedProduit!.id!);
        this.resetPrixForm();
        this.showPrixForm = false;
      },
      error: (err) => this.error = 'Erreur lors de l\'ajout du prix'
    });
  }

  saveProduitTVA(): void {
    if (!this.selectedProduit || !this.formTVA.tvaId) return;

    const tva = this.tvaList.find(t => t.id == this.formTVA.tvaId);
    if (!tva) return;

    const produitTVA: ProduitTVA = {
      produit: { id: this.selectedProduit.id },
      tva: tva,
      dateDebut: this.formTVA.dateDebut,
      dateFin: this.formTVA.dateFin || null
    };

    this.produitTVAService.create(produitTVA).subscribe({
      next: () => {
        this.loadTVAForProduit(this.selectedProduit!.id!);
        this.resetTVAForm();
        this.showTVAForm = false;
      },
      error: (err) => this.error = 'Erreur lors de l\'ajout de la TVA'
    });
  }

  deletePrix(id: number): void {
    if (confirm('Supprimer ce prix ?')) {
      this.prixService.delete(id).subscribe({
        next: () => this.loadPrixForProduit(this.selectedProduit!.id!),
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  deleteProduitTVA(id: number): void {
    if (confirm('Supprimer cette TVA ?')) {
      this.produitTVAService.delete(id).subscribe({
        next: () => this.loadTVAForProduit(this.selectedProduit!.id!),
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  resetProduitForm(): void {
    this.formProduit = { reference: '', nom: '', description: '' };
    this.isEditingProduit = false;
    this.error = '';
  }

  resetPrixForm(): void {
    this.formPrix = { prixHT: 0, dateDebut: new Date().toISOString().split('T')[0], dateFin: '' };
  }

  resetTVAForm(): void {
    this.formTVA = { tvaId: '', dateDebut: new Date().toISOString().split('T')[0], dateFin: '' };
  }
}
