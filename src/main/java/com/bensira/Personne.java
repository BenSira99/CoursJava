package com.bensira;

/**
 * Représente une entité Personne.
 */
public class Personne {
    private String matricule;
    private String nom;
    private double salaire;

    public Personne(String matricule, String nom, double salaire) {
        this.matricule = matricule;
        this.nom = nom;
        this.salaire = salaire;
    }

    public String obtenirMatricule() {
        return matricule;
    }

    public void modifierMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String obtenirNom() {
        return nom;
    }

    public void modifierNom(String nom) {
        this.nom = nom;
    }

    public double obtenirSalaire() {
        return salaire;
    }

    public void modifierSalaire(double salaire) {
        this.salaire = salaire;
    }
}
