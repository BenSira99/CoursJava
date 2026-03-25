# JavaStandard

## 📋 Description
Application Java Swing moderne pour la gestion des collaborateurs (Personnes) avec stockage persistant SQLite et style visuel Teal (Sarcelle). Elle implémente un tableau de bord complet pour les opérations CRUD.


## 🚀 Fonctionnalités

- **Dashboard Central** : Interface avec `JTable` pour visualiser tous les collaborateurs.
- **Gestion SQLite** : Persistance des données (Nom, Matricule, Salaire) via JDBC.
- **Insertion & Modification** : Fenêtres de saisie dédiées avec validation.
- **Suppression Sécurisée** : Confirmation de suppression avec feedback utilisateur.
- **Design Teal** : Palette de couleurs moderne intégrée directement dans les composants Swing.


## 🛠️ Stack Technique

- **Language**: Java 21
- **Framework**: Swing (standard library)
- **Build System**: Maven 3.9+
- **Architecture**: MVC (Model-View-Controller) principles

## 📦 Prerequis
- JDK 21 or higher.
- Maven installed.

## ⚙️ Installation
```bash
git clone https://github.com/BenSira99/CoursJava.git
cd JavaStandard
```

## run Lancement
```bash
mvn compile exec:java -Dexec.mainClass="com.bensira.FenetrePrincipale"
```

## 🧪 Tests
No unit tests implemented yet.

## 📁 Structure du Projet
```text
JavaStandard/
├── src/main/java/com/bensira/
│   ├── FenetrePrincipale.java  (Main Entry)
│   ├── FenetreInformation.java (Information window)
│   ├── FenetreTraitement.java  (Processing window)
│   └── Main.java               (Placeholder main)
├── pom.xml                     (Maven Config)
└── README.md                   (Documentation)
```

## 🔒 Sécurité
- Code-level input validation (to be expanded).
- Secure window disposal management.

## 📄 Licence
Distributed under the MIT License. See `LICENSE` for more information.

## 👤 Auteur — BenSira99
- GitHub: [@BenSira99](https://github.com/BenSira99)
