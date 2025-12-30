package gui;

import javax.swing.*;
import java.awt.*;

public class SetupDialog {

    public int[] showDialog() {
        JTextField rowsField = new JTextField("10");
        JTextField colsField = new JTextField("10");
        JTextField bombsField = new JTextField("10");

        // Mise en forme des champs
        Dimension fieldSize = new Dimension(80, 24);
        rowsField.setPreferredSize(fieldSize);
        colsField.setPreferredSize(fieldSize);
        bombsField.setPreferredSize(fieldSize);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre de lignes :"), gbc);
        gbc.gridx = 1;
        formPanel.add(rowsField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre de colonnes :"), gbc);
        gbc.gridx = 1;
        formPanel.add(colsField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Nombre de bombes :"), gbc);
        gbc.gridx = 1;
        formPanel.add(bombsField, gbc);

        int result = JOptionPane.showConfirmDialog(
            null,
            formPanel,
            "Paramètres du Démineur",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int rows = Integer.parseInt(rowsField.getText().trim());
                int cols = Integer.parseInt(colsField.getText().trim());
                int bombs = Integer.parseInt(bombsField.getText().trim());

                if (rows <= 0 || cols <= 0 || bombs <= 0 || bombs >= rows * cols) {
                    throw new IllegalArgumentException("Valeurs hors limites.");
                }

                return new int[]{rows, cols, bombs};
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Veuillez entrer des entiers valides.\nLe nombre de bombes doivent être positives et inférieures au nombre total de cases.",
                    "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE
                );
                return showDialog(); // Re-tente la saisie
            }
        }

        return null; // Annulation
    }
}
