package View;

import Model.Sudoku;
import Model.SudokuCreator;
import Model.Solver;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuApp extends Application {

    private GridPane sudokuGrid;
    private int gridSize = 9;
    private Sudoku currentSudoku;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(4, 9, 16);
        sizeComboBox.setValue(gridSize);
        sizeComboBox.setOnAction(_ -> gridSize = sizeComboBox.getValue());

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Facile", "Moyen", "Difficile");
        difficultyComboBox.setValue("Moyen");

        Button createSudokuButton = new Button("Créer un Sudoku");
        createSudokuButton.setOnAction(_ -> {
            int percentage = getRemovalPercentage(difficultyComboBox.getValue());
            SudokuCreator sudokuCreator = new SudokuCreator(gridSize);
            currentSudoku = sudokuCreator.generateSudoku(gridSize, percentage);
            createSudokuGrid(currentSudoku);
        });

        Button resolveSudokuButton = new Button("Résoudre un Sudoku");
        resolveSudokuButton.setOnAction(_ -> solveSudoku());

        sudokuGrid = new GridPane();
        createSudokuGrid(null);

        root.getChildren().addAll(sizeComboBox, difficultyComboBox, createSudokuButton, resolveSudokuButton,
                sudokuGrid);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.show();
    }

    private int getRemovalPercentage(String difficulty) {
        switch (difficulty) {
            case "Facile":
                return 30;
            case "Moyen":
                return 50;
            case "Difficile":
                return 70;
            default:
                return 50;
        }
    }

    private void createSudokuGrid(Sudoku sudoku) {
        sudokuGrid.getChildren().clear();
        sudokuGrid.setGridLinesVisible(true);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                TextField cell = new TextField();
                cell.setPrefWidth(50);
                cell.setPrefHeight(50);
                if (sudoku != null && sudoku.get(row, col) != 0) {
                    cell.setText(String.valueOf(sudoku.get(row, col)));
                    cell.setEditable(false);
                }
                sudokuGrid.add(cell, col, row);
            }
        }
    }

    private void solveSudoku() {
        if (currentSudoku != null) {
            Solver solver = new Solver(currentSudoku);
            if (solver.solveSudoku(0, 0)) {
                createSudokuGrid(currentSudoku);
                System.out.println("Sudoku solved successfully.");
            } else {
                System.out.println("Failed to solve Sudoku.");
            }
        } else {
            System.out.println("No Sudoku puzzle to solve.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}