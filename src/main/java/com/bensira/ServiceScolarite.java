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

    public boolean existeEtudiant(String matricule, String nom) {
        String sql = "SELECT count(*) FROM etudiants WHERE matricule = '" + matricule + "' OR nom = '" + nom + "'";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur de vérification étudiant : " + e.getMessage());
        }
        return false;
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

    public void modifierFiliere(String code, String nouvelleDesignation) {
        String sql = "UPDATE filieres SET designation = '" + nouvelleDesignation + "' WHERE code = '" + code + "'";
        executerUpdate(sql, "Filière modifiée");
    }

    public void supprimerFiliere(String code) {
        String sql = "DELETE FROM filieres WHERE code = '" + code + "'";
        executerUpdate(sql, "Filière supprimée");
    }

    public void modifierEtudiant(String mat, String nom, double moy, String codeFil) {
        String sql = "UPDATE etudiants SET nom = '" + nom + "', moyenne = " + moy + 
                     ", code_filiere = '" + codeFil + "' WHERE matricule = '" + mat + "'";
        executerUpdate(sql, "Étudiant modifié");
    }

    public void supprimerEtudiant(String mat) {
        String sql = "DELETE FROM etudiants WHERE matricule = '" + mat + "'";
        executerUpdate(sql, "Étudiant supprimé");
    }

    public List<String> obtenirListeMatriculesEtudiants() {
        List<String> matricules = new ArrayList<>();
        String sql = "SELECT matricule FROM etudiants";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            while (rs.next()) {
                matricules.add(rs.getString("matricule"));
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return matricules;
    }

    public ResultSet obtenirDonneesEtudiant(String mat) throws SQLException {
        Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
        Statement instruction = connexion.createStatement();
        return instruction.executeQuery("SELECT * FROM etudiants WHERE matricule = '" + mat + "'");
    }

    public void supprimerFiliereParNom(String designation) {
        String sql = "DELETE FROM filieres WHERE designation = '" + designation + "'";
        executerUpdate(sql, "Filière supprimée par nom");
    }

    public void supprimerEtudiantParNom(String nom) {
        String sql = "DELETE FROM etudiants WHERE nom = '" + nom + "'";
        executerUpdate(sql, "Étudiant supprimé par nom");
    }

    public List<String> obtenirListeDesignationsFilieres() {
        List<String> noms = new ArrayList<>();
        String sql = "SELECT designation FROM filieres";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            while (rs.next()) noms.add(rs.getString("designation"));
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return noms;
    }

    public List<String> obtenirListeNomsEtudiants() {
        List<String> noms = new ArrayList<>();
        String sql = "SELECT nom FROM etudiants";
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement();
             ResultSet rs = instruction.executeQuery(sql)) {
            while (rs.next()) noms.add(rs.getString("nom"));
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return noms;
    }

    private void executerUpdate(String sql, String message) {
        try (Connection connexion = GestionnaireBaseDonnees.obtenirConnexion();
             Statement instruction = connexion.createStatement()) {
            instruction.executeUpdate(sql);
            System.out.println(message + " SQL : " + sql);
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }
}
