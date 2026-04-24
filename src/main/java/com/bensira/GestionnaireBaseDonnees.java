package com.bensira;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestionnaire de la base de données SQLite.
 * Gère la connexion au fichier local personnes.db et l'initialisation des tables.
 */
public class GestionnaireBaseDonnees {
    private static final String URL_BASE_DONNEES = "jdbc:sqlite:personnes.db";

    /**
     * Initialise la base de données SQLite.
     * Crée les tables si elles n'existent pas et insère des données de test.
     */
    public static void initialiser() {
        try (Connection connexion = obtenirConnexion();
             Statement instruction = connexion.createStatement()) {
            
            // 1. Activation des clés étrangères (désactivé par défaut dans SQLite)
            instruction.execute("PRAGMA foreign_keys = ON;");

            // 2. Création des tables
            String sqlCreationPersonnes = "CREATE TABLE IF NOT EXISTS personnes (" +
                    "matricule TEXT PRIMARY KEY," +
                    "nom TEXT NOT NULL," +
                    "salaire REAL NOT NULL)";
            
            String sqlCreationFilieres = "CREATE TABLE IF NOT EXISTS filieres (" +
                    "code TEXT PRIMARY KEY," +
                    "designation TEXT NOT NULL)";
            
            String sqlCreationEtudiants = "CREATE TABLE IF NOT EXISTS etudiants (" +
                    "matricule TEXT PRIMARY KEY," +
                    "nom TEXT NOT NULL," +
                    "moyenne REAL NOT NULL," +
                    "code_filiere TEXT NOT NULL," +
                    "FOREIGN KEY (code_filiere) REFERENCES filieres(code))";

            instruction.execute(sqlCreationPersonnes);
            instruction.execute(sqlCreationFilieres);
            instruction.execute(sqlCreationEtudiants);

            // 3. Insertion des données initiales (seulement si elles n'existent pas)

            // 4. Insertion des données initiales
            instruction.execute("INSERT OR IGNORE INTO personnes VALUES ('M001', 'Jean Dupont', 2500.0)");
            instruction.execute("INSERT OR IGNORE INTO personnes VALUES ('M002', 'Marie Curie', 3500.0)");
            instruction.execute("INSERT OR IGNORE INTO personnes VALUES ('M003', 'Pierre Martin', 2200.0)");
            
            instruction.execute("INSERT OR IGNORE INTO filieres VALUES ('INFO', 'Informatique')");
            instruction.execute("INSERT OR IGNORE INTO filieres VALUES ('GE', 'Génie Électrique')");
            
            instruction.execute("INSERT OR IGNORE INTO etudiants VALUES ('E001', 'Ben Sira', 18.5, 'INFO')");
            instruction.execute("INSERT OR IGNORE INTO etudiants VALUES ('E002', 'Alice Smith', 15.0, 'GE')");

            System.out.println("Base de données SQLite initialisée avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation SQLite : " + e.getMessage());
        }
    }

    /**
     * Retourne une connexion à la base de données SQLite.
     */
    public static Connection obtenirConnexion() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite JDBC non trouvé", e);
        }
        return DriverManager.getConnection(URL_BASE_DONNEES);
    }
}
