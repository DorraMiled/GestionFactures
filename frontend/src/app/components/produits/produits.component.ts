import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/produit.model';

/**
 * Composant pour la gestion des produits
 */
@Component({
  selector: 'app-produits',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './produits.component.html'
})
export class ProduitsComponent implements OnInit {
  produits: Produit[] = [];
  isEditing = false;
  loading = false;
  error = '';

  // Formulaire
  formData: Produit = {
    reference: '',
    nom: '',
    description: ''
  };

  constructor(private produitService: ProduitService) {}

  ngOnInit(): void {
    this.loadProduits();
  }

  /**
   * Charger tous les produits
   */
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

  /**
   * Sauvegarder un produit
   */
  saveProduit(): void {
    if (this.isEditing && this.formData.id) {
      this.produitService.update(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadProduits();
          this.resetForm();
        },
        error: (err) => this.error = 'Erreur lors de la mise à jour'
      });
    } else {
      this.produitService.create(this.formData).subscribe({
        next: () => {
          this.loadProduits();
          this.resetForm();
        },
        error: (err) => this.error = 'Erreur lors de la création'
      });
    }
  }

  /**
   * Éditer un produit
   */
  editProduit(produit: Produit): void {
    this.formData = { ...produit };
    this.isEditing = true;
  }

  /**
   * Supprimer un produit
   */
  deleteProduit(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
      this.produitService.delete(id).subscribe({
        next: () => this.loadProduits(),
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
      nom: '',
      description: ''
    };
    this.isEditing = false;
    this.error = '';
  }
}
