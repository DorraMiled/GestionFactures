# Guide de Démarrage Rapide - Okaya

## Étapes d'installation et de lancement

### 1. Prérequis à installer

#### PostgreSQL
- Télécharger: https://www.postgresql.org/download/windows/
- Installer avec le mot de passe `12345` pour l'utilisateur `postgres`
- Port par défaut: `5432`

#### Java 17
- Télécharger: https://adoptium.net/
- Installer et vérifier: `java -version`

#### Maven
- Télécharger: https://maven.apache.org/download.cgi
- Ajouter au PATH
- Vérifier: `mvn -version`

#### Node.js
- Télécharger: https://nodejs.org/ (version LTS)
- Installer et vérifier: `node -v` et `npm -v`

#### Angular CLI
```bash
npm install -g @angular/cli@17
```

### 2. Créer la base de données

Ouvrir pgAdmin ou psql et exécuter:
```sql
CREATE DATABASE okaya_db;
```

### 3. Lancer le backend

**Option 1 - Script automatique:**
```bash
start-backend.bat
```

**Option 2 - Manuellement:**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Le backend sera accessible sur: **http://localhost:8080**

### 4. Lancer le frontend

Ouvrir un **nouveau terminal** et exécuter:

**Option 1 - Script automatique:**
```bash
start-frontend.bat
```

**Option 2 - Manuellement:**
```bash
cd frontend
npm install
npm start
```

Le frontend sera accessible sur: **http://localhost:4200**

### 5. Utiliser l'application

1. Ouvrir le navigateur sur http://localhost:4200
2. Créer des clients dans l'onglet "Clients"
3. Créer des produits dans l'onglet "Produits"
4. Ajouter des taux de TVA dans l'onglet "TVA"
5. Créer des factures dans l'onglet "Factures"

### 6. Ajouter des prix et TVA aux produits (via API)

Pour que les factures calculent correctement, il faut:

**Ajouter un prix à un produit:**
```bash
curl -X POST http://localhost:8080/api/produits-prix \
  -H "Content-Type: application/json" \
  -d '{
    "produit": {"id": 1},
    "prixHT": 99.99,
    "dateDebut": "2024-01-01",
    "dateFin": null
  }'
```

**Associer une TVA à un produit:**
```bash
curl -X POST http://localhost:8080/api/produits-tva \
  -H "Content-Type: application/json" \
  -d '{
    "produit": {"id": 1},
    "tva": {"id": 1},
    "dateDebut": "2024-01-01",
    "dateFin": null
  }'
```

Ou utiliser un outil comme **Postman** ou **Insomnia** pour ces requêtes.

## Dépannage

### Le backend ne démarre pas
- Vérifier que PostgreSQL est lancé
- Vérifier que la base `okaya_db` existe
- Vérifier les identifiants dans `application.properties`

### Le frontend ne démarre pas
- Exécuter `npm install` dans le dossier frontend
- Vérifier que le port 4200 est libre
- Vérifier que Node.js est bien installé

### Erreur CORS
- Vérifier que le backend est lancé sur le port 8080
- Vérifier que le frontend est lancé sur le port 4200
- La configuration CORS est déjà en place dans `CorsConfig.java`

### Les totaux de facture sont à 0
- Ajouter des prix aux produits via l'API `/api/produits-prix`
- Ajouter des taux de TVA aux produits via l'API `/api/produits-tva`

## Structure des données

```
Client → Facture → LigneFacture → Produit
                                    ↓
                              ProduitPrix (historique)
                              ProduitTVA → TVA (historique)
```

## Ports utilisés

- Backend: **8080**
- Frontend: **4200**
- PostgreSQL: **5432**

## Arrêt des services

- Backend: `Ctrl + C` dans le terminal
- Frontend: `Ctrl + C` dans le terminal
- PostgreSQL: Via les services Windows ou pgAdmin
