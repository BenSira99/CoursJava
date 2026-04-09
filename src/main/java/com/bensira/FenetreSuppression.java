package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Fenêtre de suppression avec sélection par Matricule (JComboBox).
 */
public class FenetreSuppression extends JFrame implements ActionListener {

    private final JComboBox<String> comboMatricule;
    private final JButton boutonSupprimer;
    private final JButton boutonAnnuler;
    private final FenetrePrincipale parent;
    private final ServicePersonne service;

    public FenetreSuppression(FenetrePrincipale parent) {
        this.parent = parent;
        this.service = new ServicePersonne();

        setTitle("Supprimer un Collaborateur");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(15, 15));

        JPanel panneauCentral = new JPanel(new GridLayout(2, 1, 10, 10));
        panneauCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panneauCentral.add(new JLabel("Choisir le matricule à supprimer :", SwingConstants.CENTER));
        
        comboMatricule = new JComboBox<>();
        chargerMatricules();
        panneauCentral.add(comboMatricule);
        
        add(panneauCentral, BorderLayout.CENTER);

        // -- BOUTONS --
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        boutonSupprimer = new JButton("Supprimer définitivement");
        boutonSupprimer.setBackground(new Color(192, 57, 43));
        boutonSupprimer.setForeground(Color.WHITE);
        boutonSupprimer.setFocusPainted(false);
        
        boutonAnnuler = new JButton("Annuler");

        boutonSupprimer.addActionListener(this);
        boutonAnnuler.addActionListener(this);

        panneauBoutons.add(boutonSupprimer);
        panneauBoutons.add(boutonAnnuler);
        add(panneauBoutons, BorderLayout.SOUTH);
    }

    private void chargerMatricules() {
        List<Personne> liste = service.listerToutes();
        for (Personne p : liste) {
            comboMatricule.addItem(p.obtenirMatricule());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAnnuler) {
            dispose();
            return;
        }

        String mat = (String) comboMatricule.getSelectedItem();
        if (mat == null || mat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun matricule sélectionné.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer le collaborateur " + mat + " ?", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            service.supprimer(mat);
            parent.rafraichirDonnees();
            dispose();
            JOptionPane.showMessageDialog(parent, "Collaborateur supprimé avec succès.");
        }
    }
}
