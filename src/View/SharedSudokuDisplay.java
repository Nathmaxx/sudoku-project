package View;

import Model.SharedSudoku;
import Controller.NavigationController;
import Model.SharedArea;
import Model.SudokuCreator;
import View.Components.HomeButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import utils.ViewManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;

/**
 * Classe représentant l'affichage d'un Sudoku partagé.
 * Gère l'affichage des grilles de Sudoku partagées et les interactions avec
 * l'utilisateur.
 */
public class SharedSudokuDisplay extends BaseView {

    private NavigationController nc;

    /** Premier Sudoku partagé */
    private SharedSudoku sharedSudoku1;

    /** Deuxième Sudoku partagé */
    private SharedSudoku sharedSudoku2;

    /** Troisième Sudoku partagé */
    private SharedSudoku sharedSudoku3;

    /** Modèle de Sudoku sélectionné */
    private String pattern;

    /** Difficulté sélectionnée */
    private String difficulty;

    /** Grille de Sudoku fusionnée pour l'affichage */
    private GridPane mergedSudokuGrid;

    /** Bouton pour régénérer le Sudoku */
    private Button regenerateButton;

    /**
     * Constructeur pour initialiser l'affichage de Sudoku partagé.
     *
     * @param pattern    le modèle de Sudoku sélectionné
     * @param difficulty la difficulté sélectionnée
     */
    public SharedSudokuDisplay(String pattern, String difficulty, ViewManager vm) {
        super();
        this.difficulty = difficulty;
        this.pattern = pattern;
        this.nc = new NavigationController(vm);
        initializeUI();
    }

    /**
     * Initialise l'interface utilisateur de l'affichage de Sudoku partagé.
     * Configure les composants et les ajoute à la scène.
     */
    @Override
    protected void initializeUI() {
        System.out.println("Selected pattern: " + pattern);
        System.out.println("Selected difficulty: " + difficulty);

        if ("Pattern 1".equals(pattern)) {
            createPattern1Sudokus();
        } else if ("Pattern 2".equals(pattern)) {
            createPattern2Sudokus();
        }

        HomeButton homeButton = new HomeButton(nc);

        mergedSudokuGrid = new GridPane();
        mergedSudokuGrid.setPadding(new Insets(30));
        createMergedSudokuGrid();

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(event -> solveBothSudokus());

        regenerateButton = new Button("Regenerate Grid");
        regenerateButton.setOnAction(event -> regenerateGrid());
        regenerateButton.setVisible(false);

        mainView.getChildren().addAll(homeButton, mergedSudokuGrid, solveButton, regenerateButton);
        mainView.setAlignment(Pos.CENTER);
        mainView.setPadding(new Insets(20));
    }

    /**
     * Crée les Sudokus pour le modèle de pattern 1.
     * Génère deux Sudokus partageant une zone commune.
     */
    private void createPattern1Sudokus() {
        // Generate the first Sudoku
        SudokuCreator sudokuCreator1 = new SudokuCreator(9);
        int[][] board1 = sudokuCreator1.generateSudoku(9, 60).getBoard();

        // Extract the shared 3x3 square from the first Sudoku
        int[][] sharedSquare = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sharedSquare[i][j] = board1[6 + i][6 + j];
            }
        }

        // Create the shared area
        SharedArea sharedArea = new SharedArea(sharedSquare);

        // Generate the second Sudoku with the shared area filled
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board2[i][j] = sharedSquare[i][j];
            }
        }

        SudokuCreator sudokuCreator2 = new SudokuCreator(9);
        board2 = sudokuCreator2.generateSudokuWithPreFilled(9, 60, board2, 0, 0, 2, 2).getBoard();

        // Create the shared Sudokus
        sharedSudoku1 = new SharedSudoku(board1, sharedArea);
        sharedSudoku2 = new SharedSudoku(board2, sharedArea);
    }

    /**
     * Crée les Sudokus pour le modèle de pattern 2.
     * Génère trois Sudokus partageant des zones communes.
     */
    private void createPattern2Sudokus() {
        // Generate the first Sudoku
        SudokuCreator sudokuCreator1 = new SudokuCreator(9);
        int[][] board1 = sudokuCreator1.generateSudoku(9, 60).getBoard();

        // Extract the first shared 3x3 square from the first Sudoku
        int[][] sharedSquare1 = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sharedSquare1[i][j] = board1[6 + i][6 + j];
            }
        }

        // Create the first shared area
        SharedArea sharedArea1 = new SharedArea(sharedSquare1);

        // Generate the second Sudoku with the first shared area filled
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board2[i][j] = sharedSquare1[i][j];
            }
        }

        SudokuCreator sudokuCreator2 = new SudokuCreator(9);
        board2 = sudokuCreator2.generateSudokuWithPreFilled(9, 60, board2, 0, 0, 2, 2).getBoard();

        // Extract the second shared 3x3 square from the second Sudoku
        int[][] sharedSquare2 = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sharedSquare2[i][j] = board2[3 + i][3 + j];
            }
        }

        // Create the second shared area
        SharedArea sharedArea2 = new SharedArea(sharedSquare2);

        // Generate the third Sudoku with the second shared area filled
        int[][] board3 = new int[9][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board3[i][j] = sharedSquare2[i][j];
            }
        }

        SudokuCreator sudokuCreator3 = new SudokuCreator(9);
        board3 = sudokuCreator3.generateSudokuWithPreFilled(9, 60, board3, 0, 0, 2, 2).getBoard();

        // Create the shared Sudokus
        sharedSudoku1 = new SharedSudoku(board1, new SharedArea[] { sharedArea1, sharedArea2 });
        sharedSudoku2 = new SharedSudoku(board2, sharedArea1);
        sharedSudoku3 = new SharedSudoku(board3, sharedArea2);
    }

    /**
     * Crée la grille fusionnée de Sudoku pour l'affichage.
     * Combine les grilles de Sudoku partagées en une seule grille pour l'affichage.
     */
    private void createMergedSudokuGrid() {
        mergedSudokuGrid.getChildren().clear();
        int cellSize = 28;

        // Create the first Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (row >= 6 && col >= 6)
                    continue; // Skip the shared area
                TextField cell = new TextField();
                cell.setPrefHeight(cellSize);
                cell.setPrefWidth(cellSize);
                cell.setMaxHeight(cellSize);
                cell.setMaxWidth(cellSize);
                cell.setMinHeight(cellSize);
                cell.setMinWidth(cellSize);

                int value = sharedSudoku1.get(row, col);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                }
                cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                mergedSudokuGrid.add(cell, col, row);
            }
        }

        // Create the second Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 6; col < 15; col++) {
                if (row < 3 && col < 3)
                    continue; // Skip the shared area
                TextField cell = new TextField();
                cell.setPrefHeight(cellSize);
                cell.setPrefWidth(cellSize);
                cell.setMaxHeight(cellSize);
                cell.setMaxWidth(cellSize);
                cell.setMinHeight(cellSize);
                cell.setMinWidth(cellSize);

                int value = sharedSudoku2.get(row, col - 6);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                }
                cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                mergedSudokuGrid.add(cell, col, row + 6);
            }
        }

        // Create the third Sudoku grid if it exists
        if (sharedSudoku3 != null) {
            for (int row = 0; row < 9; row++) {
                for (int col = 12; col < 21; col++) {
                    if (row < 3 && col < 3)
                        continue; // Skip the shared area
                    TextField cell = new TextField();
                    cell.setPrefHeight(cellSize);
                    cell.setPrefWidth(cellSize);
                    cell.setMaxHeight(cellSize);
                    cell.setMaxWidth(cellSize);
                    cell.setMinHeight(cellSize);
                    cell.setMinWidth(cellSize);

                    int value = sharedSudoku3.get(row, col - 12);
                    if (value != 0) {
                        cell.setText(String.valueOf(value));
                        cell.setEditable(false);
                    }
                    cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                    mergedSudokuGrid.add(cell, col, row + 12);
                }
            }
        }

        // Create the shared 3x3 grids
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField cell = new TextField();
                cell.setPrefHeight(cellSize);
                cell.setPrefWidth(cellSize);
                cell.setMaxHeight(cellSize);
                cell.setMaxWidth(cellSize);
                cell.setMinHeight(cellSize);
                cell.setMinWidth(cellSize);

                int value = sharedSudoku1.get(6 + i, 6 + j);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                }
                cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                mergedSudokuGrid.add(cell, j + 6, i + 6);
            }
        }
    }

    /**
     * Résout le Multidoku en utilisant un algorithme de backtracking.
     * Remplit les cellules vides avec des valeurs valides.
     *
     * @param row la ligne de départ
     * @param col la colonne de départ
     * @return true si le Multidoku est résolu, false sinon
     */
    private boolean solveMultidoku(int row, int col) {
        // Check if we have reached the end of the grid
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true; // All cells are filled
            }
        }
        if (sharedSudoku1.get(row, col) != 0) {
            return solveMultidoku(row + 1, col);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(sharedSudoku1, row, col, num) &&
                    isValid(sharedSudoku2, row, col, num) &&
                    (sharedSudoku3 == null || isValid(sharedSudoku3, row, col, num))) {

                sharedSudoku1.set(row, col, num);
                if (row >= 6 && col >= 6) {
                    sharedSudoku2.set(row - 6, col - 6, num);
                    if (sharedSudoku3 != null && row >= 12 && col >= 12) {
                        sharedSudoku3.set(row - 12, col - 12, num);
                    }
                }

                if (solveMultidoku(row + 1, col)) {
                    return true;
                }

                sharedSudoku1.set(row, col, 0);
                if (row >= 6 && col >= 6) {
                    sharedSudoku2.set(row - 6, col - 6, 0);
                    if (sharedSudoku3 != null && row >= 12 && col >= 12) {
                        sharedSudoku3.set(row - 12, col - 12, 0);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un nombre peut être placé en toute sécurité à une position donnée.
     * 
     * @param sudoku le Sudoku à vérifier
     * @param row    la ligne à vérifier
     * @param col    la colonne à vérifier
     * @param num    le nombre à vérifier
     * @return true si le nombre peut être placé en toute sécurité, false sinon
     */
    private boolean isValid(SharedSudoku sudoku, int row, int col, int num) {
        for (int x = 0; x < 9; x++) {
            if (sudoku.get(row, x) == num || sudoku.get(x, col) == num ||
                    sudoku.get(row - row % 3 + x / 3, col - col % 3 + x % 3) == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * Résout les deux Sudokus partagés.
     * Utilise un algorithme de backtracking pour remplir les cellules vides avec
     * des valeurs valides.
     */
    private void solveBothSudokus() {
        if (solveMultidoku(0, 0)) {
            createMergedSudokuGrid();
            System.out.println("All Sudokus solved!");
            regenerateButton.setVisible(false);
        } else {
            System.out.println("Unable to solve all Sudokus.");
            showAlert("Unable to solve the Sudoku. Please try regenerating the grid.");
            regenerateButton.setVisible(true);
        }
    }

    /**
     * Affiche une alerte avec un message donné.
     *
     * @param message le message à afficher dans l'alerte
     */
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche une alerte avec un message donné.
     *
     * @param message le message à afficher dans l'alerte
     */
    private void regenerateGrid() {
        if ("Pattern 1".equals(pattern)) {
            createPattern1Sudokus();
        } else if ("Pattern 2".equals(pattern)) {
            createPattern2Sudokus();
        }
        createMergedSudokuGrid();
        regenerateButton.setVisible(false);
    }
}
