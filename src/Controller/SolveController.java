package Controller;

import Model.BackTrackingSolver;
import Model.HumanSolver;
import Model.Sudoku;
import View.Solve;

public class SolveController {

    private Solve solve;
    private BackTrackingSolver bts;
    private HumanSolver hs;

    public SolveController(Solve solve) {
        this.solve = solve;
        this.bts = new BackTrackingSolver();
        this.hs = new HumanSolver();
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

    public void humanSolve() {
        Sudoku currenSudoku = solve.getCurrentSudoku();
        hs.setSudoku(currenSudoku);
        hs.solveLikeHuman();
        currenSudoku = new Sudoku(hs.getBoard());
        solve.setCurrentSudoku(currenSudoku);
    }

    public void humanSolveStep() {
        Sudoku currenSudoku = solve.getCurrentSudoku();
        hs.setSudoku(currenSudoku);
        hs.solveOneStep();
        currenSudoku = new Sudoku(hs.getBoard());
        solve.setCurrentSudoku(currenSudoku);
    }

}