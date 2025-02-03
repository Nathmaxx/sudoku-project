package View;

import Model.SharedSudoku;
import Model.SharedArea;
import Model.Solver;
import Model.SudokuCreator;
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

    private SharedSudoku sharedSudoku1;
    private SharedSudoku sharedSudoku2;
    private String pattern;
    private String difficulty;
    private GridPane mergedSudokuGrid;

    public SharedSudokuDisplay(String pattern, String difficulty) {
        this.pattern = pattern;
        this.difficulty = difficulty;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shared Sudoku");

        System.out.println("Selected pattern: " + pattern);
        System.out.println("Selected difficulty: " + difficulty);

        if ("Pattern 1".equals(pattern)) {
            createPattern1Sudokus();
        }

        mergedSudokuGrid = new GridPane();
        mergedSudokuGrid.setPadding(new Insets(30));
        createMergedSudokuGrid();

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(event -> {
            Solver solver1 = new Solver(sharedSudoku1);
            Solver solver2 = new Solver(sharedSudoku2);
            if (solver1.solveSudoku(0, 0) && solver2.solveSudoku(0, 0)) {
                createMergedSudokuGrid();
                System.out.println("Sudokus solved!");
            } else {
                System.out.println("Unable to solve Sudokus.");
            }
        });

        VBox vbox = new VBox(10, mergedSudokuGrid, solveButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

        //sysout sharedArea
        System.out.println("Shared Area: ");
        sharedArea.print();

        // Generate the second Sudoku with the shared area filled
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board2[i][j] = sharedSquare[i][j];
            }
        }
        //sysout board2
        System.out.println("Board 2: ");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board2[i][j] + " ");
            }
            System.out.println();
        }

        SudokuCreator sudokuCreator2 = new SudokuCreator(9);
        board2 = sudokuCreator2.generateSudokuWithPreFilled(9, 60, board2).getBoard();

        // Create the shared Sudokus
        sharedSudoku1 = new SharedSudoku(board1, sharedArea);
        sharedSudoku2 = new SharedSudoku(board2, sharedArea);
    }

    private void createMergedSudokuGrid() {
        mergedSudokuGrid.getChildren().clear();
        int cellSize = 28;

        // Create the first Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (row >= 6 && col >= 6) continue; // Skip the shared area
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
                if (row < 3 && col < 3) continue; // Skip the shared area
                TextField cell = new TextField();
                cell.setPrefHeight(cellSize);
                cell.setPrefWidth(cellSize);
                cell.setMaxHeight(cellSize);
                cell.setMaxWidth(cellSize);
                cell.setMinHeight(cellSize);
                cell.setMinWidth(cellSize);

                int value = sharedSudoku2.get(row, col-6);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                }
                cell.setStyle("-fx-background-radius: 0; -fx-border-radius: 0;");
                mergedSudokuGrid.add(cell, col, row + 6);
            }
        }

        // Create the shared 3x3 grid
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

    public static void main(String[] args) {
        launch(args);
    }
}