import { Routes } from '@angular/router';
import { ClientsComponent } from './components/clients/clients.component';
import { CatalogueComponent } from './components/catalogue/catalogue.component';
import { FacturesComponent } from './components/factures/factures.component';
import { FactureDetailComponent } from './components/facture-detail/facture-detail.component';
import { TvaComponent } from './components/tva/tva.component';

export const routes: Routes = [
  { path: '', redirectTo: '/clients', pathMatch: 'full' },
  { path: 'clients', component: ClientsComponent },
  { path: 'catalogue', component: CatalogueComponent },
  { path: 'factures', component: FacturesComponent },
  { path: 'factures/:id', component: FactureDetailComponent },
  { path: 'tva', component: TvaComponent }
];
