package lib;

import java.util.ArrayList;
import java.util.List;

/*
 Classe représentant une cellule dans le jeu du démineur.
 Chaque cellule peut contenir une bombe ou non, être révélée ou non,
 et connaît ses cellules adjacentes.
 */

public class AbstractCell {

    // Attributs de la cellule
    private boolean bomb;                     // Indique si cette cellule contient une bombe
    private boolean revealed;                 // Indique si cette cellule a été révélée
    private AbstractCell[] adjacentCells;     // Tableau des cellules adjacentes (jusqu'à 8)
    private int adjacentCellCount;            // Nombre de cellules adjacentes ajoutées


    //Constructeur avec paramètre : indique si la cellule contient une bombe.
    public AbstractCell(boolean bomb){
        this.bomb = bomb;
        this.revealed = false;
        this.adjacentCells = new AbstractCell[8];  // Une cellule peut avoir jusqu'à 8 voisines
        this.adjacentCellCount = 0;
    }

    //Constructeur par défaut : cellule sans bombe
    public AbstractCell() {
        this.bomb = false;
        this.revealed = false;
        this.adjacentCells = new AbstractCell[8];
        this.adjacentCellCount = 0;
    }

    // Getters et Setters

    // Vérifie si la cellule contient une bombe
    public boolean isBomb() {
        return this.bomb;
    }

    // Vérifie si la cellule a été révélée
    public boolean isRevealed() {
        return this.revealed;
    }

    // Marque la cellule comme révélée
    public void setRevealed() {
        this.revealed = true;
    }

    // Gestion des cellules adjacentes 

    // Ajoute une cellule adjacente à la cellule.
    public void addAdjacentCell(AbstractCell cell) {
        if (adjacentCellCount < adjacentCells.length) {
            adjacentCells[adjacentCellCount++] = cell;
        } else {
            System.err.println("Erreur, le tableau est plein");
        }
    }

    // Compte combien de cellules adjacentes contiennent une bombe.
    public int getAdjacentBombs() {
        int count = 0;
        for (int i = 0; i < adjacentCellCount; i++) {
            if (adjacentCells[i].isBomb()) {
                count++;
            }
        }
        return count;
    }
}
