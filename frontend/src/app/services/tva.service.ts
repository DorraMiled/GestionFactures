import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TVA } from '../models/tva.model';

/**
 *  pour la gestion de la TVA
 */
@Injectable({
  providedIn: 'root'
})
export class TvaService {
  private apiUrl = 'http://localhost:8080/api/tva';

  constructor(private http: HttpClient) {}

  /**
   * Récupérer tous les taux de TVA
   */
  getAll(): Observable<TVA[]> {
    return this.http.get<TVA[]>(this.apiUrl);
  }

  /**
   * Récupérer un taux de TVA par ID
   */
  getById(id: number): Observable<TVA> {
    return this.http.get<TVA>(`${this.apiUrl}/${id}`);
  }

  /**
   * Créer un nouveau taux de TVA
   */
  create(tva: TVA): Observable<TVA> {
    return this.http.post<TVA>(this.apiUrl, tva);
  }

  /**
   * Supprimer un taux de TVA
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
