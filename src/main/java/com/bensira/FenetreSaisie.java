package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * Fenêtre de saisie unifiée.
 * Mode Nouveau : Matricule libre (JTextField).
 * Mode Modifier : Matricule sélectionné (JComboBox) avec auto-remplissage.
 */
public class FenetreSaisie extends JFrame implements ActionListener, ItemListener {

    private final JComboBox<String> comboMatricule;
    private final JTextField champMatricule;
    private final JTextField champNom;
    private final JTextField champSalaire;
    private final JButton boutonValider;
    private final JButton boutonAnnuler;
    
    private final FenetrePrincipale parent;
    private final boolean modeModification;
    private final ServicePersonne service;
    private List<Personne> listePersonnes;

    public FenetreSaisie(FenetrePrincipale parent, boolean modeModification) {
        this.parent = parent;
        this.modeModification = modeModification;
        this.service = new ServicePersonne();

        setTitle(modeModification ? "Modifier un Collaborateur" : "Nouveau Collaborateur");
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel panneauForm = new JPanel(new GridLayout(4, 2, 10, 15));
        panneauForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboMatricule = new JComboBox<>();
        champMatricule = new JTextField();
        champNom = new JTextField();
        champSalaire = new JTextField();

        if (modeModification) {
            chargerMatricules();
            comboMatricule.addItemListener(this);
            panneauForm.add(new JLabel("Choisir Matricule :"));
            panneauForm.add(comboMatricule);
            // Initialiser les champs avec la première sélection
            remplirChampsDepuisSelection();
        } else {
            panneauForm.add(new JLabel("Saisir Matricule :"));
            panneauForm.add(champMatricule);
        }

        panneauForm.add(new JLabel("Nom Complet :"));
        panneauForm.add(champNom);
        panneauForm.add(new JLabel("Salaire (DH) :"));
        panneauForm.add(champSalaire);

        add(panneauForm, BorderLayout.CENTER);

        // -- BOUTONS --
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        boutonValider = new JButton(modeModification ? "Enregistrer les modifications" : "Ajouter au Dashboard");
        boutonAnnuler = new JButton("Annuler");

        boutonValider.addActionListener(this);
        boutonAnnuler.addActionListener(this);

        panneauBoutons.add(boutonValider);
        panneauBoutons.add(boutonAnnuler);
        add(panneauBoutons, BorderLayout.SOUTH);
    }

    private void chargerMatricules() {
        listePersonnes = service.listerToutes();
        for (Personne p : listePersonnes) {
            comboMatricule.addItem(p.obtenirMatricule());
        }
    }

    private void remplirChampsDepuisSelection() {
        String mat = (String) comboMatricule.getSelectedItem();
        if (mat == null) return;
        
        for (Personne p : listePersonnes) {
            if (p.obtenirMatricule().equals(mat)) {
                champNom.setText(p.obtenirNom());
                champSalaire.setText(String.valueOf(p.obtenirSalaire()));
                break;
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            remplirChampsDepuisSelection();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAnnuler) {
            dispose();
            return;
        }

        try {
            String matricule = modeModification 
                ? (String) comboMatricule.getSelectedItem() 
                : champMatricule.getText();
            
            String nom = champNom.getText();
            double salaire = Double.parseDouble(champSalaire.getText());

            if (matricule == null || matricule.isEmpty()) {
                throw new Exception("Le matricule est obligatoire.");
            }

            Personne p = new Personne(matricule, nom, salaire);

            if (modeModification) {
                service.modifier(p);
            } else {
                service.ajouter(p);
            }

            parent.rafraichirDonnees();
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un salaire valide (numérique).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }
}
