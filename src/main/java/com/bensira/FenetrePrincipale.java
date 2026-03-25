package com.bensira;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dashboard principal affichant la liste des personnes dans un tableau.
 * Style : Teal (Sarcelle) léger.
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private final JTable tableau;
    private final DefaultTableModel modeleTableau;
    private final JButton boutonAjouter;
    private final JButton boutonModifier;
    private final JButton boutonSupprimer;
    private final JButton boutonQuitter;
    private final ServicePersonne service;

    // Couleurs Teal personnalisées
    private final Color COULEUR_FOND = new Color(224, 242, 241); // Teal très léger
    private final Color COULEUR_ENTETE = new Color(0, 121, 107); // Teal sombre
    private final Color COULEUR_BOUTON = new Color(38, 166, 154); // Teal moyen

    public FenetrePrincipale() {
        GestionnaireBaseDonnees.initialiser();
        this.service = new ServicePersonne();

        setTitle("Dashboard - Gestion des Personnes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COULEUR_FOND);
        setLayout(new BorderLayout(10, 10));

        // -- PANNEAU HAUT (Titre et Ajout) --
        JPanel panneauHaut = new JPanel(new BorderLayout());
        panneauHaut.setBackground(COULEUR_FOND);
        panneauHaut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titre = new JLabel("Liste des Collaborateurs");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titre.setForeground(COULEUR_ENTETE);
        panneauHaut.add(titre, BorderLayout.WEST);

        boutonAjouter = creerBouton("Nouveau Collaborateur");
        panneauHaut.add(boutonAjouter, BorderLayout.EAST);
        add(panneauHaut, BorderLayout.NORTH);

        // -- TABLEAU CENTRAL --
        String[] colonnes = {"Matricule", "Nom Complet", "Salaire (DH)"};
        modeleTableau = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Lecture seule
            }
        };
        tableau = new JTable(modeleTableau);
        tableau.setRowHeight(25);
        tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableau.getTableHeader().setBackground(COULEUR_ENTETE);
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JScrollPane defileur = new JScrollPane(tableau);
        defileur.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        defileur.getViewport().setBackground(Color.WHITE);
        add(defileur, BorderLayout.CENTER);

        // -- PANNEAU BAS (Actions) --
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panneauBas.setBackground(COULEUR_FOND);

        boutonModifier = creerBouton("Modifier");
        boutonSupprimer = creerBouton("Supprimer");
        boutonQuitter = new JButton("Quitter");
        
        configurerBoutonAction(boutonModifier);
        configurerBoutonAction(boutonSupprimer);
        configurerBoutonAction(boutonQuitter);
        boutonQuitter.setBackground(new Color(200, 50, 50)); // Rouge léger pour quitter
        boutonQuitter.setForeground(Color.WHITE);

        panneauBas.add(boutonModifier);
        panneauBas.add(boutonSupprimer);
        panneauBas.add(boutonQuitter);
        add(panneauBas, BorderLayout.SOUTH);

        rafraichirDonnees();
    }

    private JButton creerBouton(String texte) {
        JButton b = new JButton(texte);
        b.setBackground(COULEUR_BOUTON);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.addActionListener(this);
        return b;
    }

    private void configurerBoutonAction(JButton b) {
        b.setPreferredSize(new Dimension(120, 35));
        b.addActionListener(this);
    }

    public void rafraichirDonnees() {
        modeleTableau.setRowCount(0);
        List<Personne> liste = service.listerToutes();
        for (Personne p : liste) {
            Object[] ligne = {p.obtenirMatricule(), p.obtenirNom(), p.obtenirSalaire()};
            modeleTableau.addRow(ligne);
        }
        
        // Cacher les boutons si pas de données (selon demande utilisateur)
        boolean aDesDonnees = !liste.isEmpty();
        boutonModifier.setVisible(aDesDonnees);
        boutonSupprimer.setVisible(aDesDonnees);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == boutonQuitter) {
            System.exit(0);
        } else if (source == boutonAjouter) {
            new FenetreSaisie(this, null).setVisible(true);
        } else {
            int ligneSelectionnee = tableau.getSelectedRow();
            if (ligneSelectionnee == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String matricule = (String) modeleTableau.getValueAt(ligneSelectionnee, 0);
            String nom = (String) modeleTableau.getValueAt(ligneSelectionnee, 1);
            double salaire = (double) modeleTableau.getValueAt(ligneSelectionnee, 2);
            Personne selection = new Personne(matricule, nom, salaire);

            if (source == boutonModifier) {
                new FenetreSaisie(this, selection).setVisible(true);
            } else if (source == boutonSupprimer) {
                new FenetreNotification(this, selection, true).setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        // Look and Feel pour un meilleur rendu
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new FenetrePrincipale().setVisible(true);
        });
    }
}