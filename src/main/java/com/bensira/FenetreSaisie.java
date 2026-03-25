package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de saisie pour l'ajout ou la modification d'une personne.
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

    // Couleurs Teal
    private final Color COULEUR_FOND = new Color(224, 242, 241);
    private final Color COULEUR_TEXTE = new Color(0, 105, 92);
    private final Color COULEUR_BOUTON = new Color(38, 166, 154);

    public FenetreSaisie(FenetrePrincipale parent, Personne personne) {
        this.parent = parent;
        this.personneAModifier = personne;
        this.service = new ServicePersonne();
        boolean estModification = (personne != null);

        setTitle(estModification ? "Modifier Collaborateur" : "Nouveau Collaborateur");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(COULEUR_FOND);
        setLayout(new GridLayout(4, 2, 10, 20));

        // -- CHAMPS --
        champMatricule = new JTextField();
        champNom = new JTextField();
        champSalaire = new JTextField();

        if (estModification) {
            champMatricule.setText(personne.obtenirMatricule());
            champMatricule.setEditable(false); // On ne modifie pas la clé primaire
            champNom.setText(personne.obtenirNom());
            champSalaire.setText(String.valueOf(personne.obtenirSalaire()));
        }

        ajouterComposant("Matricule :", champMatricule);
        ajouterComposant("Nom Complet :", champNom);
        ajouterComposant("Salaire (DH) :", champSalaire);

        // -- BOUTONS --
        boutonValider = new JButton(estModification ? "Modifier" : "Insérer");
        boutonAnnuler = new JButton("Annuler");

        configurerBouton(boutonValider, COULEUR_BOUTON);
        configurerBouton(boutonAnnuler, Color.GRAY);

        add(boutonValider);
        add(boutonAnnuler);
    }

    private void ajouterComposant(String label, JTextField champ) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(COULEUR_TEXTE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(lbl);
        add(champ);
    }

    private void configurerBouton(JButton b, Color c) {
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAnnuler) {
            this.dispose();
            return;
        }

        try {
            String matricule = champMatricule.getText().trim();
            String nom = champNom.getText().trim();
            double salaire = Double.parseDouble(champSalaire.getText().trim());

            if (matricule.isEmpty() || nom.isEmpty()) {
                throw new Exception("Champs obligatoires vides.");
            }

            Personne p = new Personne(matricule, nom, salaire);
            if (personneAModifier == null) {
                service.ajouter(p);
            } else {
                service.modifier(p);
            }

            parent.rafraichirDonnees();
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salaire invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
