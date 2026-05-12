package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de connexion et d'inscription pour l'application de Gestion Scolaire.
 * Auteur: BenSira99
 */
public class FenetreConnexion extends JFrame implements ActionListener {
    
    private final JTextField txtUtilisateur = new JTextField(15);
    private final JPasswordField txtMotDePasse = new JPasswordField(15);
    private final JButton btnAction = new JButton("Se connecter");
    private final JButton btnQuitter = new JButton("Quitter");
    private final JButton btnSwitch = new JButton("Créer un compte");
    private final JLabel lblTitre = new JLabel("Connexion", SwingConstants.CENTER);
    
    private boolean estEnModeConnexion = true;
    private final ServiceScolarite service = new ServiceScolarite();

    public FenetreConnexion() {
        GestionnaireBaseDonnees.initialiser();
        
        setTitle("Authentification - Ben Sira");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        configurerInterface();
    }

    private void configurerInterface() {
        JPanel pnlPrincipal = new JPanel(new GridBagLayout());
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitre.setForeground(new Color(33, 150, 243));
        pnlPrincipal.add(lblTitre, gbc);

        // Utilisateur
        gbc.gridy = 1; gbc.gridwidth = 1;
        pnlPrincipal.add(new JLabel("Utilisateur :"), gbc);
        gbc.gridx = 1;
        pnlPrincipal.add(txtUtilisateur, gbc);

        // Mot de passe
        gbc.gridx = 0; gbc.gridy = 2;
        pnlPrincipal.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        pnlPrincipal.add(txtMotDePasse, gbc);

        // Bouton Switch (Lien)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnSwitch.setBorderPainted(false);
        btnSwitch.setContentAreaFilled(false);
        btnSwitch.setFocusPainted(false);
        btnSwitch.setForeground(Color.BLUE);
        btnSwitch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSwitch.addActionListener(this);
        pnlPrincipal.add(btnSwitch, gbc);

        // Boutons Action
        JPanel pnlBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAction.addActionListener(this);
        btnQuitter.addActionListener(this);
        pnlBoutons.add(btnAction);
        pnlBoutons.add(btnQuitter);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        pnlPrincipal.add(pnlBoutons, gbc);

        add(pnlPrincipal);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSwitch) {
            basculerMode();
        } else if (e.getSource() == btnAction) {
            if (estEnModeConnexion) {
                gererConnexion();
            } else {
                gererInscription();
            }
        } else if (e.getSource() == btnQuitter) {
            System.exit(0);
        }
    }

    private void basculerMode() {
        estEnModeConnexion = !estEnModeConnexion;
        if (estEnModeConnexion) {
            lblTitre.setText("Connexion");
            btnAction.setText("Se connecter");
            btnSwitch.setText("Créer un compte");
        } else {
            lblTitre.setText("Inscription");
            btnAction.setText("S'inscrire");
            btnSwitch.setText("Déjà un compte ? Se connecter");
        }
        txtMotDePasse.setText("");
    }

    private void gererConnexion() {
        String utilisateur = txtUtilisateur.getText();
        String motDePasse = new String(txtMotDePasse.getPassword());

        if (utilisateur.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.authentifierUtilisateur(utilisateur, motDePasse)) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new FenetreScolarite().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Utilisateur ou mot de passe incorrect.", "Échec", JOptionPane.ERROR_MESSAGE);
            txtMotDePasse.setText("");
        }
    }

    private void gererInscription() {
        String utilisateur = txtUtilisateur.getText();
        String motDePasse = new String(txtMotDePasse.getPassword());

        if (utilisateur.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (service.inscrireUtilisateur(utilisateur, motDePasse)) {
            JOptionPane.showMessageDialog(this, "Compte créé avec succès ! Vous pouvez maintenant vous connecter.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            basculerMode();
        } else {
            JOptionPane.showMessageDialog(this, "Cet identifiant est peut-être déjà utilisé.", "Erreur d'inscription", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new FenetreConnexion().setVisible(true));
    }
}
