import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  template: `
    <header>
      <h1>Gestion de Factures</h1>
      
    </header>
      <nav>
      <a routerLink="/clients" routerLinkActive="active">Clients</a>
      <a routerLink="/catalogue" routerLinkActive="active">Catalogue</a>
      <a routerLink="/factures" routerLinkActive="active">Factures</a>
      <a routerLink="/tva" routerLinkActive="active">TVA</a>
    </nav>
  
    
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
  title = 'Okaya';
}
