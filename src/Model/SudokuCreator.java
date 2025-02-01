package Model;

import java.util.function.Consumer;

public class SudokuCreator {
    private Sudoku sudoku;
    private Consumer<Double> progressListener;

    public SudokuCreator(int size) {
        this.sudoku = new Sudoku(size);
    }

    public Sudoku generateSudoku(int size, int percentage) {
        this.sudoku = new Sudoku(size);
        this.fillSudoku();
        System.out.println("Removing " + percentage + "% of numbers from the sudoku");
        this.removeNumbers(percentage);
        return this.sudoku;
    }

    private void removeNumbers(int percentage) {
        int size = this.sudoku.getSize();
        int totalCells = size * size;
        int cellsToRemove = (int) (totalCells * (percentage / 100.0));

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (this.sudoku.get(row, col) != 0) {
                this.sudoku.set(row, col, 0);
                cellsToRemove--;
            }
        }
    }

    private void fillSudoku() {
        System.out.println("Filling sudoku of size " + this.sudoku.getSize());
        int size = this.sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);

        // Fill the diagonal sub-grids
        for (int i = 0; i < size; i += sqrtSize) {
            fillDiagonalSubGrid(i, i);
            updateProgress((double) i / size);
        }

        // Solve the partially filled Sudoku
        Solver solver = new Solver(this.sudoku);
        if (solver.solveSudoku(0, 0)) {
            System.out.println("Sudoku successfully created:");
        } else {
            System.out.println("Failed to create a valid Sudoku.");
        }
    }

    private void fillDiagonalSubGrid(int row, int col) {
        int size = this.sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                int num;
                do {
                    num = (int) (Math.random() * size) + 1;
                } while (!isSafeToPlace(row + i, col + j, num));
                this.sudoku.set(row + i, col + j, num);
            }
        }
    }

    private boolean isSafeToPlace(int row, int col, int num) {
        for (int x = 0; x < this.sudoku.getSize(); x++) {
            if (this.sudoku.get(row, x) == num || this.sudoku.get(x, col) == num) {
                return false;
            }
        }
        int sqrtSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % sqrtSize;
        int startCol = col - col % sqrtSize;
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setProgressListener(Consumer<Double> progressListener) {
        this.progressListener = progressListener;
    }

    private void updateProgress(double progress) {
        if (progressListener != null) {
            progressListener.accept(progress);
        }
    }

    public Sudoku generateSudokuWithPreFilled(int size, int removalPercentage, int[][] preFilled) {
        this.sudoku = new Sudoku(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.sudoku.set(i, j, preFilled[i][j]);
            }
        }

        fillRemainingCells();

        removeNumbers(removalPercentage);

        return this.sudoku;
    }

    private void fillRemainingCells() {
        int size = this.sudoku.getSize();
        Solver solver = new Solver(this.sudoku);
        solver.solveSudoku(0, 0);
    }
}