import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TVA } from '../models/tva.model';

/**
 * Interface pour l'association Produit-TVA
 */
export interface ProduitTVA {
  id?: number;
  produit: any;
  tva: TVA;
  dateDebut: Date | string;
  dateFin?: Date | string;
}

/**
 *  pour gérer la TVA des produits
 */
@Injectable({
  providedIn: 'root'
})
export class ProduitTVAService {
  private apiUrl = 'http://localhost:8080/api/produits-tva';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProduitTVA[]> {
    return this.http.get<ProduitTVA[]>(this.apiUrl);
  }

  getByProduitId(produitId: number): Observable<ProduitTVA[]> {
    return this.http.get<ProduitTVA[]>(`${this.apiUrl}/produit/${produitId}`);
  }

  create(produitTVA: ProduitTVA): Observable<ProduitTVA> {
    return this.http.post<ProduitTVA>(this.apiUrl, produitTVA);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
