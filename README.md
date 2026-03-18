# JavaStandard

## 📋 Description
JavaStandard is a Java Swing application designed to demonstrate a standard desktop interface with navigation through a menu bar. It provides modules for information display and data processing simulation.

## 🚀 Fonctionnalités
- Main window with a professional `JMenuBar`.
- Information module for displaying static content.
- Treatment module for simulating data processing.
- Dynamic navigation between windows.
- Centralized event handling using `ActionListener`.

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
