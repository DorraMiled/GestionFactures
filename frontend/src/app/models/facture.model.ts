import { Client } from './client.model';
import { LigneFacture } from './ligne-facture.model';


export interface Facture {
  id?: number;
  reference: string;
  dateFacturation: Date;
  dateEcheance?: Date;
  client: Client;
  lignes: LigneFacture[];
  totalHT?: number;
  totalTVA?: number;
  totalTTC?: number;
}
