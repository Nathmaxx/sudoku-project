package Controller;

import Model.Solver;
import Model.Sudoku;
import View.Generation;

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
}
