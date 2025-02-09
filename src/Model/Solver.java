package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {

    private Sudoku sudoku;

    public Solver(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    private int[][] solution;

    public Solver(SharedSudoku sudoku) {
        this.sudoku = sudoku;
        this.solution = new int[9][9];
    }

    private boolean isSafe(int row, int col, int num) {
        // Check if it's safe to place the number in the given cell
        // Ensure that the shared area constraints are checked
        int subGridSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public void reset() {
        this.solution = new int[9][9];
    }

    public boolean checkRow(int row) {
        int[] values = new int[this.sudoku.getSize()];
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            values[i] = this.sudoku.get(row, i);
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkColumn(int column) {
        int[] values = new int[this.sudoku.getSize()];
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            values[i] = this.sudoku.get(i, column);
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkSquare(int startX, int startY, int sizeX, int sizeY) {
        boolean[] seen = new boolean[sudoku.getSize() + 1];
        for (int i = startX; i < startX + sizeX; i++) {
            for (int j = startY; j < startY + sizeY; j++) {
                int value = sudoku.get(i, j);
                if (value < 1 || value > sudoku.getSize() || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }

    public boolean checkSudoku() {
        return isValid(0, 0, 0);
    }

    public boolean isValid(int row, int col, int num) {
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            if (this.sudoku.get(row, i) == num || this.sudoku.get(i, col) == num) {
                return false;
            }
        }
        int subGridSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solveMultiSudoku(int row, int col) {
        if (row == this.sudoku.getSize()) {
            row = 0;
            if (++col == this.sudoku.getSize()) {
                // Store the current solution in the solution array
                for (int i = 0; i < this.sudoku.getSize(); i++) {
                    for (int j = 0; j < this.sudoku.getSize(); j++) {
                        solution[i][j] = this.sudoku.get(i, j);
                    }
                }
                return true; // Sudoku solved
            }
        }
        if (this.sudoku.get(row, col) != 0) {
            return solveSudoku(row + 1, col); // Skip filled cells
        }
        for (int num = 1; num <= this.sudoku.getSize(); num++) {
            if (isValid(row, col, num)) {
                this.sudoku.set(row, col, num); // Try placing the number
                if (solveSudoku(row + 1, col)) {
                    return true; // If it leads to a solution, return true
                }
                this.sudoku.set(row, col, 0); // Backtrack if it doesn't work
            }
        }
        return false; // No valid number found for this cell
    }

    public boolean solveSudoku(int row, int col) {
        if (row == this.sudoku.getSize()) {
            row = 0;
            if (++col == this.sudoku.getSize()) {
                return true;
            }
        }
        if (this.sudoku.get(row, col) != 0) {
            return solveSudoku(row + 1, col);
        }
        Solver solver = new Solver(this.sudoku);
        for (int num = 1; num <= this.sudoku.getSize(); num++) {
            if (solver.isValid(row, col, num)) {
                this.sudoku.set(row, col, num);
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                this.sudoku.set(row, col, 0);
            }
        }
        return false;
    }

    public List<Map.Entry<Integer, Integer>> solveSudokuWithSteps(int row, int col) {
        Sudoku sudokuCopy = new Sudoku(this.sudoku);
        Solver solver = new Solver(sudokuCopy);
        solver.solveSudoku(row, col);

        List<Map.Entry<Integer, Integer>> steps = new ArrayList<>();
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            for (int j = 0; j < this.sudoku.getSize(); j++) {
                if (this.sudoku.get(i, j) == 0 && sudokuCopy.get(i, j) != 0) {
                    steps.add(new HashMap.SimpleEntry<>(i * this.sudoku.getSize() + j, sudokuCopy.get(i, j)));
                }
            }
        }
        return steps;
    }
}