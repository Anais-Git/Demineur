package gui;

import javax.swing.*;
import java.awt.*;

import lib.Demineur;

public class DemineurUI extends JFrame {
    TopPanel topPanel;
    GamePanel gamePanel;
    Demineur game;

    public DemineurUI() {
        showSetupDialog();
    }

    private void showSetupDialog() {
        SetupDialog dialog = new SetupDialog();
        int[] params = dialog.showDialog();
        if (params != null) {
            game = new Demineur(params[0], params[1], params[2]);
            initGameWindow();
        } else {
            System.exit(0);
        }
    }

    private void initGameWindow() {
        setTitle("Démineur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        topPanel = new TopPanel(game, this::restart, this::attemptHelp);
        gamePanel = new GamePanel(game, this);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void restart() {
        dispose();
        SwingUtilities.invokeLater(DemineurUI::new);
    }

    public void attemptHelp() {
        int[] coords = game.attemptRandomReveal();
        if (coords != null) {
            gamePanel.highlightSuggestion(coords[0], coords[1]);
        } else {
            showMessage("Aucune suggestion possible.");
        }
        topPanel.updateClickCount(game.getClickCount());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DemineurUI::new);
    }
}
