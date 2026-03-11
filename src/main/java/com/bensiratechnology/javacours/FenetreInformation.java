package com.bensiratechnology.javacours;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre affichant des informations.
 * 
 * @author BenSira99
 */
public class FenetreInformation extends JFrame implements ActionListener {
    private final JFrame fenetreParente;
    private final JButton boutonRetour;

    public FenetreInformation(JFrame parente) {
        this.fenetreParente = parente;

        setTitle("Informations");
        setSize(650, 200);

        Container conteneurPrincipal = this.getContentPane();
        conteneurPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel messageLabel = new JLabel("Ceci est la fenêtre des informations.", SwingConstants.CENTER);
        gbc.gridy = 0;
        conteneurPrincipal.add(messageLabel, gbc);

        boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener(this);
        gbc.gridy = 1;
        conteneurPrincipal.add(boutonRetour, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonRetour) {
            fenetreParente.setVisible(true);
            this.dispose();
        }
    }
}
