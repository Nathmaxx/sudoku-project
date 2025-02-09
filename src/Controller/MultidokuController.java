package Controller;

import View.SharedSudokuDisplay;
import View.SharedSudokuSelection;
import utils.ViewManager;

/**
 * Contrôleur pour gérer les interactions entre la vue et le modèle de
 * Multidoku.
 * Cette classe gère l'affichage et la sélection des grilles de Sudoku
 * partagées.
 */
public class MultidokuController {

    /** Sélection de Sudoku partagé */
    private SharedSudokuSelection sharedSudokuSelection;

    /** Manager de vue */
    private ViewManager vm;

    /**
     * Constructeur pour initialiser le contrôleur de Multidoku.
     *
     * @param sharedSudokuSelection la sélection de Sudoku partagé
     * @param vm                    le gestionnaire de vues
     */
    public MultidokuController(SharedSudokuSelection sharedSudokuSelection, ViewManager vm) {
        this.sharedSudokuSelection = sharedSudokuSelection;
        this.vm = vm;
    }

    /**
     * Affiche le Sudoku partagé en fonction du modèle et de la difficulté
     * sélectionnés.
     *
     * @param selectedPattern    le modèle de Sudoku sélectionné
     * @param selectedDifficulty la difficulté sélectionnée
     */
    public void displaySharedSudoku(String selectedPattern, String selectedDifficulty) {
        SharedSudokuDisplay display = new SharedSudokuDisplay(selectedPattern, selectedDifficulty, vm);
        sharedSudokuSelection.setView(display.getView());
    }

}
