package com.bensira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Interface de Gestion Scolarité (Ben Sira).
 * Système CRUD complet avec menus et listes déroulantes interactives.
 */
public class FenetreScolarite extends JFrame implements ActionListener {
    
    private final ServiceScolarite service = new ServiceScolarite();

    // Menu Bar
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuFiliere = new JMenu("Filières");
    private final JMenu menuEtudiant = new JMenu("Étudiants");
    private final JMenu menuApp = new JMenu("Application");

    // Menu Items Filières
    private final JMenuItem itemAjouterFil = new JMenuItem("Ajouter");
    private final JMenuItem itemModifierFil = new JMenuItem("Modifier");
    private final JMenuItem itemSupprimerFil = new JMenuItem("Supprimer");

    // Menu Items Étudiants
    private final JMenuItem itemAjouterEtu = new JMenuItem("Inscrire");
    private final JMenuItem itemModifierEtu = new JMenuItem("Modifier");
    private final JMenuItem itemSupprimerEtu = new JMenuItem("Supprimer");
    private final JMenuItem itemListeEtu = new JMenuItem("Lister tous");

    private final JMenuItem itemAccueil = new JMenuItem("Accueil");
    private final JMenuItem itemQuitter = new JMenuItem("Quitter");

    // Panneau principal avec CardLayout
    private final JPanel pnlPrincipal = new JPanel(new CardLayout());
    private final CardLayout cards = (CardLayout) pnlPrincipal.getLayout();

    public FenetreScolarite() {
        GestionnaireBaseDonnees.initialiser();
        
        setTitle("Système de Gestion Scolaire - Ben Sira");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        configurerMenus();
        configurerPages();

        add(pnlPrincipal);
        setJMenuBar(menuBar);
        
        // Page par défaut
        cards.show(pnlPrincipal, "ACCUEIL");
    }

    private void configurerMenus() {
        // Filières
        itemAjouterFil.addActionListener(this);
        itemModifierFil.addActionListener(this);
        itemSupprimerFil.addActionListener(this);
        menuFiliere.add(itemAjouterFil);
        menuFiliere.add(itemModifierFil);
        menuFiliere.add(itemSupprimerFil);

        // Étudiants
        itemAjouterEtu.addActionListener(this);
        itemModifierEtu.addActionListener(this);
        itemSupprimerEtu.addActionListener(this);
        itemListeEtu.addActionListener(this);
        menuEtudiant.add(itemAjouterEtu);
        menuEtudiant.add(itemModifierEtu);
        menuEtudiant.add(itemSupprimerEtu);
        menuEtudiant.addSeparator();
        menuEtudiant.add(itemListeEtu);

        // App
        itemAccueil.addActionListener(this);
        itemQuitter.addActionListener(this);
        menuApp.add(itemAccueil);
        menuApp.addSeparator();
        menuApp.add(itemQuitter);

        menuBar.add(menuApp);
        menuBar.add(menuFiliere);
        menuBar.add(menuEtudiant);
    }

    private void configurerPages() {
        pnlPrincipal.add(creerPageAccueil(), "ACCUEIL");
        pnlPrincipal.add(creerPageAjoutFiliere(), "AJOUT_FIL");
        pnlPrincipal.add(creerPageModifFiliere(), "MODIF_FIL");
        pnlPrincipal.add(creerPageSupprFiliere(), "SUPPR_FIL");
        
        pnlPrincipal.add(creerPageAjoutEtudiant(), "AJOUT_ETU");
        pnlPrincipal.add(creerPageModifEtudiant(), "MODIF_ETU");
        pnlPrincipal.add(creerPageSupprEtudiant(), "SUPPR_ETU");
        pnlPrincipal.add(creerPageListeEtudiants(), "LISTE_ETU");
    }

    // --- PAGE ACCUEIL ---

    private JPanel creerPageAccueil() {
        JPanel p = new JPanel(new GridBagLayout());
        JLabel lb = new JLabel("Bienvenue dans votre Gestion Scolaire");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lb.setForeground(new Color(33, 150, 243));
        
        JLabel lbS = new JLabel("Utilisez les menus en haut pour commencer.");
        lbS.setFont(new Font("Segoe UI", Font.ITALIC, 16));

        JPanel pSub = new JPanel(new GridLayout(2, 1, 10, 10));
        pSub.add(lb);
        pSub.add(lbS);
        
        p.add(pSub);
        return p;
    }

    // --- PAGES FILIERES ---

    private JPanel creerPageAjoutFiliere() {
        JPanel p = new JPanel(new FlowLayout());
        JTextField txtCode = new JTextField(10);
        JTextField txtDes = new JTextField(20);
        JButton btn = new JButton("Enregistrer la filière");
        
        p.add(new JLabel("Nouveau Code:")); p.add(txtCode);
        p.add(new JLabel("Désignation:")); p.add(txtDes);
        p.add(btn);

        btn.addActionListener(e -> {
            if (service.existeFiliere(txtCode.getText(), txtDes.getText())) {
                JOptionPane.showMessageDialog(this, "Déjà existant !");
            } else {
                service.ajouterFiliere(txtCode.getText(), txtDes.getText());
                JOptionPane.showMessageDialog(this, "Filière créée.");
                txtCode.setText(""); txtDes.setText("");
            }
        });
        return p;
    }

    private JPanel creerPageModifFiliere() {
        JPanel p = new JPanel(new FlowLayout());
        JComboBox<String> cbCodes = new JComboBox<>();
        JTextField txtDes = new JTextField(20);
        JButton btn = new JButton("Mettre à jour");

        p.add(new JLabel("Choisir Code:")); p.add(cbCodes);
        p.add(new JLabel("Nouvelle Désignation:")); p.add(txtDes);
        p.add(btn);

        // Charger les codes au clic sur le menu ou via focus
        cbCodes.addActionListener(e -> {
            String code = (String) cbCodes.getSelectedItem();
            if (code != null) txtDes.setText(service.obtenirNomFiliere(code));
        });

        btn.addActionListener(e -> {
            String code = (String) cbCodes.getSelectedItem();
            if (code != null) {
                service.modifierFiliere(code, txtDes.getText());
                JOptionPane.showMessageDialog(this, "Modifié !");
            }
        });

        p.putClientProperty("reload", (Runnable) () -> {
            cbCodes.removeAllItems();
            service.obtenirListeCodesFilieres().forEach(cbCodes::addItem);
        });

        return p;
    }

    private JPanel creerPageSupprFiliere() {
        JPanel p = new JPanel(new FlowLayout());
        JComboBox<String> cbCodes = new JComboBox<>();
        JComboBox<String> cbNoms = new JComboBox<>();
        JButton btnCode = new JButton("Supprimer par Code");
        JButton btnNom = new JButton("Supprimer par Nom");

        p.add(new JLabel("Par Code:")); p.add(cbCodes); p.add(btnCode);
        p.add(new JLabel(" OU Par Nom:")); p.add(cbNoms); p.add(btnNom);

        btnCode.addActionListener(e -> {
            String code = (String) cbCodes.getSelectedItem();
            if (code != null && JOptionPane.showConfirmDialog(this, "Supprimer la filière " + code + " ?") == JOptionPane.YES_OPTION) {
                service.supprimerFiliere(code);
                ((Runnable)p.getClientProperty("reload")).run();
            }
        });

        btnNom.addActionListener(e -> {
            String nom = (String) cbNoms.getSelectedItem();
            if (nom != null && JOptionPane.showConfirmDialog(this, "Supprimer la filière '" + nom + "' ?") == JOptionPane.YES_OPTION) {
                service.supprimerFiliereParNom(nom);
                ((Runnable)p.getClientProperty("reload")).run();
            }
        });

        p.putClientProperty("reload", (Runnable) () -> {
            cbCodes.removeAllItems();
            service.obtenirListeCodesFilieres().forEach(cbCodes::addItem);
            cbNoms.removeAllItems();
            service.obtenirListeDesignationsFilieres().forEach(cbNoms::addItem);
        });
        return p;
    }

    // --- PAGES ETUDIANTS ---

    private JPanel creerPageAjoutEtudiant() {
        JPanel p = new JPanel(new FlowLayout());
        JTextField txtMat = new JTextField(10);
        JTextField txtNom = new JTextField(15);
        JTextField txtMoy = new JTextField(5);
        JComboBox<String> cbFil = new JComboBox<>();
        JButton btn = new JButton("Inscrire");

        p.add(new JLabel("Mat:")); p.add(txtMat);
        p.add(new JLabel("Nom:")); p.add(txtNom);
        p.add(new JLabel("Moy:")); p.add(txtMoy);
        p.add(new JLabel("Fil:")); p.add(cbFil);
        p.add(btn);

        btn.addActionListener(e -> {
            try {
                if (service.existeEtudiant(txtMat.getText(), txtNom.getText())) {
                    JOptionPane.showMessageDialog(this, "Étudiant déjà existant !");
                } else {
                    service.ajouterEtudiant(txtMat.getText(), txtNom.getText(), 
                                           Double.parseDouble(txtMoy.getText()), 
                                           (String)cbFil.getSelectedItem());
                    JOptionPane.showMessageDialog(this, "Inscrit !");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erreur format !"); }
        });

        p.putClientProperty("reload", (Runnable) () -> {
            cbFil.removeAllItems();
            service.obtenirListeCodesFilieres().forEach(cbFil::addItem);
        });
        return p;
    }

    private JPanel creerPageModifEtudiant() {
        JPanel p = new JPanel(new FlowLayout());
        JComboBox<String> cbMat = new JComboBox<>();
        JTextField txtNom = new JTextField(15);
        JTextField txtMoy = new JTextField(5);
        JComboBox<String> cbFil = new JComboBox<>();
        JButton btn = new JButton("Modifier l'étudiant");

        p.add(new JLabel("Choisir Mat:")); p.add(cbMat);
        p.add(new JLabel("Nom:")); p.add(txtNom);
        p.add(new JLabel("Moy:")); p.add(txtMoy);
        p.add(new JLabel("Fil:")); p.add(cbFil);
        p.add(btn);

        cbMat.addActionListener(e -> {
            String mat = (String) cbMat.getSelectedItem();
            if (mat != null) {
                try (ResultSet rs = service.obtenirDonneesEtudiant(mat)) {
                    if (rs.next()) {
                        txtNom.setText(rs.getString("nom"));
                        txtMoy.setText(String.valueOf(rs.getDouble("moyenne")));
                        cbFil.setSelectedItem(rs.getString("code_filiere"));
                    }
                } catch (SQLException ex) { System.err.println(ex.getMessage()); }
            }
        });

        btn.addActionListener(e -> {
            String mat = (String) cbMat.getSelectedItem();
            if (mat != null) {
                service.modifierEtudiant(mat, txtNom.getText(), Double.parseDouble(txtMoy.getText()), (String)cbFil.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Mis à jour !");
            }
        });

        p.putClientProperty("reload", (Runnable) () -> {
            cbMat.removeAllItems();
            service.obtenirListeMatriculesEtudiants().forEach(cbMat::addItem);
            cbFil.removeAllItems();
            service.obtenirListeCodesFilieres().forEach(cbFil::addItem);
        });
        return p;
    }

    private JPanel creerPageSupprEtudiant() {
        JPanel p = new JPanel(new FlowLayout());
        JComboBox<String> cbMat = new JComboBox<>();
        JComboBox<String> cbNom = new JComboBox<>();
        JButton btnMat = new JButton("Supprimer par Mat");
        JButton btnNom = new JButton("Supprimer par Nom");

        p.add(new JLabel("Par Mat:")); p.add(cbMat); p.add(btnMat);
        p.add(new JLabel(" OU Par Nom:")); p.add(cbNom); p.add(btnNom);

        btnMat.addActionListener(e -> {
            String mat = (String) cbMat.getSelectedItem();
            if (mat != null && JOptionPane.showConfirmDialog(this, "Supprimer l'étudiant " + mat + " ?") == JOptionPane.YES_OPTION) {
                service.supprimerEtudiant(mat);
                ((Runnable)p.getClientProperty("reload")).run();
            }
        });

        btnNom.addActionListener(e -> {
            String nom = (String) cbNom.getSelectedItem();
            if (nom != null && JOptionPane.showConfirmDialog(this, "Supprimer l'étudiant '" + nom + "' ?") == JOptionPane.YES_OPTION) {
                service.supprimerEtudiantParNom(nom);
                ((Runnable)p.getClientProperty("reload")).run();
            }
        });

        p.putClientProperty("reload", (Runnable) () -> {
            cbMat.removeAllItems();
            service.obtenirListeMatriculesEtudiants().forEach(cbMat::addItem);
            cbNom.removeAllItems();
            service.obtenirListeNomsEtudiants().forEach(cbNom::addItem);
        });
        return p;
    }

    private JPanel creerPageListeEtudiants() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel pnlCartes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p.add(new JScrollPane(pnlCartes), BorderLayout.CENTER);

        p.putClientProperty("reload", (Runnable) () -> {
            pnlCartes.removeAll();
            try (ResultSet rs = service.listerTousLesEtudiants()) {
                while (rs.next()) {
                    JPanel carte = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
                    carte.setBorder(BorderFactory.createEtchedBorder());
                    carte.add(new JLabel("Mat: " + rs.getString("matricule")));
                    carte.add(new JLabel("Nom: " + rs.getString("nom")));
                    carte.add(new JLabel("Fil: " + service.obtenirNomFiliere(rs.getString("code_filiere"))));
                    pnlCartes.add(carte);
                }
            } catch (SQLException ex) { System.err.println(ex.getMessage()); }
            pnlCartes.revalidate(); pnlCartes.repaint();
        });
        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = "";
        if (e.getSource() == itemAjouterFil) cmd = "AJOUT_FIL";
        else if (e.getSource() == itemModifierFil) cmd = "MODIF_FIL";
        else if (e.getSource() == itemSupprimerFil) cmd = "SUPPR_FIL";
        else if (e.getSource() == itemAjouterEtu) cmd = "AJOUT_ETU";
        else if (e.getSource() == itemModifierEtu) cmd = "MODIF_ETU";
        else if (e.getSource() == itemSupprimerEtu) cmd = "SUPPR_ETU";
        else if (e.getSource() == itemListeEtu) cmd = "LISTE_ETU";
        else if (e.getSource() == itemAccueil) cmd = "ACCUEIL";
        else if (e.getSource() == itemQuitter) System.exit(0);

        if (!cmd.isEmpty()) {
            // Recharger les données de la page avant de l'afficher
            Component[] comps = pnlPrincipal.getComponents();
            for (Component c : comps) {
                if (c instanceof JPanel) {
                    Runnable reloadAction = (Runnable) ((JPanel) c).getClientProperty("reload");
                    if (reloadAction != null) reloadAction.run();
                }
            }
            cards.show(pnlPrincipal, cmd);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreScolarite().setVisible(true));
    }
}
