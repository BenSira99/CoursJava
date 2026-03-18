package com.bensira;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre affichant des informations de traitement.
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

        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JLabel messageLabel = new JLabel("Ceci est la fenêtre des traitements.", SwingConstants.CENTER);
        conteneurPrincipal.add(messageLabel);

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
