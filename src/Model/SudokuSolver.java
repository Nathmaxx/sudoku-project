package Model;

import java.util.logging.Logger;

/**
 * Classe qui implémente les méthodes de base pour résoudre un Sudoku.
 * Cette classe fournit les méthodes de validation et de vérification communes
 * à tous les algorithmes de résolution.
 */
public class SudokuSolver {

    /** La grille de Sudoku */
    protected int[][] board;

    /** La taille de la grille */
    protected int size;

    /** La taille d'une sous-grille (racine carrée de size) */
    protected int boxSize;
    private static final Logger logger = Logger.getLogger(SudokuSolver.class.getName());

    /**
     * Constructeur par défaut.
     */
    public SudokuSolver() {
        super();
    }

    /**
     * Définit le Sudoku à résoudre.
     * 
     * @param sudoku Le Sudoku à résoudre
     */
    public void setSudoku(Sudoku sudoku) {
        this.board = sudoku.getBoard();
        this.size = sudoku.getSize();
        this.boxSize = (int) Math.sqrt(size);
        logger.info("Sudoku set with size " + size);
    }

    /**
     * Vérifie si un nombre est présent dans une ligne donnée.
     * 
     * @param number Le nombre à vérifier
     * @param row    L'index de la ligne
     * @return true si le nombre est présent, false sinon
     */
    protected boolean isNumberInRow(int number, int row) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == number) {
                logger.info("Number " + number + " found in row " + row);
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un nombre est présent dans une colonne donnée.
     * 
     * @param number Le nombre à vérifier
     * @param col    L'index de la colonne
     * @return true si le nombre est présent, false sinon
     */
    protected boolean isNumberInColumn(int number, int col) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == number) {
                logger.info("Number " + number + " found in column " + col);
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un nombre est présent dans une sous-grille (box) donnée.
     * 
     * @param number Le nombre à vérifier
     * @param row    L'index de la ligne
     * @param column L'index de la colonne
     * @return true si le nombre est présent dans la sous-grille, false sinon
     */
    protected boolean isNumberInBox(int number, int row, int column) {
        int boxRow = row - row % boxSize;
        int boxColumn = column - column % boxSize;

        for (int i = boxRow; i < boxRow + boxSize; i++) {
            for (int j = boxColumn; j < boxColumn + boxSize; j++) {
                if (board[i][j] == number) {
                    logger.info("Number " + number + " found in box at (" + row + ", " + column + ")");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un nombre peut être placé à une position donnée.
     * La position est valide si le nombre n'est pas déjà présent dans :
     * - la ligne
     * - la colonne
     * - la sous-grille (box)
     * 
     * @param number Le nombre à placer
     * @param column L'index de la colonne
     * @param row    L'index de la ligne
     * @return true si le placement est valide, false sinon
     */
    protected boolean isValidPlacement(int number, int column, int row) {
        boolean valid = !isNumberInRow(number, row) &&
                !isNumberInColumn(number, column) &&
                !isNumberInBox(number, row, column);
        logger.info("Placement of number " + number + " at (" + row + ", " + column + ") is "
                + (valid ? "valid" : "invalid"));
        return valid;
    }

    /**
     * Retourne la grille de Sudoku.
     * 
     * @return La grille de Sudoku sous forme de tableau 2D
     */
    public int[][] getBoard() {
        return this.board;
    }
}