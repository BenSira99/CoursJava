# Système de Gestion Scolaire

## 📋 Description
Application Java de gestion académique permettant de gérer les étudiants et les filières. Le système utilise une architecture simplifiée (AWT Container & Swing) et une persistance locale avec SQLite.

## 🚀 Fonctionnalités
- **Gestion des Filières** : Création avec vérification d'existence (Code/Désignation).
- **Gestion des Étudiants** : Inscription avec calcul de moyenne et association à une filière via `JComboBox`.
- **Interface Dynamique** : Navigation par boutons dans la barre de menu et basculement manuel du ContentPane.
- **Persistance SQLite** : Toutes les données sont stockées dans `personnes.db`.

## 🛠️ Stack Technique
- **Langage** : Java 21+
- **Interface** : AWT Container / Swing Components
- **Base de Données** : SQLite JDBC Specialist
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
Le programme de test principal se trouve dans `ApplicationScolarite.java`.

## 📁 Structure du Projet
```text
src/main/java/com/bensira/
├── FenetreScolarite.java        (Interface Principale)
├── ServiceScolarite.java        (Logique Métier & SQL)
├── GestionnaireBaseDonnees.java (Configuration SQLite)
└── ApplicationScolarite.java    (Tests & Initialisation)
```

## 🔒 Sécurité
- Validation des entrées côté client.
- Détection automatique des doublons de filières.
- Gestion sécurisée des connexions JDBC.

## 📄 Licence
Ce projet est sous licence MIT.

## 👤 Auteur — BenSira99
Ingénieur logiciel Senior Full-Stack.
- GitHub: [@BenSira99](https://github.com/BenSira99)
