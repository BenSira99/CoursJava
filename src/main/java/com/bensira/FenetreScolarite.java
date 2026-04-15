package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface unique pour la Gestion Scolarité (Exercice 1).
 * Utilise une MenuBar et un CardLayout pour switcher entre Étudiants et Filières.
 */
public class FenetreScolarite extends JFrame implements ActionListener {
    
    // Plus de CardLayout, on utilise le Content Pane directement
    
    // Éléments Navigation (Boutons dans la MenuBar)
    private final JButton btnNavEtudiants = new JButton("Étudiants");
    private final JButton btnNavFilieres = new JButton("Filières");
    private final JButton btnNavQuitter = new JButton("Quitter");
    
    // Éléments Page Étudiants
    private final Container pnlListeEtudiants = new Container();
    private final JTextField txtMatEtu = new JTextField(10);
    private final JTextField txtNomEtu = new JTextField(10);
    private final JTextField txtMoyEtu = new JTextField(10);
    private final JComboBox<String> cbFiliereEtu = new JComboBox<>();
    private final JButton btnAjouterEtu = new JButton("Inscrire l'étudiant");
    
    // Éléments Page Filières
    private final JTextField txtCodeFil = new JTextField(10);
    private final JTextField txtDesFil = new JTextField(20);
    private final JButton btnAjouterFil = new JButton("Créer la Filière");

    private final ServiceScolarite service = new ServiceScolarite();

    public FenetreScolarite() {
        GestionnaireBaseDonnees.initialiser();
        
        setTitle("Système de Gestion Scolaire - Ben Sira");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Création du Menu avec des boutons
        JMenuBar menuBar = new JMenuBar();
        
        btnNavEtudiants.addActionListener(this);
        btnNavFilieres.addActionListener(this);
        btnNavQuitter.addActionListener(this);
        
        menuBar.add(btnNavEtudiants);
        menuBar.add(btnNavFilieres);
        menuBar.add(btnNavQuitter);
        
        setJMenuBar(menuBar);

        // 2. Affichage initial du contenu directement dans le JFrame
        getContentPane().add(creerPageEtudiants());
    }

    private Container creerPageEtudiants() {
        Container pnl = new Container();
        pnl.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Formulaire
        Container pnlForm = new Container();
        pnlForm.setLayout(new FlowLayout());
        pnlForm.add(new JLabel("Mat:")); pnlForm.add(txtMatEtu);
        pnlForm.add(new JLabel("Nom:")); pnlForm.add(txtNomEtu);
        pnlForm.add(new JLabel("Moy:")); pnlForm.add(txtMoyEtu);
        pnlForm.add(new JLabel("Fil:")); pnlForm.add(cbFiliereEtu);
        btnAjouterEtu.addActionListener(this);
        pnlForm.add(btnAjouterEtu);
        pnl.add(pnlForm);

        return pnl;
    }

    private Container creerPageFilieres() {
        Container pnl = new Container();
        pnl.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        pnl.add(new JLabel("Code de la Filière:"));
        pnl.add(txtCodeFil);
        
        pnl.add(new JLabel("Désignation:"));
        pnl.add(txtDesFil);
        
        btnAjouterFil.addActionListener(this);
        pnl.add(btnAjouterFil);

        return pnl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNavEtudiants) {
            chargerComboboxFilieres();
            getContentPane().removeAll();
            getContentPane().add(creerPageEtudiants());
            revalidate(); repaint();
        } else if (e.getSource() == btnNavFilieres) {
            getContentPane().removeAll();
            getContentPane().add(creerPageFilieres());
            revalidate(); repaint();
        } else if (e.getSource() == btnNavQuitter) {
            System.exit(0);
        } else if (e.getSource() == btnAjouterFil) {
            String code = txtCodeFil.getText();
            String des = txtDesFil.getText();
            if (!code.isEmpty() && !des.isEmpty()) {
                // Vérification si la filière existe déjà
                if (service.existeFiliere(code, des)) {
                    JOptionPane.showMessageDialog(this, "Erreur : Une filière avec ce code ou ce nom existe déjà !", "Doublon détecté", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                service.ajouterFiliere(code, des);
                JOptionPane.showMessageDialog(this, "Filière '" + des + "' créée !");
                txtCodeFil.setText(""); txtDesFil.setText("");
            }
        } else if (e.getSource() == btnAjouterEtu) {
            try {
                String mat = txtMatEtu.getText();
                String nom = txtNomEtu.getText();
                double moy = Double.parseDouble(txtMoyEtu.getText());
                String fil = (String) cbFiliereEtu.getSelectedItem();
                
                if (fil != null && !mat.isEmpty() && !nom.isEmpty()) {
                    service.ajouterEtudiant(mat, nom, moy, fil);
                    txtMatEtu.setText(""); txtNomEtu.setText(""); txtMoyEtu.setText("");
                    rafraichirListeEtudiants();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur saisie : " + ex.getMessage());
            }
        }
    }

    private void chargerComboboxFilieres() {
        cbFiliereEtu.removeAllItems();
        List<String> codes = service.obtenirListeCodesFilieres();
        for (String c : codes) cbFiliereEtu.addItem(c);
    }

    private void rafraichirListeEtudiants() {
        pnlListeEtudiants.removeAll();
        pnlListeEtudiants.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Utilisation de FlowLayout ici aussi
        chargerComboboxFilieres(); 
        
        try (ResultSet rs = service.listerTousLesEtudiants()) {
            while (rs.next()) {
                String mat = rs.getString("matricule");
                String nom = rs.getString("nom");
                double moy = rs.getDouble("moyenne");
                String codeF = rs.getString("code_filiere");
                String nomF = service.obtenirNomFiliere(codeF);

                // Chaque "carte" devient aussi un Container
                Container carte = new Container();
                carte.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

                carte.add(new JLabel("Mat: " + mat));
                JLabel lbNom = new JLabel(nom); lbNom.setFont(new Font("Arial", Font.BOLD, 13));
                carte.add(lbNom);
                carte.add(new JLabel("Moy: " + moy + "/20"));
                carte.add(new JLabel("Fil: " + nomF));

                pnlListeEtudiants.add(carte);
            }
        } catch (SQLException ex) { System.err.println(ex.getMessage()); }
        
        pnlListeEtudiants.revalidate();
        pnlListeEtudiants.repaint();
    }

    public static void main(String[] args) {
        new FenetreScolarite().setVisible(true);
    }
}
