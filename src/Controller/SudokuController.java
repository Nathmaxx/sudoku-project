package Controller;

import Model.BackTrackingSolver;
import Model.Sudoku;
import Model.SudokuCreator;
import View.Generation;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

/**
 * Contrôleur pour gérer les interactions entre la vue et le modèle de Sudoku.
 * Cette classe gère la génération et la résolution des grilles de Sudoku.
 */
public class SudokuController {

    /** Vue de génération de Sudoku */
    private Generation generation;

    /**
     * Constructeur pour initialiser le contrôleur avec la vue de génération.
     *
     * @param generation la vue de génération de Sudoku
     */
    public SudokuController(Generation generation) {
        this.generation = generation;
    }

    /**
     * Résout le Sudoku actuel en utilisant l'algorithme de backtracking.
     *
     * @param currentSudoku le Sudoku à résoudre
     */
    public void solveSudoku(Sudoku currentSudoku) {
        if (currentSudoku != null) {
            BackTrackingSolver bts = new BackTrackingSolver();
            bts.setSudoku(currentSudoku);

            if (bts.backTrackingSolve()) {
                generation.displaySudoku(new Sudoku(bts.getBoard()));
            } else {
                System.out.println("Impossible de résoudre le Sudoku.");
            }

        }
    }

    /**
     * Génère un nouveau Sudoku de la taille spécifiée.
     *
     * @param size la taille de la grille de Sudoku à générer
     */
    public void generateSudoku(int gridSize, String difficulty, ProgressBar progressBar) {
        int percentage = getRemovalPercentage(difficulty);

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SudokuCreator sudokuCreator = new SudokuCreator(gridSize);
                sudokuCreator.setProgressListener(progress -> updateProgress(progress, 1.0));
                generation.setCurrentSudoku(sudokuCreator.generateSudoku(gridSize, percentage));
                return null;
            }

            @Override
            protected void succeeded() {
                generation.displaySudoku(generation.getCurrentSudoku());
                progressBar.setVisible(false);
            }

            @Override
            protected void running() {
                progressBar.setVisible(true);
            }
        };
        new Thread(task).start();
        generation.showSolveButtons();
    }

    /**
     * Retourne le pourcentage de cases à retirer pour générer un puzzle de Sudoku.
     * Ce pourcentage est utilisé pour déterminer la difficulté du puzzle.
     *
     * @return le pourcentage de cases à retirer
     */
    private int getRemovalPercentage(String difficulty) {
        switch (difficulty) {
            case "Facile":
                return 40;
            case "Moyen":
                return 60;
            case "Difficile":
                return 80;
            default:
                return 60;
        }
    }
}
