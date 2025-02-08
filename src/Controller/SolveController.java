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
        solve.hideMessage();
        Sudoku currentSudoku = solve.getCurrentSudoku();
        bts.setSudoku(currentSudoku);
        boolean sudokuSolved = bts.backTrackingSolve();
        if (sudokuSolved) {
            solve.setMessage("Le sudoku a été résolu par le backtracking");

        } else {
            solve.setMessage("Le sudoku n'est pas correct");
        }
        solve.displayMessage();
        currentSudoku = new Sudoku(bts.getBoard());
        solve.setCurrentSudoku(currentSudoku);

    }

    public void humanSolve() {
        solve.hideMessage();
        Sudoku currenSudoku = solve.getCurrentSudoku();
        hs.setSudoku(currenSudoku);
        boolean sudokuSolved = hs.solveLikeHuman();
        if (sudokuSolved) {
            solve.setMessage("Le sudoku a été résolu comme un humain");

        } else {
            solve.setMessage("Le sudoku n'est pas correct ou ne peux pas être plus résolu avec la méthode humain.");
        }
        solve.displayMessage();
        currenSudoku = new Sudoku(hs.getBoard());
        solve.setCurrentSudoku(currenSudoku);
    }

    public void humanSolveStep() {
        solve.hideMessage();
        Sudoku currenSudoku = solve.getCurrentSudoku();
        hs.setSudoku(currenSudoku);
        boolean sudokuSolved = hs.solveOneStep();
        if (sudokuSolved) {
            solve.setMessage("L'étape a été réalisée");

        } else {
            solve.setMessage("Le sudoku n'est pas correct ou il n'est pas possible de réaliser plus d'étapes");
        }
        solve.displayMessage();
        currenSudoku = new Sudoku(hs.getBoard());
        solve.setCurrentSudoku(currenSudoku);
    }

}