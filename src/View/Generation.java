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

/**
 * Classe représentant la vue de génération de Sudoku.
 * Gère l'affichage des options de génération et la grille de Sudoku.
 */
public class Generation extends BaseView {

    /** Contrôleur pour gérer les interactions avec la vue de génération */
    private NavigationController navigationController;

    /** COntrôleur pour gérer les intéracitons avec le sudoku */
    private SudokuController sudokuController;

    /** Taille de la grille */
    private int gridSize = 9;

    /** Sudoku actuellement affiché */
    private Sudoku currentSudoku;

    /** ComboBox des trois difficultés */
    private ComboBox<String> difficultyComboBox;

    /** Grille de Sudoku affichée dans la vue */
    private SudokuGrid sudokuGrid;

    /** Bouton pour résoudre le Sudoku */
    private Button solveSudokuButton;

    /** Barre de progression */
    private ProgressBar progressBar;

    /**
     * Constructeur pour initialiser la vue de génération de Sudoku.
     *
     * @param vm le gestionnaire de vues
     */
    public Generation(ViewManager viewManager) {
        this.navigationController = new NavigationController(viewManager);
        this.sudokuController = new SudokuController(this);
        initializeUI();
    }

    /**
     * Initialise l'interface utilisateur de la vue de génération de Sudoku.
     * Configure les composants et les ajoute à la scène.
     */
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

    /**
     * Affiche le Sudoku dans la grille.
     *
     * @param sudoku le Sudoku à afficher
     */
    public void displaySudoku(Sudoku sudoku) {
        sudokuGrid.setSudoku(sudoku);
        sudokuGrid.displaySudoku();
    }

    /**
     * Affiche les boutons de résolution.
     */
    public void showSolveButtons() {
        solveSudokuButton.setVisible(true);
    }

    /**
     * Retourne le niveau de difficulté sélectionné.
     *
     * @return le niveau de difficulté sélectionné
     */
    public String getDifficulty() {
        return difficultyComboBox.getValue();
    }

    /**
     * Retourne la taille de la grille de Sudoku.
     *
     * @return la taille de la grille de Sudoku
     */
    public int getGridSize() {
        return this.gridSize;
    }

    /**
     * Retourne le Sudoku actuellement affiché.
     *
     * @return le Sudoku actuellement affiché
     */
    public Sudoku getCurrentSudoku() {
        return this.currentSudoku;
    }

    /**
     * Définit le Sudoku à afficher dans la vue.
     *
     * @param newSudoku le nouveau Sudoku à afficher
     */
    public void setCurrentSudoku(Sudoku newSudoku) {
        this.currentSudoku = newSudoku;
    }

    /**
     * Retourne la barre de progression utilisée pour afficher l'avancement de la
     * génération.
     *
     * @return la barre de progression
     */
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

}
