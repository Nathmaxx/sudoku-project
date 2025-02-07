import View.Accueil;
import View.Generation;
import View.SharedSudokuSelection;
import View.Solve;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.ViewManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewManager vm = new ViewManager(primaryStage);

        Accueil accueil = new Accueil(vm);
        Generation generation = new Generation(vm);
        Solve solve = new Solve(vm);
        SharedSudokuSelection sharedSudokuSelection = new SharedSudokuSelection(vm);

        vm.addView("generation", generation.getView());
        vm.addView("accueil", accueil.getView());
        vm.addView("solve", solve.getView());
        vm.addView("multidoku", sharedSudokuSelection.getView());

        vm.showView("accueil");

        primaryStage.setTitle("Sudoku Application");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}