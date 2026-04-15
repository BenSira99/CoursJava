package com.bensira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service gérant les opérations CRUD pour les Filières et Étudiants.
 * Version simplifiée utilisant des Statement simples et la concaténation (+).
 */
public class ServiceScolarite {

    public boolean existeFiliere(String code, String designation) {
        String sql = "SELECT count(*) FROM filieres WHERE code = '" + code + "' OR designation = '" + designation + "'";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur de vérification filière : " + e.getMessage());
        }
        return false;
    }

    public void ajouterFiliere(String code, String designation) {
        // Construction de la requête par concaténation (+)
        String sql = "INSERT INTO filieres(code, designation) VALUES('" + code + "', '" + designation + "')";
        
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement()) {
            
            instruction.executeUpdate(sql);
            System.out.println("Filière ajoutée (SQL direct) : " + code);
            
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout filière : " + e.getMessage());
        }
    }

    public void ajouterEtudiant(String matricule, String nom, double moyenne, String codeFiliere) {
        // Construction de la requête par concaténation (+)
        String sql = "INSERT INTO etudiants(matricule, nom, moyenne, code_filiere) VALUES('" 
                     + matricule + "', '" + nom + "', " + moyenne + ", '" + codeFiliere + "')";
        
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement()) {
            
            instruction.executeUpdate(sql);
            System.out.println("Étudiant ajouté (SQL direct) : " + nom);
            
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout étudiant : " + e.getMessage());
        }
    }

    public ResultSet listerTousLesEtudiants() throws SQLException {
        Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
        Statement instruction = connexion.createStatement();
        return instruction.executeQuery("SELECT * FROM etudiants");
    }

    public List<String> obtenirListeCodesFilieres() {
        List<String> codes = new ArrayList<>();
        String sql = "SELECT code FROM filieres";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet resultat = instruction.executeQuery(sql)) {
            while (resultat.next()) {
                codes.add(resultat.getString("code"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de liste codes filières : " + e.getMessage());
        }
        return codes;
    }

    public String obtenirNomFiliere(String code) {
        String sql = "SELECT designation FROM filieres WHERE code = '" + code + "'";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getString("designation");
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur de récupération filière : " + e.getMessage());
        }
        return "N/A";
    }
}
