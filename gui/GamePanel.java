package gui;

import javax.swing.*;
import java.awt.*;

import lib.Demineur;
import lib.AbstractCell;
import exceptions.*;


public class GamePanel extends JPanel {
    private final JButton[][] buttons;
    private final Demineur game;
    private final DemineurUI parent;
    private static final int CELL_SIZE = 40;

    public GamePanel(Demineur game, DemineurUI parent) {
        this.game = game;
        this.parent = parent;
        this.setLayout(new GridLayout(game.getRowCount(), game.getColCount(), 1, 1));
        this.setBackground(Color.GRAY);

        buttons = new JButton[game.getRowCount()][game.getColCount()];
        initButtons();
    }

    private void initButtons() {
        for (int r = 0; r < game.getRowCount(); r++) {
            for (int c = 0; c < game.getColCount(); c++) {
                JButton button = new JButton();
                int row = r, col = c;

                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.setBackground(new Color(116, 216, 212));
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                button.setFont(new Font("Arial", Font.BOLD, 14));

                button.addActionListener(e -> handleReveal(row, col));

                buttons[row][col] = button;
                this.add(button);
            }
        }
    }

    private void handleReveal(int row, int col) {
        try {
            game.revealCell(row, col);
            // updateButton(row, col);
            updateAllButtons();
        } catch (GameLostException ex) {
            buttons[row][col].setText("B");
            buttons[row][col].setBackground(new Color(200, 50, 50));
            buttons[row][col].setForeground(Color.WHITE);
            revealAllBombs();
            parent.showMessage(ex.getMessage());
        } catch (GameWonException ex) {
            updateAllButtons();
            parent.showMessage(ex.getMessage());
        } catch (InvalidMoveException ex) {
            parent.showMessage(ex.getMessage());
        }

        parent.topPanel.updateClickCount(game.getClickCount());
    }

    public void highlightSuggestion(int row, int col) {
        buttons[row][col].setBackground(new Color(255, 255, 180));
    }

    private void updateButton(int row, int col) {
        AbstractCell cell = game.getCell(row, col);
        JButton button = buttons[row][col];
        button.setEnabled(false);
        button.setBackground(new Color(240, 240, 240));

        if (!cell.isBomb()) {
            int n = cell.getAdjacentBombs();
            if (n >= 0) {
                button.setText(String.valueOf(n));
                button.setForeground(getColorForNumber(n));
            }
        }
    }

    private void updateAllButtons() {
        for (int r = 0; r < game.getRowCount(); r++) {
            for (int c = 0; c < game.getColCount(); c++) {
                AbstractCell cell = game.getCell(r, c);
                JButton button = buttons[r][c];
                if (cell.isRevealed()) {
                    button.setEnabled(false);
                    button.setBackground(new Color(240, 240, 240));

                    if (!cell.isBomb()) {
                        int n = cell.getAdjacentBombs();
                        if (n >= 0) {
                            button.setText(String.valueOf(n));
                            // System.out.println(getColorForNumber(n));
                            button.setForeground(getColorForNumber(n));
                        }
                    }

                }
            }
        }
    }

    private void revealAllBombs() {
        for (int r = 0; r < game.getRowCount(); r++) {
            for (int c = 0; c < game.getColCount(); c++) {
                AbstractCell cell = game.getCell(r, c);
                JButton button = buttons[r][c];

                if (cell.isBomb()) {
                    button.setText("B");
                    button.setBackground(Color.LIGHT_GRAY);
                }

                button.setEnabled(false);
            }
        }
    }

    private Color getColorForNumber(int n) {
        switch (n) {
            case 0: return new Color(0x0097A7); // cyan foncé
            case 1: return new Color(0x1976D2); // bleu
            case 2: return new Color(0x388E3C); // vert
            case 3: return new Color(0xD32F2F); // rouge
            case 4: return new Color(0x7B1FA2); // violet
            case 5: return new Color(0xF57C00); // orange
            case 6: return new Color(0x0097A7); // cyan foncé
            case 7: return Color.BLACK;
            case 8: return Color.DARK_GRAY;
            default: return Color.GRAY;
        }
    }
}
