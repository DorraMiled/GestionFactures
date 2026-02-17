# Application Okaya - Gestion de Factures

Application web complète pour la gestion de factures avec:
- **Backend**: Spring Boot + PostgreSQL
- **Frontend**: Angular

## Vue d'ensemble

Cette application permet de gérer des factures en respectant les contraintes suivantes:

1. ✅ Une facture appartient à un seul client
2. ✅ Une facture contient plusieurs lignes
3. ✅ Une ligne de facture correspond à un produit du catalogue
4. ✅ **Une facture doit rester figée dans le temps**
5. ✅ TVA est définie par période (début et fin)
6. ✅ Le nom d'un produit peut changer
7. ✅ Le prix peut changer
8. ✅ Le taux de TVA peut changer

## Architecture

```
okaya/
├── backend/          # Spring Boot (Java 17)
│   ├── src/
│   │   └── main/
│   │       ├── java/com/okaya/
│   │       │   ├── model/        # Entités JPA
│   │       │   ├── repository/   # Repositories
│   │       │   ├── service/      # Services métier
│   │       │   └── controller/   # Controllers REST
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
└── frontend/         # Angular 17
    ├── src/
    │   └── app/
    │       ├── components/       # Interface utilisateur
    │       ├── models/          # Modèles 
    │       └── services/        # Services HTTP
    └── package.json
```

## Prérequis

### Backend
- Java 17 ou supérieur
- Maven 3.6+
- PostgreSQL 12+

### Frontend
- Node.js 18+
- npm 9+
- Angular CLI 17

## Installation et démarrage

### 1. Configurer la base de données

Créer une base de données PostgreSQL:


**Identifiants configurés:**
- Username: `postgres`
- Password: `mdp`
- Port: `tonport`

### 2. Lancer le backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Le backend démarre sur: **http://localhost:8080**

### 3. Lancer le frontend

```bash
cd frontend
npm install
npm start
```

Le frontend démarre sur: **http://localhost:4200**


## Modèle de données

### Entités principales

- **Client**: Informations client (code, nom, adresse...)
- **Produit**: Catalogue de produits (référence, nom, description)
- **Facture**: Facture figée avec totaux calculés
- **LigneFacture**: Ligne de facture avec prix et TVA figés
- **ProduitPrix**: Historique des prix par période
- **TVA**: Taux de TVA par période
- **ProduitTVA**: Association produit-TVA par période

### Principe de figement

Quand une facture est créée:
1. Le prix actuel du produit est récupéré et figé dans la ligne
2. Le taux de TVA actuel est récupéré et figé dans la ligne
3. Le nom du produit est copié dans la désignation
4. Les totaux sont calculés et stockés

**Résultat**: La facture reste inchangée même si les prix, TVA ou noms changent.

## API REST

### Endpoints principaux

**Clients**
- `GET    /api/clients` - Liste
- `POST   /api/clients` - Créer
- `PUT    /api/clients/{id}` - Modifier
- `DELETE /api/clients/{id}` - Supprimer

**Produits**
- `GET    /api/produits` - Liste
- `POST   /api/produits` - Créer
- `PUT    /api/produits/{id}` - Modifier
- `DELETE /api/produits/{id}` - Supprimer

**Factures**
- `GET    /api/factures` - Liste
- `POST   /api/factures` - Créer 
- `DELETE /api/factures/{id}` - Supprimer

**TVA**
- `GET    /api/tva` - Liste
- `POST   /api/tva` - Créer

## Interface utilisateur

Interface minimaliste et intuitive avec:
- Navigation par sections (Clients, Catalogue, Factures, TVA)
- Formulaires simples et clairs
- Tableaux de données lisibles
- Design sobre 

### Pages disponibles

1. **Clients**: CRUD complet des clients
2. **Catalogue**: Gestion complète du catalogue (produits + prix + TVA avec périodes)
3. **Factures**: Création de factures multi-lignes avec récupération automatique des prix
4. **Facture Détail**: Consultation d'une facture avec montants figés (aucun recalcul)
5. **TVA**: Gestion des taux de TVA par période

### Fonctionnalités détaillées

#### 1️⃣ Gestion des clients
- Créer un client avec toutes ses informations
- Lister tous les clients
- Modifier un client existant
- Supprimer un client

#### 2️⃣ Gestion du catalogue
**Interface unifiée pour gérer:**
- Création de produits (référence, nom, description)
- Ajout de prix avec périodes (prix HT, date début, date fin optionnelle)
- Association de taux de TVA avec périodes (sélection TVA, date début, date fin)





#### 3️⃣ Création d'une facture
**Processus automatisé:**
1. Sélectionner un client
2. Définir la date de facturation
3. Sélectionner un produit → **Le prix actif à cette date s'affiche automatiquement**
4. Ajouter la quantité
5. Cliquer sur "Ajouter" 
6. Répéter pour d'autres lignes
7. Créer la facture → **Tous les montants sont calculés et figés**

**Affichage en temps réel:**
- Message : "✓ Prix actuel : 100.00 € HT (du 01/01/2024 au 31/12/2024)"
- Si aucun prix actif : "Aucun prix actif trouvé pour ce produit à cette date"
- Bouton "Ajouter" désactivé tant qu'aucun prix actif n'est disponible

#### 4️⃣ Consultation d'une facture
**Affichage des données figées:**
- Informations client complètes
- Dates de facturation et d'échéance
- **Désignations figées** (nom du produit au moment de la création)
- **Prix figés** (prix actif au moment de la création)
- **Taux de TVA figés** (taux actif au moment de la création)
- **Totaux figés** (HT, TVA, TTC calculés une seule fois)



## Technologies

### Backend
- Spring Boot 3.2.0
- Java 17
- PostgreSQL
- Hibernate/JPA
- Lombok
- Maven

### Frontend
- Angular 17
- TypeScript
- RxJS
- CSS 



