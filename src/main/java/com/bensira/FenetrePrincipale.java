package com.bensira;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

    private final JMenuItem elementInformation;
    private final JMenuItem elementTraitement;
    private final JMenuItem elementSortie;

    public FenetrePrincipale() {
        // Configuration de la fenêtre
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Utilisation d'un JContainer (Container) explicite avec FlowLayout
        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Création de la barre de menus
        JMenuBar barreMenus = new JMenuBar();
        JMenu menuOptions = new JMenu("Options");

        // Création des éléments de menu
        elementInformation = new JMenuItem("Informations");
        elementTraitement = new JMenuItem("Traitement");
        elementSortie = new JMenuItem("Sortie");

        // Enregistrement des écouteurs
        elementInformation.addActionListener(this);
        elementTraitement.addActionListener(this);
        elementSortie.addActionListener(this);

        // Ajout des éléments au menu et de la barre à la fenêtre
        menuOptions.add(elementInformation);
        menuOptions.add(elementTraitement);
        menuOptions.addSeparator();
        menuOptions.add(elementSortie);
        barreMenus.add(menuOptions);
        setJMenuBar(barreMenus);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == elementInformation) {
            new FenetreInformation(this).setVisible(true);
            this.setVisible(false);
        } else if (source == elementTraitement) {
            new FenetreTraitement(this).setVisible(true);
            this.setVisible(false);
        } else if (source == elementSortie) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        FenetrePrincipale fenetre = new FenetrePrincipale();
        fenetre.setVisible(true);
    }
}