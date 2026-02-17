# Application Okaya - Backend

## Description
Application de gestion de factures avec Spring Boot et PostgreSQL.

## Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- PostgreSQL 12+

## Configuration de la base de données

1. Créer une base de données PostgreSQL

2. Les identifiants configurés sont:
   - Username: `postgres`
   - Password: `yourpass`
   - Port: `yourport`

## Dépendances Maven

Les dépendances principales utilisées:

- **spring-boot-starter-web** : Pour créer les APIs REST
- **spring-boot-starter-data-jpa** : Pour la persistance des données
- **postgresql** : Driver PostgreSQL
- **spring-boot-starter-validation** : Pour la validation
- **lombok** : Pour réduire le code boilerplate
- **spring-boot-devtools** : Pour le développement (rechargement automatique)

## Installation et démarrage

1. Naviguer dans le dossier backend:
```bash
cd backend
```

2. Compiler le projet:
```bash
mvn clean install
```

3. Lancer l'application:
```bash
mvn spring-boot:run
```

L'application démarre sur le port 8080: http://localhost:8080

## Structure du projet

```
backend/
├── src/main/java/com/okaya/
│   ├── model/           # Entités JPA
│   ├── repository/      # Repositories JPA
│   ├── service/         # Services métier
│   ├── controller/      # Controllers REST
│   └── OkayaApplication.java
└── src/main/resources/
    └── application.properties
```

## Endpoints API

### Clients
- GET    `/api/clients` - Liste tous les clients
- GET    `/api/clients/{id}` - Récupère un client
- POST   `/api/clients` - Crée un client
- PUT    `/api/clients/{id}` - Met à jour un client
- DELETE `/api/clients/{id}` - Supprime un client

### Produits
- GET    `/api/produits` - Liste tous les produits
- GET    `/api/produits/{id}` - Récupère un produit
- POST   `/api/produits` - Crée un produit
- PUT    `/api/produits/{id}` - Met à jour un produit
- DELETE `/api/produits/{id}` - Supprime un produit

### Factures
- GET    `/api/factures` - Liste toutes les factures
- GET    `/api/factures/{id}` - Récupère une facture
- GET    `/api/factures/client/{clientId}` - Factures d'un client
- POST   `/api/factures` - Crée une facture (figée automatiquement)
- DELETE `/api/factures/{id}` - Supprime une facture

### Prix produits
- GET    `/api/produits-prix` - Liste tous les prix
- GET    `/api/produits-prix/produit/{produitId}` - Prix d'un produit
- POST   `/api/produits-prix` - Crée un prix

### TVA
- GET    `/api/tva` - Liste tous les taux de TVA
- POST   `/api/tva` - Crée un taux de TVA

## Hypothèses implémentées

1.  Une facture appartient à un seul client
2.  Une facture contient plusieurs lignes
3.  Une ligne de facture correspond à un produit du catalogue
4.  Une facture doit rester figée dans le temps
5.  TVA est définie par période (début et fin)
6.  Le nom d'un produit peut changer
7.  Le prix peut changer
8.  Le taux de TVA peut changer
