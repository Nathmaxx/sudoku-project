package View.Components;

import Model.Sudoku;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SudokuGrid extends GridPane {
    private Sudoku sudoku;
    private TextField[][] cells;

    public SudokuGrid() {
    }

    public SudokuGrid(Sudoku sudoku) {
        this.sudoku = sudoku;
        displaySudoku();
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
        displaySudoku();
    }

    public void displaySudoku() {
        this.getChildren().clear();
        int size = sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);
        int cellSize = 30;

        GridPane[][] subGrids = new GridPane[sqrtSize][sqrtSize];
        cells = new TextField[size][size];

        // Création des sous-grilles
        for (int subRow = 0; subRow < sqrtSize; subRow++) {
            for (int subCol = 0; subCol < sqrtSize; subCol++) {
                GridPane subGrid = new GridPane();
                subGrid.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
                subGrids[subRow][subCol] = subGrid;
                this.add(subGrid, subCol, subRow);
            }
        }

        // Remplissage des cellules
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(cellSize, cellSize);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px;");

                int value = sudoku.getBoard()[row][col];
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setStyle(cell.getStyle() + "; -fx-background-color: #f0f0f0;");
                }

                final int finalRow = row;
                final int finalCol = col;
                cell.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        try {
                            int intValue = Integer.parseInt(newValue);
                            if (intValue >= 1 && intValue <= size) {
                                sudoku.getBoard()[finalRow][finalCol] = intValue;
                            } else {
                                cell.setText(oldValue);
                            }
                        } catch (NumberFormatException e) {
                            cell.setText(oldValue);
                        }
                    } else {
                        sudoku.getBoard()[finalRow][finalCol] = 0;
                    }
                });

                cells[row][col] = cell;
                subGrids[row / sqrtSize][col / sqrtSize].add(cell, col % sqrtSize, row % sqrtSize);
            }
        }
    }

    public void updateDisplay() {
        int size = sudoku.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = sudoku.getBoard()[row][col];
                if (value != 0) {
                    cells[row][col].setText(String.valueOf(value));
                } else {
                    cells[row][col].setText("");
                }
            }
        }
    }

    public TextField[][] getCells() {
        return cells;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }
}