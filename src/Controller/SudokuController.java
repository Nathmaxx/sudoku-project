package Controller;

import Model.Solver;
import Model.Sudoku;
import Model.SudokuCreator;
import View.Generation;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public class SudokuController {

    private Generation generation;

    public SudokuController(Generation generation) {
        this.generation = generation;
    }

    public void solveSudoku(Sudoku currentSudoku) {
        if (currentSudoku != null) {
            Solver solver = new Solver(currentSudoku);
            if (solver.solveSudoku(0, 0)) {
                generation.createSudokuGrid(currentSudoku);
            } else {
                System.out.println("Impossible de résoudre le Sudoku.");
            }
        }
    }

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
                generation.createSudokuGrid(generation.getCurrentSudoku());
                progressBar.setVisible(false);
            }

            @Override
            protected void running() {
                progressBar.setVisible(true);
            }
        };

        new Thread(task).start();
    }

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
