package View;

import Controller.NavigationController;
import Controller.SudokuController;
import Model.Sudoku;
import View.Components.HomeButton;
import View.Components.SizeComboBox;
import View.Components.SudokuGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.ViewManager;

public class Generation extends BaseView {

    private NavigationController navigationController;
    private SudokuController sudokuController;

    private int gridSize = 9;

    private Sudoku currentSudoku;

    private ComboBox<String> difficultyComboBox;

    private SudokuGrid sudokuGrid;

    private Button solveSudokuButton;

    private ProgressBar progressBar;

    public Generation(ViewManager viewManager) {
        this.navigationController = new NavigationController(viewManager);
        this.sudokuController = new SudokuController(this);
        initializeUI();
    }

    @Override
    protected void initializeUI() {

        // Bouton permettant de retourner à l'accueil
        HomeButton homeButton = new HomeButton(navigationController);

        // VBox contenant le homeButton
        VBox homeVBox = new VBox();
        homeVBox.getChildren().addAll(homeButton);
        homeVBox.setAlignment(Pos.CENTER);
        homeVBox.setPadding(new Insets(20, 0, 20, 0));

        // Combobox contenant les différentes tailles de Sudoku à générer
        SizeComboBox sizeComboBox = new SizeComboBox(gridSize);
        sizeComboBox.setOnAction(event -> gridSize = sizeComboBox.getValue());

        // Combobox contenant le sniveaux de difficulté pour les sudokus
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Facile", "Moyen", "Difficile");
        difficultyComboBox.setValue("Moyen");

        // Bouton permettant de créeer un sodoku et l'afficher
        Button createSudokuButton = new Button("Créer un Sudoku");
        createSudokuButton.setOnAction(event -> {
            String difficulty = difficultyComboBox.getValue();
            sudokuController.generateSudoku(gridSize, difficulty, progressBar);
        });

        // Création de la barre de chargement
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setVisible(false);

        // HBox contenant les boutons pour générer le sudoku
        HBox sudokuGenerationOptionsHBox = new HBox(20);
        sudokuGenerationOptionsHBox.setAlignment(Pos.CENTER);
        sudokuGenerationOptionsHBox.getChildren().addAll(sizeComboBox, difficultyComboBox, createSudokuButton);

        // Création de la grille du sudoku
        sudokuGrid = new SudokuGrid();
        sudokuGrid.setAlignment(Pos.CENTER);
        sudokuGrid.setPadding(new Insets(30, 0, 30, 0));

        // Bouton permettant de résoudre le sudoku
        this.solveSudokuButton = new Button("Résoudre");
        solveSudokuButton.setVisible(false);
        solveSudokuButton.setOnAction(event -> sudokuController.solveSudoku(currentSudoku));

        // HBox contenant les boutons de résolution
        HBox solveButtonsHBox = new HBox(20);
        solveButtonsHBox.setAlignment(Pos.CENTER);
        solveButtonsHBox.getChildren().addAll(solveSudokuButton);

        // Ajout de tous les éléments dans la vue principale
        mainView.getChildren().addAll(homeVBox,
                sudokuGenerationOptionsHBox,
                sudokuGrid,
                solveButtonsHBox,
                progressBar);
    }

    public void displaySudoku(Sudoku sudoku) {
        sudokuGrid.setSudoku(sudoku);
        sudokuGrid.displaySudoku();
    }

    public void showSolveButtons() {
        solveSudokuButton.setVisible(true);
    }

    public String getDifficulty() {
        return difficultyComboBox.getValue();
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public Sudoku getCurrentSudoku() {
        return this.currentSudoku;
    }

    public void setCurrentSudoku(Sudoku newSudoku) {
        this.currentSudoku = newSudoku;
    }

    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

}
