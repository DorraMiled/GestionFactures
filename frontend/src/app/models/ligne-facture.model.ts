import { Produit } from './produit.model';


export interface LigneFacture {
  id?: number;
  produit: Produit;
  designation?: string;
  prixUnitaireHT?: number;
  quantite: number;
  tauxTVA?: number;
  totalHT?: number;
  totalTTC?: number;
}
