package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de notification pour les actions (Suppression, Succès).
 */
public class FenetreNotification extends JFrame implements ActionListener {

    private final FenetrePrincipale parent;
    private final Personne personneConcernee;
    private final ServicePersonne service;

    public FenetreNotification(FenetrePrincipale parent, Personne personne, boolean suppression) {
        this.parent = parent;
        this.personneConcernee = personne;
        this.service = new ServicePersonne();

        setTitle(suppression ? "Confirmer la suppression" : "Information");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(224, 242, 241));
        setLayout(new BorderLayout());

        JPanel panneauContenu = new JPanel(new GridBagLayout());
        panneauContenu.setOpaque(false);
        
        String message = suppression 
            ? "Êtes-vous sûr de vouloir supprimer " + personne.obtenirNom() + " ?"
            : "Le collaborateur " + personne.obtenirNom() + " a été supprimé.";
        
        JLabel lblMessage = new JLabel("<html><center>" + message + "</center></html>");
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMessage.setForeground(new Color(0, 105, 92));
        panneauContenu.add(lblMessage);
        
        add(panneauContenu, BorderLayout.CENTER);

        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panneauBoutons.setOpaque(false);

        if (suppression) {
            JButton boutonOui = creerBouton("Oui, Supprimer", new Color(211, 47, 47));
            JButton boutonNon = creerBouton("Annuler", Color.GRAY);
            panneauBoutons.add(boutonOui);
            panneauBoutons.add(boutonNon);
        } else {
            JButton boutonOk = creerBouton("OK", new Color(0, 121, 107));
            panneauBoutons.add(boutonOk);
        }

        add(panneauBoutons, BorderLayout.SOUTH);
    }

    private JButton creerBouton(String texte, Color c) {
        JButton b = new JButton(texte);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();

        if (commande.equals("Oui, Supprimer")) {
            service.supprimer(personneConcernee.obtenirMatricule());
            parent.rafraichirDonnees();
            // On ferme cette fenêtre et on informe
            this.dispose();
            new FenetreNotification(parent, personneConcernee, false).setVisible(true);
        } else {
            this.dispose();
        }
    }
}
