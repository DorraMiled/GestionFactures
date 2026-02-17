import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TvaService } from '../../services/tva.service';
import { TVA } from '../../models/tva.model';

/**
 * Composant pour la gestion de la TVA
 */
@Component({
  selector: 'app-tva',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tva.component.html'
})
export class TvaComponent implements OnInit {
  tvaList: TVA[] = [];
  loading = false;
  error = '';

  // Formulaire
  formData: TVA = {
    taux: 0,
    dateDebut: new Date(),
    dateFin: undefined
  };

  constructor(private tvaService: TvaService) {}

  ngOnInit(): void {
    this.loadTVA();
  }

  /**
   * Charger tous les taux de TVA
   */
  loadTVA(): void {
    this.loading = true;
    this.tvaService.getAll().subscribe({
      next: (data) => {
        this.tvaList = data;
        console.log('TVA chargées:', this.tvaList);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des taux de TVA';
        this.loading = false;
      }
    });
  }

  /**
   * Créer un taux de TVA
   */
  createTVA(): void {
    // Transformer dateFin vide en undefined
    const tvaData: TVA = {
      ...this.formData,
      dateFin: this.formData.dateFin ? this.formData.dateFin : undefined
    };
    
    this.tvaService.create(tvaData).subscribe({
      next: () => {
        this.loadTVA();
        this.resetForm();
      },
      error: (err) => this.error = 'Erreur lors de la création'
    });
  }

  /**
   * Supprimer un taux de TVA
   */
  deleteTVA(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce taux de TVA ?')) {
      this.tvaService.delete(id).subscribe({
        next: () => this.loadTVA(),
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  /**
   * Réinitialiser le formulaire
   */
  resetForm(): void {
    this.formData = {
      taux: 0,
      dateDebut: new Date(),
      dateFin: undefined
    };
    this.error = '';
  }

  /**
   * Vérifier si un taux de TVA est actif
   */
  isActif(tva: TVA): boolean {
    if (!tva.dateFin) {
      console.log('TVA', tva.id, 'pas de dateFin -> Actif');
      return true; // Pas de date de fin = actif indéfiniment
    }
    
    const dateFin = new Date(tva.dateFin);
    const aujourd_hui = new Date();
    aujourd_hui.setHours(0, 0, 0, 0); // Réinitialiser l'heure pour comparer juste les dates
    dateFin.setHours(0, 0, 0, 0);
    
    const result = dateFin >= aujourd_hui;
    console.log('TVA', tva.id, 'dateFin:', dateFin.toISOString().split('T')[0], 'aujourd\'hui:', aujourd_hui.toISOString().split('T')[0], 'isActif:', result);
    return result;
  }
}
