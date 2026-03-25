package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de notification simplifiée.
 */
public class FenetreNotification extends JFrame implements ActionListener {

    private final FenetrePrincipale parent;
    private final Personne personneConcernee;
    private final ServicePersonne service;

    public FenetreNotification(FenetrePrincipale parent, Personne personne, boolean suppression) {
        this.parent = parent;
        this.personneConcernee = personne;
        this.service = new ServicePersonne();

        setTitle(suppression ? "Confirmer" : "Notification");
        setSize(350, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        String message = suppression 
            ? "Supprimer " + personne.obtenirNom() + " ?"
            : "Suppression effectuée.";
        
        JLabel lbl = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);

        JPanel pnl = new JPanel(new FlowLayout());
        if (suppression) {
            JButton oui = new JButton("Oui, supprimer");
            oui.setBackground(new Color(200, 0, 0));
            oui.setForeground(Color.WHITE);
            oui.addActionListener(this);
            pnl.add(oui);
            
            JButton non = new JButton("Annuler");
            non.addActionListener(this);
            pnl.add(non);
        } else {
            JButton ok = new JButton("OK");
            ok.addActionListener(this);
            pnl.add(ok);
        }
        add(pnl, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Oui, supprimer")) {
            service.supprimer(personneConcernee.obtenirMatricule());
            parent.rafraichirDonnees();
            dispose();
            new FenetreNotification(parent, personneConcernee, false).setVisible(true);
        } else {
            dispose();
        }
    }
}
