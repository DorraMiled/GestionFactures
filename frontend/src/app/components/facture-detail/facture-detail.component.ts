import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FactureService } from '../../services/facture.service';
import { Facture } from '../../models/facture.model';

/**
 * Composant de consultation d'une facture
 * Les montants affichés sont figés (stockés lors de la création)
 * AUCUN RECALCUL n'est effectué
 */
@Component({
  selector: 'app-facture-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './facture-detail.component.html'
})
export class FactureDetailComponent implements OnInit {
  facture: Facture | null = null;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private factureService: FactureService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadFacture(+id);
    }
  }

  loadFacture(id: number): void {
    this.loading = true;
    this.factureService.getById(id).subscribe({
      next: (data) => {
        this.facture = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement de la facture';
        this.loading = false;
      }
    });
  }

  print(): void {
    window.print();
  }
}
