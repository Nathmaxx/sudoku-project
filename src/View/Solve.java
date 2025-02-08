package View;

import Controller.NavigationController;
import Controller.SolveController;
import Model.Sudoku;
import View.Components.HomeButton;
import View.Components.SizeComboBox;
import View.Components.SudokuGrid;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import utils.ViewManager;

public class Solve extends BaseView {

    private NavigationController navigationController;
    private SolveController solveController;

    private SudokuGrid sudokuGrid;

    private Sudoku currentSudoku;

    private int gridSize = 9;

    public Solve(ViewManager vm) {
        super();
        this.navigationController = new NavigationController(vm);
        this.solveController = new SolveController(this);
        initializeUI();
    }

    @Override
    protected void initializeUI() {

        // Bouton permettant de retourner à l'accueil
        HomeButton homeButton = new HomeButton(navigationController);

        SizeComboBox sizeComboBox = new SizeComboBox(gridSize);
        sizeComboBox.setOnAction(event -> gridSize = sizeComboBox.getValue());

        Button generateButton = new Button("Générer");
        generateButton.setOnAction(event -> solveController.generateEmptySudoku());

        HBox sizeGenerationHBox = new HBox(20);
        sizeGenerationHBox.getChildren().addAll(sizeComboBox, generateButton);
        sizeGenerationHBox.setAlignment(Pos.CENTER);

        // SudokuGrid affichant la grille du sudoku
        this.currentSudoku = new Sudoku(gridSize);
        this.sudokuGrid = new SudokuGrid(currentSudoku);
        sudokuGrid.setAlignment(Pos.CENTER);

        Button solveButton = new Button("Résoudre");

        // Ajout des éléments dans la vue principale
        mainView.getChildren().addAll(homeButton,
                sizeGenerationHBox,
                sudokuGrid,
                solveButton);
        mainView.setAlignment(Pos.CENTER);

    }

    public void displayEmptySudoku() {
        currentSudoku = new Sudoku(gridSize);
        sudokuGrid.setSudoku(currentSudoku);
        sudokuGrid.displaySudoku();
    }

}
