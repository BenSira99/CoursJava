package com.bensira;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestionnaire de la base de données SQLite.
 * Gère la connexion et l'initialisation des tables.
 */
public class GestionnaireBaseDonnees {
    private static final String URL_BASE_DONNEES = "jdbc:sqlite:personnes.db";

    /**
     * Initialise la base de données en créant la table Personnes si elle n'existe pas.
     */
    public static void initialiser() {
        try (Connection connexion = obtenirConnexion();
             Statement instruction = connexion.createStatement()) {
            
            // Création de la table
            String sqlCreationTable = "CREATE TABLE IF NOT EXISTS personnes (" +
                    "matricule TEXT PRIMARY KEY," +
                    "nom TEXT NOT NULL," +
                    "salaire REAL NOT NULL" +
                    ")";
            instruction.execute(sqlCreationTable);

            // Insertion de données prédéfinies si la table est vide
            String sqlDonneesInitiales = "INSERT OR IGNORE INTO personnes VALUES " +
                    "('M001', 'Jean Dupont', 2500.0), " +
                    "('M002', 'Marie Curie', 3500.0), " +
                    "('M003', 'Pierre Martin', 2200.0)";
            instruction.execute(sqlDonneesInitiales);

        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation de la base de données : " + e.getMessage());
        }
    }

    /**
     * Retourne une connexion à la base de données.
     */
    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL_BASE_DONNEES);
    }
}
