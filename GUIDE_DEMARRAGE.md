# Guide de Démarrage Rapide - Okayo

## Étapes d'installation et de lancement

### 1. Prérequis à installer

#### PostgreSQL
- Installer avec le mot de passe `mdp` pour l'utilisateur `postgres`
- Port par défaut: `5432`

#### Java 17
- Installer et vérifier: `java -version`

#### Maven
- Ajouter au PATH
- Vérifier: `mvn -version`


#### Angular CLI
```bash
npm install -g @angular/cli@17
```

### 2. Créer la base de données


### 3. Lancer le backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Le backend sera accessible sur: **http://localhost:8080**

### 4. Lancer le frontend

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

## Ports utilisés

- Backend: **8080**
- Frontend: **4200**
- PostgreSQL: **5432**


