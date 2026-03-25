package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de saisie standard sans couleurs personnalisées encombrantes.
 */
public class FenetreSaisie extends JFrame implements ActionListener {

    private final JTextField champMatricule;
    private final JTextField champNom;
    private final JTextField champSalaire;
    private final JButton boutonValider;
    private final JButton boutonAnnuler;
    private final FenetrePrincipale parent;
    private final Personne personneAModifier;
    private final ServicePersonne service;

    public FenetreSaisie(FenetrePrincipale parent, Personne personne) {
        this.parent = parent;
        this.personneAModifier = personne;
        this.service = new ServicePersonne();
        boolean estModif = (personne != null);

        setTitle(estModif ? "Modifier Collaborateur" : "Nouveau Collaborateur");
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2, 10, 15));

        champMatricule = new JTextField();
        champNom = new JTextField();
        champSalaire = new JTextField();

        if (estModif) {
            champMatricule.setText(personne.obtenirMatricule());
            champMatricule.setEditable(false);
            champNom.setText(personne.obtenirNom());
            champSalaire.setText(String.valueOf(personne.obtenirSalaire()));
        }

        add(new JLabel("  Matricule :")); add(champMatricule);
        add(new JLabel("  Nom :")); add(champNom);
        add(new JLabel("  Salaire :")); add(champSalaire);

        boutonValider = new JButton(estModif ? "Enregistrer" : "Créer");
        boutonAnnuler = new JButton("Annuler");

        boutonValider.addActionListener(this);
        boutonAnnuler.addActionListener(this);

        add(boutonValider);
        add(boutonAnnuler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAnnuler) {
            dispose();
            return;
        }

        try {
            Personne p = new Personne(
                champMatricule.getText(),
                champNom.getText(),
                Double.parseDouble(champSalaire.getText())
            );

            if (personneAModifier == null) service.ajouter(p);
            else service.modifier(p);

            parent.rafraichirDonnees();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides : " + ex.getMessage());
        }
    }
}
