package Model;

/**
 * Classe représentant une grille de Sudoku.
 * Gère la structure de données et les opérations de base sur la grille.
 */
public class Sudoku {

    /** La grille de Sudoku */
    int[][] board;

    /**
     * Constructeur créant une grille de Sudoku à partir d'un tableau existant.
     * Initialise la grille avec les valeurs du tableau fourni.
     *
     * @param board le tableau 2D contenant les valeurs initiales du Sudoku
     */
    public Sudoku(int[][] board) {
        this.board = board;
    }

    /**
     * Constructeur créant une grille de Sudoku vide de taille donnée.
     *
     * @param size la taille de la grille (généralement 9)
     */
    public Sudoku(int size) {
        this.board = new int[size][size];
    }

    /**
     * Constructeur par copie créant une nouvelle instance de Sudoku
     * à partir d'un Sudoku existant.
     *
     * @param sudoku le Sudoku à copier
     */
    public Sudoku(Sudoku sudoku) {
        this.board = new int[sudoku.board.length][sudoku.board[0].length];
        for (int i = 0; i < sudoku.board.length; i++) {
            for (int j = 0; j < sudoku.board[0].length; j++) {
                this.board[i][j] = sudoku.board[i][j];
            }
        }
    }

    /**
     * Définit la valeur d'une case de la grille.
     *
     * @param i     la ligne de la case
     * @param j     la colonne de la case
     * @param value la valeur à placer
     */
    public void set(int i, int j, int value) {
        this.board[i][j] = value;
    }

    /**
     * Retourne la valeur d'une case de la grille.
     *
     * @param i la ligne de la case
     * @param j la colonne de la case
     * @return la valeur de la case
     */
    public int get(int i, int j) {
        return this.board[i][j];
    }

    /**
     * Retourne la grille de Sudoku.
     *
     * @return le tableau 2D représentant la grille
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Retourne la taille de la grille.
     *
     * @return la taille de la grille
     */
    public int getSize() {
        return this.board.length;
    }

    /**
     * Retourne une représentation textuelle simple de la grille.
     * Chaque ligne est suivie d'un retour à la ligne.
     *
     * @return une chaîne représentant la grille
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                result += this.board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Retourne une représentation textuelle formatée de la grille.
     * Inclut des séparateurs pour les sous-grilles et un formatage amélioré.
     *
     * @return une chaîne représentant la grille avec formatage
     */
    public String afficher() {
        String result = "";
        int subGridSize = (int) Math.sqrt(this.board.length);

        for (int i = 0; i < this.board.length; i++) {
            if (i % subGridSize == 0) {
                for (int j = 0; j < this.board[0].length; j++) {
                    result += "--";
                }
                result += "----\n";
            }
            for (int j = 0; j < this.board[0].length; j++) {
                if (j % subGridSize == 0) {
                    result += "|";
                }
                result += this.board[i][j] + " ";
            }
            result += "|\n";
        }
        for (int j = 0; j < this.board[0].length; j++) {
            result += "--";
        }
        result += "----\n";
        return result;
    }
}
