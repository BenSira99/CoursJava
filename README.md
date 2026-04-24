# Système de Gestion Scolaire

## 📋 Description
Application Java de gestion académique avancée permettant de piloter les étudiants et les filières. Le système utilise une architecture modulaire avec **Swing**, **CardLayout** et une persistance locale robuste via **SQLite**.

## 🚀 Fonctionnalités
- **Gestion Complète des Filières** : 
  - Création avec vérification d'unicité (Code/Désignation).
  - Modification dynamique via sélection en liste déroulante.
  - Suppression flexible (par Code ou par Désignation).
- **Gestion des Étudiants** : 
  - Inscription complète avec association aux filières existantes.
  - Modification interactive avec chargement automatique des données.
  - Suppression sécurisée avec confirmation (par Matricule ou par Nom).
- **Interface Utilisateur Premium** : 
  - Barre de menu (`JMenuBar`) professionnelle et hiérarchisée.
  - Navigation fluide via `CardLayout`.
  - Page d'accueil interactive.
- **Persistance SQLite** : Gestion optimisée des données dans `personnes.db` avec protection contre l'écrasement au démarrage.

## 🛠️ Stack Technique
- **Langage** : Java 21+
- **Interface** : Java Swing (CardLayout, JMenuBar)
- **Base de Données** : SQLite JDBC Driver (3.45.1.0)
- **Build System** : Maven

## 📦 Prérequis
- Java JDK 21 ou supérieur.
- Maven installé et configuré.

## ⚙️ Installation
```bash
git clone https://github.com/BenSira99/CoursJava.git
cd JavaStandard
```

## 🏃 Lancement
```bash
mvn compile exec:java -Dexec.mainClass="com.bensira.FenetreScolarite"
```

## 🧪 Tests
Les tests fonctionnels sont intégrés à l'interface graphique. La classe `GestionnaireBaseDonnees` assure l'intégrité des schémas.

## 📁 Structure du Projet
```text
src/main/java/com/bensira/
├── FenetreScolarite.java        (Interface Principale Swing)
├── ServiceScolarite.java        (Logique Métier & Requêtes SQL)
├── GestionnaireBaseDonnees.java (Moteur de Persistance SQLite)
└── ApplicationScolarite.java    (Point d'entrée alternatif)
```

## 🔒 Sécurité
- Prévention des doublons au niveau applicatif et base de données.
- Boîtes de dialogue de confirmation pour les actions critiques.
- Gestion des ressources JDBC avec try-with-resources.

## 📄 Licence
Ce projet est sous licence MIT.

## 👤 Auteur — BenSira99
Ingénieur logiciel Senior Full-Stack.
- GitHub: [@BenSira99](https://github.com/BenSira99)
