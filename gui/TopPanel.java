package gui;

import javax.swing.*;
import java.awt.*;

import lib.Demineur;

public class TopPanel extends JPanel {
    private JLabel bombLabel;
    private JLabel clickLabel;

    public TopPanel(Demineur game, Runnable onRestart, Runnable onSuggestHelp) {
        setLayout(new BorderLayout());
        bombLabel = new JLabel("Bombes: " + game.getBombCount());
        clickLabel = new JLabel("Clics: 0");

        JButton helpButton = new JButton("Règles");
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
            "Cliquez sur une case pour la révéler. Ne cliquez pas sur une bombe !"));

        JButton suggestButton = new JButton("Suggérer une case");
        suggestButton.addActionListener(e -> onSuggestHelp.run());

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(e -> onRestart.run());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(helpButton);
        buttonPanel.add(suggestButton);
        buttonPanel.add(restartButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(bombLabel);
        leftPanel.add(clickLabel);

        add(leftPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
    }

    public void updateClickCount(int count) {
        clickLabel.setText("Clics: " + count);
    }
}
