package Controller;

import Model.BackTrackingSolver;
import Model.Sudoku;
import View.Solve;

public class SolveController {

    private Solve solve;
    private BackTrackingSolver bts;

    public SolveController(Solve solve) {
        this.solve = solve;
        this.bts = new BackTrackingSolver();

    }

    public void generateEmptySudoku() {
        solve.displayEmptySudoku();
    }

    public void backTrackSolve() {
        Sudoku currentSudoku = solve.getCurrentSudoku();
        bts.setSudoku(currentSudoku);
        bts.backTrackingSolve();
        currentSudoku = new Sudoku(bts.getBoard());
        solve.setCurrentSudoku(currentSudoku);

    }

}