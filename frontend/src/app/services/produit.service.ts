import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit.model';

/**
 *  pour la gestion des produits
 */
@Injectable({
  providedIn: 'root'
})
export class ProduitService {
  private apiUrl = 'http://localhost:8080/api/produits';

  constructor(private http: HttpClient) {}

  /**
   * Récupérer tous les produits
   */
  getAll(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.apiUrl);
  }

  /**
   * Récupérer un produit par ID
   */
  getById(id: number): Observable<Produit> {
    return this.http.get<Produit>(`${this.apiUrl}/${id}`);
  }

  /**
   * Créer un nouveau produit
   */
  create(produit: Produit): Observable<Produit> {
    return this.http.post<Produit>(this.apiUrl, produit);
  }

  /**
   * Mettre à jour un produit
   */
  update(id: number, produit: Produit): Observable<Produit> {
    return this.http.put<Produit>(`${this.apiUrl}/${id}`, produit);
  }

  /**
   * Supprimer un produit
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
