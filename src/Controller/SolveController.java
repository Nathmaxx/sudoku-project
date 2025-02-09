package Controller;

import Model.BackTrackingSolver;
import Model.HumanSolver;
import Model.Sudoku;
import View.Solve;

/**
 * Contrôleur pour gérer les interactions entre la vue et le modèle de
 * résolution de Sudoku.
 * Cette classe gère la génération et la résolution des grilles de Sudoku.
 */
public class SolveController {

    /** Vue de résolution de Sudoku */
    private Solve solve;

    /** Solveur utilisant l'algorithme de backtracking */
    private BackTrackingSolver bts;

    /** Solveur utilisant des techniques humaines */
    private HumanSolver hs;

    /**
     * Constructeur pour initialiser le contrôleur avec la vue de résolution.
     *
     * @param solve la vue de résolution de Sudoku
     */
    public SolveController(Solve solve) {
        this.solve = solve;
        this.bts = new BackTrackingSolver();
        this.hs = new HumanSolver();
    }

    /**
     * Génère un Sudoku vide et l'affiche dans la vue.
     */
    public void generateEmptySudoku() {
        solve.displayEmptySudoku();
    }

    /**
     * Résout le Sudoku actuel en utilisant l'algorithme de backtracking.
     */
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

    /**
     * Résout le Sudoku actuel en utilisant des techniques humaines.
     */
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

    /**
     * Effectue une seule étape de résolution en utilisant des techniques humaines.
     */
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