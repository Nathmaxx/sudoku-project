package View;

import Model.SharedSudoku;
import Model.SharedArea;
import Model.Solver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SharedSudokuDisplay extends Application {

    private SharedSudoku sharedSudoku;
    private String pattern;
    private String difficulty;
    private GridPane sudokuGrid;

    public SharedSudokuDisplay(String pattern, String difficulty) {
        this.pattern = pattern;
        this.difficulty = difficulty;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shared Sudoku");

        System.out.println("Selected pattern: " + pattern);
        System.out.println("Selected difficulty: " + difficulty);

        int[][] sampleBoard = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        SharedArea sharedArea = new SharedArea(sampleBoard);
        sharedSudoku = new SharedSudoku(sampleBoard, sharedArea);

        sudokuGrid = new GridPane();
        sudokuGrid.setPadding(new Insets(30));

        createSudokuGrid(sharedSudoku);

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(event -> {
            Solver solver = new Solver(sharedSudoku);
            if (solver.solveSudoku(0, 0)) {
                createSudokuGrid(sharedSudoku);
                System.out.println("Sudoku solved!");
            } else {
                System.out.println("Unable to solve Sudoku.");
            }
        });

        VBox vbox = new VBox(10, sudokuGrid, solveButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSudokuGrid(SharedSudoku sudoku) {
        sudokuGrid.getChildren().clear();
        int size = sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);
        int cellSize = 28;

        if (size > 9) {
            cellSize = 34;
        }
        GridPane[][] subGrids = new GridPane[sqrtSize][sqrtSize];

        for (int subRow = 0; subRow < sqrtSize; subRow++) {
            for (int subCol = 0; subCol < sqrtSize; subCol++) {
                GridPane subGrid = new GridPane();
                subGrid.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
                subGrids[subRow][subCol] = subGrid;
                sudokuGrid.add(subGrid, subCol, subRow);
            }
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                TextField cell = new TextField();
                cell.setPrefHeight(cellSize);
                cell.setPrefWidth(cellSize);
                cell.setMaxHeight(cellSize);
                cell.setMaxWidth(cellSize);
                cell.setMinHeight(cellSize);
                cell.setMinWidth(cellSize);

                int value = sudoku.get(row, col);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                }
                cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                int subGridRow = row / sqrtSize;
                int subGridCol = col / sqrtSize;
                subGrids[subGridRow][subGridCol].add(cell, col % sqrtSize, row % sqrtSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}