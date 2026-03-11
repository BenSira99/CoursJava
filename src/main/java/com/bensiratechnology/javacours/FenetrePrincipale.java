package com.bensiratechnology.javacours;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe représentant la Fenêtre Principale de l'application.
 * Implémente ActionListener pour gérer les événements de manière centralisée.
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
        
        // Utilisation d'un JContainer (Container) explicite
        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des boutons
        boutonInformation = new JButton("Informations");
        boutonTraitement = new JButton("Traitement");
        boutonSortie = new JButton("Sortie");

        // Enregistrement de l'écouteur
        boutonInformation.addActionListener(this);
        boutonTraitement.addActionListener(this);
        boutonSortie.addActionListener(this);

        // Ajout des boutons
        gbc.gridy = 0;
        conteneurPrincipal.add(boutonInformation, gbc);
        
        gbc.gridy = 1;
        conteneurPrincipal.add(boutonTraitement, gbc);
        
        gbc.gridy = 2;
        conteneurPrincipal.add(boutonSortie, gbc);
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
