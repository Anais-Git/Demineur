package lib;

import exceptions.GameLostException;
import exceptions.GameWonException;
import exceptions.InvalidMoveException;

// Classe principale représentant le jeu du démineur
public class Demineur {

    // Attributs principaux : dimensions, nombre de bombes, grille de cellules
    private int rows;
    private int cols;
    private int bombs;
    private AbstractCell[][] cells;
    private int clickCount = 0;              // Compteur de cellules révélées (non-bombe)
    private int cellsToReveal;               // Nombre de cellules à révéler pour gagner
    private boolean firstClickDone = false;  // Indique si le premier clic a été fait

    // Constructeur : initialise les dimensions et le nombre de bombes
    public Demineur(int rows, int cols, int bombs) {
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
        this.cells = new AbstractCell[rows][cols];
        this.cellsToReveal = rows * cols - bombs;
    }

    // Accesseurs pour les dimensions
    public int getRowCount() { 
        return this.rows; 
    }

    public int getColCount() { 
        return this.cols; 
    }

    /*
    Révèle une cellule à la position donnée
    Initialise le plateau lors du premier clic
    Lève une exception si le joueur clique sur une bombe ou gagne
    */
    public void revealCell(int row, int col) throws InvalidMoveException, GameLostException, GameWonException {
        // Vérifie les coordonnées valides
        if (row < 0 || row >= getRowCount() || col < 0 || col >= getColCount()) {
            throw new InvalidMoveException("Coordonnées de cellule invalides.");
        }

        // Initialise la grille au premier clic sans placer de bombe 
        if (!firstClickDone) {
            initializeCells(row, col);
            setAdjacentCells();
            this.firstClickDone = true;
        }

        AbstractCell cell = getCell(row, col);

        // Si la cellule est déjà révélée
        if (cell.isRevealed()) {
            throw new InvalidMoveException("Cellule déjà révélée.");
        }

        // Si c’est une bombe c'est perdu, fin de partie
        if (cell.isBomb()) {
            throw new GameLostException();
        }

        this.clickCount++;
        revealCellRecursive(row, col);

        // Vérifie si toutes les cellules non-bombe sont révélées, 
        // si oui c'est la victoire, fin de partie
        if (clickCount == cellsToReveal) {
            throw new GameWonException();
        }
    }

    // Révélation récursive des cellules adjacentes vides
    private void revealCellRecursive(int row, int col) {
        AbstractCell cell = getCell(row, col);

        if (cell == null || cell.isRevealed() || cell.isBomb()) return;

        cell.setRevealed();

        // Si la cellule a des bombes adjacentes, on arrête la récursion
        if (cell.getAdjacentBombs() > 0) return;

        // Exploration des 8 directions autour
        int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < getRowCount() && newCol >= 0 && newCol < getColCount()) {
                revealCellRecursive(newRow, newCol);
            }
        }
    }

    // Retourne une cellule selon ses coordonnées
    public AbstractCell getCell(int row, int col) {
        if (row >= 0 && row < getRowCount() && col >= 0 && col < getColCount()) {
            return this.cells[row][col];
        }
        return null;
    }

    // Retourne le nombre de clics effectués
    public int getClickCount() { 
        return this.clickCount; 
    }

    // Retourne le nombre de bombes de la partie
    public int getBombCount() { 
        return this.bombs; 
        }

    // Initialise la grille et place les bombes (en évitant la zone de sécurité du premier clic)
    private void initializeCells(int safeRow, int safeCol) {
        // Crée d'abord des cellules vides 
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                this.cells[i][j] = new AbstractCell();
            }
        }

        // Place les bombes aléatoirement, mais pas dans la zone sûre
        int placedBombs = 0;
        while (placedBombs < this.bombs) {
            int row = (int) (Math.random() * getRowCount());
            int col = (int) (Math.random() * getColCount());

            if (isInSafeZone(row, col, safeRow, safeCol)) continue;

            AbstractCell cell = getCell(row, col);
            if (!cell.isBomb()) {
                this.cells[row][col] = new AbstractCell(true); // Cellule avec bombe
                placedBombs++;
            }
        }
    }

    // Vérifie si une cellule est dans la zone sûre du premier clic
    private boolean isInSafeZone(int row, int col, int safeRow, int safeCol) {
        return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
    }

    // Associe chaque cellule à ses cellules adjacentes pour compter les bombes voisines
    private void setAdjacentCells() {
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                for (int[] dir : directions) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if (ni >= 0 && ni < getRowCount() && nj >= 0 && nj < getColCount()) {
                        this.getCell(i, j).addAdjacentCell(this.getCell(ni, nj));
                    }
                }
            }
        }
    }

    // Tente de suggérer une cellule sûre (non révélée, non bombe)
    public int[] attemptRandomReveal() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                AbstractCell cell = getCell(i, j);
                if (!cell.isRevealed() && !cell.isBomb()) {
                    return new int[]{i, j}; // Retourne la première cellule sûre trouvée
                }
            }
        }
        return null; // Si aucune cellule disponible
    }
}
