package Controller;

import View.Solve;

public class SolveController {

    private Solve solve;

    public SolveController(Solve solve) {
        this.solve = solve;
    }

    public void generateEmptySudoku() {
        solve.displayEmptySudoku();
    }
}