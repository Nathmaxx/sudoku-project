
package Controller;

import View.Accueil;
import View.Generation;
import View.SharedSudokuSelection;
import View.Solve;

public class HomeController {

    public enum Views {
        GENERATION,
        SOLVE,
        MULTIDOKU
    }

    private Accueil accueil;
    private Generation generation;
    private Solve solve;
    private SharedSudokuSelection sharedSudoku;

    public HomeController(Accueil accueil) {
        this.accueil = accueil;
        this.generation = new Generation();
        this.solve = new Solve();
        this.sharedSudoku = new SharedSudokuSelection();
    }

    public void handleChangeView(Views view) {
        switch (view) {
            case GENERATION:
                accueil.setView(generation.getView());
                break;
            case SOLVE:
                accueil.setView(solve.getView());
                break;
            case MULTIDOKU:
                accueil.setView(sharedSudoku.getView());
                break;

        }
    }
}