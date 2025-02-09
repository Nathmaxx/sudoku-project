package Controller;

import View.SharedSudokuDisplay;
import View.SharedSudokuSelection;

/**
 * Contrôleur pour gérer les interactions entre la vue et le modèle de
 * Multidoku.
 * Cette classe gère l'affichage et la sélection des grilles de Sudoku
 * partagées.
 */
public class MultidokuController {

    /** Sélection de Sudoku partagé */
    private SharedSudokuSelection sharedSudokuSelection;

    /**
     * Constructeur pour initialiser le contrôleur avec la sélection de Sudoku
     * partagé.
     *
     * @param sharedSudokuSelection la sélection de Sudoku partagé
     */
    public MultidokuController(SharedSudokuSelection sharedSudokuSelection) {
        this.sharedSudokuSelection = sharedSudokuSelection;
    }

    /**
     * Affiche le Sudoku partagé en fonction du modèle et de la difficulté
     * sélectionnés.
     *
     * @param selectedPattern    le modèle de Sudoku sélectionné
     * @param selectedDifficulty la difficulté sélectionnée
     */
    public void displaySharedSudoku(String selectedPattern, String selectedDifficulty) {
        SharedSudokuDisplay display = new SharedSudokuDisplay(selectedPattern, selectedDifficulty);
        sharedSudokuSelection.setView(display.getView());
    }

}
