package com.bensira;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dashboard principal simplifié : affichage pur des données.
 * Les actions se trouvent dans l'en-tête.
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private final JTable tableau;
    private final DefaultTableModel modeleTableau;
    private final JButton boutonAjouter;
    private final JButton boutonModifier;
    private final JButton boutonSupprimer;
    private final JButton boutonQuitter;
    private final ServicePersonne service;

    public FenetrePrincipale() {
        GestionnaireBaseDonnees.initialiser();
        this.service = new ServicePersonne();

        setTitle("Gestion des Collaborateurs - Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // -- PANNEAU HAUT --
        JPanel panneauHaut = new JPanel(new BorderLayout());
        panneauHaut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titre = new JLabel("Liste des Collaborateurs");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        panneauHaut.add(titre, BorderLayout.WEST);

        // Groupe de boutons d'action
        JPanel panneauActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        boutonAjouter = new JButton("Nouveau");
        boutonModifier = new JButton("Modifier");
        boutonSupprimer = new JButton("Supprimer");

        configurerBoutonHaut(boutonAjouter, new Color(46, 204, 113)); // Vert
        configurerBoutonHaut(boutonModifier, new Color(52, 152, 219)); // Bleu
        configurerBoutonHaut(boutonSupprimer, new Color(231, 76, 60)); // Rouge

        panneauActions.add(boutonAjouter);
        panneauActions.add(boutonModifier);
        panneauActions.add(boutonSupprimer);
        
        panneauHaut.add(panneauActions, BorderLayout.EAST);
        add(panneauHaut, BorderLayout.NORTH);

        // -- TABLEAU --
        String[] colonnes = {"Matricule", "Nom Complet", "Salaire (DH)"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Lecture seule
            }
        };

        tableau = new JTable(modeleTableau);
        tableau.setRowHeight(30);
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        add(new JScrollPane(tableau), BorderLayout.CENTER);

        // -- PANNEAU BAS --
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonQuitter = new JButton("Quitter l'Application");
        boutonQuitter.addActionListener(this);
        panneauBas.add(boutonQuitter);
        add(panneauBas, BorderLayout.SOUTH);

        rafraichirDonnees();
    }

    private void configurerBoutonHaut(JButton b, Color c) {
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.addActionListener(this);
    }

    public void rafraichirDonnees() {
        modeleTableau.setRowCount(0);
        List<Personne> liste = service.listerToutes();
        for (Personne p : liste) {
            Object[] ligne = {p.obtenirMatricule(), p.obtenirNom(), p.obtenirSalaire()};
            modeleTableau.addRow(ligne);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAjouter) {
            new FenetreSaisie(this, false).setVisible(true);
        } else if (e.getSource() == boutonModifier) {
            new FenetreSaisie(this, true).setVisible(true);
        } else if (e.getSource() == boutonSupprimer) {
            new FenetreSuppression(this).setVisible(true);
        } else if (e.getSource() == boutonQuitter) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new FenetrePrincipale().setVisible(true);
    }
}