import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Facture } from '../models/facture.model';

/**
 *  pour la gestion des factures
 */
@Injectable({
  providedIn: 'root'
})
export class FactureService {
  private apiUrl = 'http://localhost:8080/api/factures';

  constructor(private http: HttpClient) {}

  /**
   * Récupérer toutes les factures
   */
  getAll(): Observable<Facture[]> {
    return this.http.get<Facture[]>(this.apiUrl);
  }

  /**
   * Récupérer une facture par ID
   */
  getById(id: number): Observable<Facture> {
    return this.http.get<Facture>(`${this.apiUrl}/${id}`);
  }

  /**
   * Récupérer toutes les factures d'un client
   */
  getByClientId(clientId: number): Observable<Facture[]> {
    return this.http.get<Facture[]>(`${this.apiUrl}/client/${clientId}`);
  }

  /**
   * Créer une nouvelle facture
   */
  create(facture: Facture): Observable<Facture> {
    return this.http.post<Facture>(this.apiUrl, facture);
  }

  /**
   * Supprimer une facture
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
