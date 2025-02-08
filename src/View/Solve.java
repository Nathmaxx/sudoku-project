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
import javafx.scene.text.Text;
import utils.ViewManager;

public class Solve extends BaseView {

    private NavigationController navigationController;
    private SolveController solveController;

    private SudokuGrid sudokuGrid;

    private Sudoku currentSudoku;

    private int gridSize = 9;

    private Text message;

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

        Button generateButton = new Button("Changer de taille/Réinitialiser");
        generateButton.setOnAction(event -> solveController.generateEmptySudoku());

        HBox sizeGenerationHBox = new HBox(20);
        sizeGenerationHBox.getChildren().addAll(sizeComboBox, generateButton);
        sizeGenerationHBox.setAlignment(Pos.CENTER);

        this.message = new Text("");
        message.setVisible(false);

        // SudokuGrid affichant la grille du sudoku
        this.currentSudoku = new Sudoku(gridSize);
        this.sudokuGrid = new SudokuGrid(currentSudoku);
        sudokuGrid.setAlignment(Pos.CENTER);

        Button solveBackTrackButton = new Button("Résoudre avec le backtraking");
        solveBackTrackButton.setOnAction(event -> solveController.backTrackSolve());

        Button solveHumanButton = new Button("Résoudre comme un humain");
        solveHumanButton.setOnAction(event -> solveController.humanSolve());

        Button solveHumanOneStep = new Button("Résoudre une seule étape");
        solveHumanOneStep.setOnAction(event -> solveController.humanSolveStep());

        HBox backTrackHBox = new HBox(20);
        backTrackHBox.getChildren().addAll(solveBackTrackButton, solveHumanButton, solveHumanOneStep);
        backTrackHBox.setAlignment(Pos.CENTER);

        // Ajout des éléments dans la vue principale
        mainView.getChildren().addAll(homeButton,
                sizeGenerationHBox,
                message,
                sudokuGrid,
                backTrackHBox);
        mainView.setAlignment(Pos.CENTER);

    }

    public void displayEmptySudoku() {
        currentSudoku = new Sudoku(gridSize);
        sudokuGrid.setSudoku(currentSudoku);
        sudokuGrid.displaySudoku();
    }

    public Sudoku getCurrentSudoku() {
        return this.currentSudoku;
    }

    public void setCurrentSudoku(Sudoku newSudoku) {
        this.currentSudoku = newSudoku;
        sudokuGrid.setSudoku(newSudoku);
        sudokuGrid.displaySudoku();
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public String getMessage() {
        return message.getText();
    }

    public void setMessage(String newMessage) {
        this.message.setText(newMessage);
    }

    public void displayMessage() {
        message.setVisible(true);
    }

    public void hideMessage() {
        message.setVisible(false);
    }

}
