# Dépendances Spring Boot - Projet Okaya

## Dépendances Maven à installer

Toutes ces dépendances sont définies dans le fichier `backend/pom.xml`

### 1. Spring Boot Starter Web
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
**Utilité**: Permet de créer des APIs REST. Inclut Spring MVC, Tomcat embarqué, et Jackson pour JSON.

### 2. Spring Boot Starter Data JPA
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
**Utilité**: Gestion de la persistance des données avec JPA/Hibernate. Permet de mapper les entités Java aux tables SQL.

### 3. PostgreSQL Driver
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```
**Utilité**: Driver JDBC pour se connecter à la base de données PostgreSQL.

### 4. Spring Boot Starter Validation
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
**Utilité**: Validation des données avec les annotations `@NotNull`, `@NotEmpty`, etc.

### 5. Lombok
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
**Utilité**: Réduit le code boilerplate (getters, setters, constructeurs). Utilise les annotations `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`.

### 6. Spring Boot DevTools
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```
**Utilité**: Rechargement automatique de l'application lors des modifications (développement uniquement).

## Version Spring Boot

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>
```

## Version Java

```xml
<properties>
    <java.version>17</java.version>
</properties>
```

## Installation des dépendances

Les dépendances sont automatiquement téléchargées lors de la première compilation:

```bash
cd backend
mvn clean install
```

Maven téléchargera toutes les dépendances depuis Maven Central.

## Dépendances transitives incluses

Chaque dépendance Spring Boot inclut automatiquement d'autres bibliothèques:

**spring-boot-starter-web** inclut:
- Spring MVC
- Tomcat (serveur embarqué)
- Jackson (sérialisation JSON)
- Spring Boot Autoconfigure

**spring-boot-starter-data-jpa** inclut:
- Hibernate ORM
- Spring Data JPA
- JDBC
- Transaction management

## Versions utilisées

Toutes les versions sont gérées par Spring Boot parent:
- Spring Framework: 6.1.x
- Hibernate: 6.4.x
- Jackson: 2.15.x
- Tomcat: 10.1.x

## Configuration minimale requise

Fichier `application.properties`:
```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/okaya_db
spring.datasource.username=postgres
spring.datasource.password=12345

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

C'est tout! Spring Boot configure automatiquement le reste.
