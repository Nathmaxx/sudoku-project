package View;

import java.util.List;
import java.util.Map;

import Controller.NavigationController;
import Controller.SudokuController;
import Model.Solver;
import Model.Sudoku;
import View.Components.HomeButton;
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
    private int currentStepIndex = 0;

    private Sudoku currentSudoku;

    private ComboBox<String> difficultyComboBox;

    private SudokuGrid sudokuGrid;

    private Button solveSudokuButton;
    private Button solveSudokuWithStepButton;

    private List<Map.Entry<Integer, Integer>> steps;
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
        ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(4, 9, 16, 25, 36, 49);
        sizeComboBox.setValue(gridSize);
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

        // Bouton permettanst de résoudre l'étape suivante
        this.solveSudokuWithStepButton = new Button("Résoudre l'étape suivante");
        solveSudokuWithStepButton.setVisible(false);
        solveSudokuWithStepButton.setOnAction(event -> {
            if (currentSudoku != null) {
                System.out.println("Solving step " + currentStepIndex);
                if (steps == null) {
                    System.out.println("First IF Solving Sudoku with steps");
                    Solver solver = new Solver(currentSudoku);
                    steps = solver.solveSudokuWithSteps(0, 0);
                    currentStepIndex = 0;
                    System.out.println("Nombre d'étapes: " + steps.size());
                }
                if (currentStepIndex < steps.size()) {
                    Map.Entry<Integer, Integer> step = steps.get(currentStepIndex);
                    int position = step.getKey();
                    int value = step.getValue();
                    int row = position / currentSudoku.getSize();
                    int col = position % currentSudoku.getSize();
                    currentSudoku.set(row, col, value);
                    System.out.println("Setting " + value + " at position " + row + ", " + col);
                    displaySudoku(currentSudoku);
                    System.out.println("Step " + currentStepIndex + " done");
                    currentStepIndex++;
                } else {
                    System.out.println("Sudoku solved");
                }
            }
        });

        // HBox contenant les boutons de résolution
        HBox solveButtonsHBox = new HBox(20);
        solveButtonsHBox.setAlignment(Pos.CENTER);
        solveButtonsHBox.getChildren().addAll(solveSudokuButton, solveSudokuWithStepButton);

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
        solveSudokuWithStepButton.setVisible(true);
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
