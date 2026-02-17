import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 *  pour les prix de produits
 */
export interface ProduitPrix {
  id?: number;
  produit: any;
  prixHT: number;
  dateDebut: Date | string;
  dateFin?: Date | string;
}

/**
 * Service pour gérer les prix des produits
 */
@Injectable({
  providedIn: 'root'
})
export class ProduitPrixService {
  private apiUrl = 'http://localhost:8080/api/produits-prix';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProduitPrix[]> {
    return this.http.get<ProduitPrix[]>(this.apiUrl);
  }

  getByProduitId(produitId: number): Observable<ProduitPrix[]> {
    return this.http.get<ProduitPrix[]>(`${this.apiUrl}/produit/${produitId}`);
  }

  getPrixActif(produitId: number, date: string): Observable<ProduitPrix> {
    return this.http.get<ProduitPrix>(`${this.apiUrl}/actif/${produitId}/${date}`);
  }

  create(prix: ProduitPrix): Observable<ProduitPrix> {
    return this.http.post<ProduitPrix>(this.apiUrl, prix);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
