package com.bensira;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dashboard principal avec actions intégrées au tableau.
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private final JTable tableau;
    private final DefaultTableModel modeleTableau;
    private final JButton boutonAjouter;
    private final JButton boutonQuitter;
    private final ServicePersonne service;

    public FenetrePrincipale() {
        GestionnaireBaseDonnees.initialiser();
        this.service = new ServicePersonne();

        setTitle("Gestion des Collaborateurs");
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

        boutonAjouter = new JButton("Nouveau Collaborateur");
        boutonAjouter.setFont(new Font("Arial", Font.BOLD, 14));
        boutonAjouter.addActionListener(this);
        panneauHaut.add(boutonAjouter, BorderLayout.EAST);
        add(panneauHaut, BorderLayout.NORTH);

        // -- TABLEAU --
        String[] colonnes = {"Matricule", "Nom Complet", "Salaire (DH)", "Modif.", "Suppr."};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 3; // Seuls les boutons sont éditables
            }
        };

        tableau = new JTable(modeleTableau);
        tableau.setRowHeight(35);
        
        // Rendu et Éditeur pour la colonne Modifier (3)
        GestionnaireBoutonTableau actionModif = new GestionnaireBoutonTableau("Modifier", new Color(0, 100, 200), (ligne) -> {
            Personne p = obtenirPersonneLigne(ligne);
            new FenetreSaisie(this, p).setVisible(true);
        });
        tableau.getColumnModel().getColumn(3).setCellRenderer(actionModif);
        tableau.getColumnModel().getColumn(3).setCellEditor(actionModif);

        // Rendu et Éditeur pour la colonne Supprimer (4)
        GestionnaireBoutonTableau actionSuppr = new GestionnaireBoutonTableau("Supprimer", new Color(200, 0, 0), (ligne) -> {
            Personne p = obtenirPersonneLigne(ligne);
            new FenetreNotification(this, p, true).setVisible(true);
        });
        tableau.getColumnModel().getColumn(4).setCellRenderer(actionSuppr);
        tableau.getColumnModel().getColumn(4).setCellEditor(actionSuppr);

        add(new JScrollPane(tableau), BorderLayout.CENTER);

        // -- PANNEAU BAS --
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(this);
        panneauBas.add(boutonQuitter);
        add(panneauBas, BorderLayout.SOUTH);

        rafraichirDonnees();
    }

    private Personne obtenirPersonneLigne(int ligne) {
        String matricule = (String) modeleTableau.getValueAt(ligne, 0);
        String nom = (String) modeleTableau.getValueAt(ligne, 1);
        double salaire = (double) modeleTableau.getValueAt(ligne, 2);
        return new Personne(matricule, nom, salaire);
    }

    public void rafraichirDonnees() {
        modeleTableau.setRowCount(0);
        List<Personne> liste = service.listerToutes();
        for (Personne p : liste) {
            Object[] ligne = {p.obtenirMatricule(), p.obtenirNom(), p.obtenirSalaire(), "Modifier", "Supprimer"};
            modeleTableau.addRow(ligne);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonAjouter) {
            new FenetreSaisie(this, null).setVisible(true);
        } else if (e.getSource() == boutonQuitter) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetrePrincipale().setVisible(true));
    }
}