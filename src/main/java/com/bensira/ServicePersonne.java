package com.bensira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service gérant les opérations CRUD pour la classe Personne.
 */
public class ServicePersonne {

    public void ajouter(Personne personne) {
        String sql = "INSERT INTO personnes(matricule, nom, salaire) VALUES(?, ?, ?)";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             PreparedStatement instruction = connexion.prepareStatement(sql)) {
            instruction.setString(1, personne.obtenirMatricule());
            instruction.setString(2, personne.obtenirNom());
            instruction.setDouble(3, personne.obtenirSalaire());
            instruction.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout : " + e.getMessage());
        }
    }

    public List<Personne> listerToutes() {
        List<Personne> personnes = new ArrayList<>();
        String sql = "SELECT * FROM personnes";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet resultat = instruction.executeQuery(sql)) {
            while (resultat.next()) {
                personnes.add(new Personne(
                        resultat.getString("matricule"),
                        resultat.getString("nom"),
                        resultat.getDouble("salaire")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de liste : " + e.getMessage());
        }
        return personnes;
    }

    public void modifier(Personne personne) {
        String sql = "UPDATE personnes SET nom = ?, salaire = ? WHERE matricule = ?";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             PreparedStatement instruction = connexion.prepareStatement(sql)) {
            instruction.setString(1, personne.obtenirNom());
            instruction.setDouble(2, personne.obtenirSalaire());
            instruction.setString(3, personne.obtenirMatricule());
            instruction.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur de modification : " + e.getMessage());
        }
    }

    public void supprimer(String matricule) {
        String sql = "DELETE FROM personnes WHERE matricule = ?";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             PreparedStatement instruction = connexion.prepareStatement(sql)) {
            instruction.setString(1, matricule);
            instruction.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur de suppression : " + e.getMessage());
        }
    }
}
