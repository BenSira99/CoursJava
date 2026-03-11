# Javacours — Swing Navigation

## 📋 Description

Projet de démonstration de navigation entre JFrames en Java Swing, utilisant des constructeurs pour une initialisation directe et simplifiée.

## 🚀 Fonctionnalités

- Menu principal avec navigation vers "Informations" et "Traitement".
- Boutons "Retour" pour une navigation fluide.
- Fermeture sécurisée de l'application.

## 🛠️ Stack Technique

- Java 17+
- Swing / AWT
- Maven (Gestion de projet)

## 📦 Prérequis

- Java JDK 17+
- Maven 3.6+

## ⚙️ Installation

```bash
git clone <url-du-repo>
cd javacours
mvn install
```

## 🔧 Configuration (.env)

Aucune variable d'environnement requise pour ce projet Swing local.

## 🏃 Lancement

Pour lancer le menu principal :

```bash
mvn exec:java -Dexec.mainClass="com.bensiratechnology.javacours.FenetrePrincipale"
```

### Méthode de secours (Sans Maven)

Si la commande `mvn` n'est pas reconnue sur votre système, utilisez ces commandes PowerShell :

```powershell
# Création du dossier target/classes si inexistant
mkdir -Force target/classes

# Compilation manuelle
javac -d target/classes src/main/java/com/bensiratechnology/javacours/*.java

# Exécution
java -cp target/classes com.bensiratechnology.javacours.FenetrePrincipale
```

Ou via Java directement après compilation :

```bash
mvn compile
java -cp target/classes com.bensiratechnology.javacours.FenetrePrincipale
```

## 🧪 Tests

Tests unitaires via JUnit 4 (inclus dans la structure Maven).

```bash
mvn test
```

## 🐳 Docker

Non applicable pour ce projet UI de bureau.

## 📁 Structure du Projet

```text
src/main/java/com/bensiratechnology/javacours/
├── FenetrePrincipale.java   # Menu et navigation
├── FenetreInformation.java  # Vue informations
└── FenetreTraitement.java   # Vue traitement
```

## 🔒 Sécurité

- Initialisation propre dans les constructeurs.
- Utilisation de `DISPOSE_ON_CLOSE` pour les fenêtres secondaires.

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👤 Auteur — BenSira99
