package View.Components;

import Model.Sudoku;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Classe représentant la grille de Sudoku dans l'interface utilisateur.
 * Gère l'affichage et les interactions avec la grille de Sudoku.
 */
public class SudokuGrid extends GridPane {

    /** Sudoku affiché dans la grille */
    private Sudoku sudoku;

    /** Cellules du sudoku */
    private TextField[][] cells;

    /**
     * Constructeur par défaut pour initialiser une grille de Sudoku vide.
     */
    public SudokuGrid() {
    }

    /**
     * Constructeur pour initialiser une grille de Sudoku avec un Sudoku donné.
     *
     * @param sudoku le Sudoku à afficher dans la grille
     */
    public SudokuGrid(Sudoku sudoku) {
        this.sudoku = sudoku;
        displaySudoku();
    }

    /**
     * Définit le Sudoku à afficher dans la grille.
     *
     * @param sudoku le Sudoku à afficher
     */
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
        displaySudoku();
    }

    /**
     * Affiche le Sudoku dans la grille.
     * Met à jour les cellules de la grille avec les valeurs du Sudoku.
     */
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

    /**
     * Met à jour l'affichage de la grille de Sudoku.
     * Met à jour les cellules de la grille avec les valeurs actuelles du Sudoku.
     */
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

    /**
     * Retourne les cellules de la grille de Sudoku.
     *
     * @return un tableau 2D de TextField représentant les cellules de la grille
     */
    public TextField[][] getCells() {
        return cells;
    }

    /**
     * Retourne le Sudoku actuellement affiché dans la grille.
     *
     * @return le Sudoku actuellement affiché
     */
    public Sudoku getSudoku() {
        return sudoku;
    }
}