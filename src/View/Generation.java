package View;

import java.util.List;
import java.util.Map;

import Model.Solver;
import Model.Sudoku;
import Model.SudokuCreator;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Generation extends BaseView {

    private int gridSize = 9;
    private ProgressBar progressBar;
    private Sudoku currentSudoku;
    private int currentStepIndex = 0;
    private List<Map.Entry<Integer, Integer>> steps;
    private GridPane sudokuGrid;

    public Generation() {

        initializeUI();
    }

    @Override
    protected void initializeUI() {
        ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(4, 9, 16, 25, 36, 49);
        sizeComboBox.setValue(gridSize);
        sizeComboBox.setOnAction(event -> gridSize = sizeComboBox.getValue());

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Facile", "Moyen", "Difficile");
        difficultyComboBox.setValue("Moyen");

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setVisible(false);

        Button createSudokuButton = new Button("Créer un Sudoku");
        createSudokuButton.setOnAction(event -> {
            int percentage = getRemovalPercentage(difficultyComboBox.getValue());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    SudokuCreator sudokuCreator = new SudokuCreator(gridSize);
                    sudokuCreator.setProgressListener(progress -> updateProgress(progress, 1.0));
                    currentSudoku = sudokuCreator.generateSudoku(gridSize, percentage);
                    return null;
                }

                @Override
                protected void succeeded() {
                    createSudokuGrid(currentSudoku);
                    progressBar.setVisible(false);
                }

                @Override
                protected void running() {
                    progressBar.setVisible(true);
                }
            };

            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        });

        Button solveSudokuButton = new Button("Résoudre");
        solveSudokuButton.setOnAction(event -> {
            if (currentSudoku != null) {
                Solver solver = new Solver(currentSudoku);
                if (solver.solveSudoku(0, 0)) {
                    createSudokuGrid(currentSudoku);
                } else {
                    System.out.println("Impossible de résoudre le Sudoku.");
                }
            }
        });

        Button solveSudokuWithStepButton = new Button("Résoudre l'étape suivante");
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
                    createSudokuGrid(currentSudoku);
                    System.out.println("Step " + currentStepIndex + " done");
                    currentStepIndex++;
                } else {
                    System.out.println("Sudoku solved");
                }
            }
        });

        sudokuGrid = new GridPane();
        sudokuGrid.setPadding(new Insets(30));

        mainView.getChildren().addAll(sizeComboBox, difficultyComboBox, createSudokuButton, solveSudokuButton,
                solveSudokuWithStepButton, progressBar,
                sudokuGrid);
    }

    private int getRemovalPercentage(String difficulty) {
        switch (difficulty) {
            case "Facile":
                return 40;
            case "Moyen":
                return 60;
            case "Difficile":
                return 80;
            default:
                return 60;
        }
    }

    private void createSudokuGrid(Sudoku sudoku) {
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

}
