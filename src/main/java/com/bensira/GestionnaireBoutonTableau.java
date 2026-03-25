package com.bensira;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gère le rendu et l'édition de boutons dans une cellule de JTable.
 */
public class GestionnaireBoutonTableau extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private final JButton bouton;
    private String label;
    private final Action dAction;

    public interface Action {
        void executer(int ligne);
    }

    public GestionnaireBoutonTableau(String texte, Color couleurFond, Action action) {
        this.label = texte;
        this.dAction = action;
        this.bouton = new JButton(texte);
        this.bouton.setBackground(couleurFond);
        this.bouton.setForeground(Color.WHITE);
        this.bouton.setOpaque(true);
        this.bouton.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        this.bouton.addActionListener(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return bouton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return bouton;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtenir la ligne depuis le tableau actif
        JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, bouton);
        if (table != null) {
            int ligne = table.convertRowIndexToModel(table.getEditingRow());
            fireEditingStopped();
            dAction.executer(ligne);
        }
    }
}
