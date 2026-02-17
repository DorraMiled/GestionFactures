import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

/**
 * Composant pour la gestion des clients
 */
@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clients.component.html'
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  selectedClient: Client | null = null;
  isEditing = false;
  loading = false;
  error = '';

  // Formulaire
  formData: Client = {
    codeClient: '',
    nom: '',
    adresse: '',
    ville: '',
    codePostal: '',
    pays: ''
  };

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClients();
  }

  /**
   * Charger tous les clients
   */
  loadClients(): void {
    this.loading = true;
    this.clientService.getAll().subscribe({
      next: (data) => {
        this.clients = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des clients';
        this.loading = false;
      }
    });
  }

  /**
   * Sauvegarder un client (créer ou modifier)
   */
  saveClient(): void {
    if (this.isEditing && this.formData.id) {
      // Mise à jour
      this.clientService.update(this.formData.id, this.formData).subscribe({
        next: () => {
          this.loadClients();
          this.resetForm();
        },
        error: (err) => this.error = 'Erreur lors de la mise à jour'
      });
    } else {
      // Création
      this.clientService.create(this.formData).subscribe({
        next: () => {
          this.loadClients();
          this.resetForm();
        },
        error: (err) => this.error = 'Erreur lors de la création'
      });
    }
  }

  /**
   * Éditer un client
   */
  editClient(client: Client): void {
    this.formData = { ...client };
    this.isEditing = true;
  }

  /**
   * Supprimer un client
   */
  deleteClient(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
      this.clientService.delete(id).subscribe({
        next: () => this.loadClients(),
        error: (err) => this.error = 'Erreur lors de la suppression'
      });
    }
  }

  /**
   * Réinitialiser le formulaire
   */
  resetForm(): void {
    this.formData = {
      codeClient: '',
      nom: '',
      adresse: '',
      ville: '',
      codePostal: '',
      pays: ''
    };
    this.isEditing = false;
    this.error = '';
  }
}
