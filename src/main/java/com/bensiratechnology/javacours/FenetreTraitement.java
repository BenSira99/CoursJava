package com.bensiratechnology.javacours;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre effectuant un traitement simulé.
 * Utilise FlowLayout pour l'agencement des composants.
 * 
 * @author BenSira99
 */
public class FenetreTraitement extends JFrame implements ActionListener {
    private final JFrame fenetreParente;
    private final JButton boutonRetour;

    public FenetreTraitement(JFrame parente) {
        this.fenetreParente = parente;

        setTitle("Traitement");
        setSize(650, 200);
        
        // Utilisation d'un JContainer (Container) explicite avec FlowLayout
        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Contenu
        JLabel messageLabel = new JLabel("Traitement en cours...", SwingConstants.CENTER);
        conteneurPrincipal.add(messageLabel);

        // Bouton Retour
        boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener(this);
        conteneurPrincipal.add(boutonRetour);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonRetour) {
            fenetreParente.setVisible(true);
            this.dispose();
        }
    }
}
