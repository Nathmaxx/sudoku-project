package Model;

/**
 * Classe qui gère plusieurs Sudokus partageant des cases communes.
 * Cette classe permet de gérer un ensemble de Sudokus interconnectés.
 */
public class Multidoku {

    /** Tableau des Sudokus interconnectés */
    public SharedSudoku[] sudokus;

    /**
     * Constructeur qui initialise un Multidoku avec un tableau de Sudokus.
     *
     * @param sudokus tableau de SharedSudoku à interconnecter
     */
    public Multidoku(SharedSudoku[] sudokus) {
        this.sudokus = sudokus;
    }

    /**
     * Retourne le tableau des Sudokus interconnectés.
     *
     * @return tableau des SharedSudoku
     */
    public SharedSudoku[] getSudokus() {
        return this.sudokus;
    }

    /**
     * Ajoute un nouveau Sudoku au Multidoku.
     * Crée un nouveau tableau plus grand et y copie les Sudokus existants
     * avant d'ajouter le nouveau.
     *
     * @param sudoku le SharedSudoku à ajouter
     */
    public void addSudoku(SharedSudoku sudoku) {
        SharedSudoku[] newSudokus = new SharedSudoku[this.sudokus.length + 1];
        for (int i = 0; i < this.sudokus.length; i++) {
            newSudokus[i] = this.sudokus[i];
        }
        newSudokus[this.sudokus.length] = sudoku;
        this.sudokus = newSudokus;
    }
}
