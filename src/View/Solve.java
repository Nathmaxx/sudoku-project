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

/**
 * Classe représentant la vue de résolution de Sudoku.
 * Gère l'affichage de la grille de Sudoku et les interactions avec
 * l'utilisateur.
 */
public class Solve extends BaseView {

    /** Contrôleur de navigation pour gérer les changements de vue */
    private NavigationController navigationController;

    /** Contrôleur de résolution pour gérer la logique de résolution */
    private SolveController solveController;

    /** Grille de Sudoku affichée dans la vue */
    private SudokuGrid sudokuGrid;

    /** Sudoku actuellement affiché */
    private Sudoku currentSudoku;

    /** Taille de la grille de Sudoku */
    private int gridSize = 9;

    private Text message;

    /**
     * Constructeur pour initialiser la vue de résolution de Sudoku.
     *
     * @param vm le gestionnaire de vues
     */
    public Solve(ViewManager vm) {
        super();
        this.navigationController = new NavigationController(vm);
        this.solveController = new SolveController(this);
        initializeUI();
    }

    /**
     * Initialise l'interface utilisateur de la vue de résolution de Sudoku.
     * Configure les composants et les ajoute à la scène.
     */
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

    /**
     * Affiche un Sudoku vide dans la vue.
     */
    public void displayEmptySudoku() {
        currentSudoku = new Sudoku(gridSize);
        sudokuGrid.setSudoku(currentSudoku);
        sudokuGrid.displaySudoku();
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
        sudokuGrid.setSudoku(newSudoku);
        sudokuGrid.displaySudoku();
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
     * Retourne le message affiché dans la vue.
     *
     * @return le message affiché dans la vue
     */
    public String getMessage() {
        return message.getText();
    }

    /**
     * Définit le message à afficher dans la vue.
     *
     * @param newMessage le nouveau message à afficher
     */
    public void setMessage(String newMessage) {
        this.message.setText(newMessage);
    }

    /**
     * Affiche un message dans la vue.
     *
     * @param message le message à afficher
     */
    public void displayMessage() {
        message.setVisible(true);
    }

    /**
     * Cache le message affiché dans la vue.
     */
    public void hideMessage() {
        message.setVisible(false);
    }

}
