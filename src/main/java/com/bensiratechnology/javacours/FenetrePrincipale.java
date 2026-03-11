package com.bensiratechnology.javacours;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe représentant la Fenêtre Principale de l'application.
 * Implémente ActionListener pour gérer les événements de manière centralisée.
 * Utilise FlowLayout pour l'agencement des boutons.
 * 
 * @author BenSira99
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private final JButton boutonInformation;
    private final JButton boutonTraitement;
    private final JButton boutonSortie;

    public FenetrePrincipale() {
        // Configuration de la fenêtre
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Utilisation d'un JContainer (Container) explicite avec FlowLayout
        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Création des boutons
        boutonInformation = new JButton("Informations");
        boutonTraitement = new JButton("Traitement");
        boutonSortie = new JButton("Sortie");

        // Enregistrement de l'écouteur
        boutonInformation.addActionListener(this);
        boutonTraitement.addActionListener(this);
        boutonSortie.addActionListener(this);

        // Ajout des boutons directement au conteneur
        conteneurPrincipal.add(boutonInformation);
        conteneurPrincipal.add(boutonTraitement);
        conteneurPrincipal.add(boutonSortie);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == boutonInformation) {
            new FenetreInformation(this).setVisible(true);
            this.setVisible(false);
        } else if (source == boutonTraitement) {
            new FenetreTraitement(this).setVisible(true);
            this.setVisible(false);
        } else if (source == boutonSortie) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        FenetrePrincipale fenetre = new FenetrePrincipale();
        fenetre.setVisible(true);
    }
}
