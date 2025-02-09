package Model;

import java.util.logging.Logger;

public class SudokuSolver {

    protected int[][] board;
    protected int size;
    protected int boxSize;
    private static final Logger logger = Logger.getLogger(SudokuSolver.class.getName());

    public SudokuSolver() {
    }

    public void setSudoku(Sudoku sudoku) {
        this.board = sudoku.getBoard();
        this.size = sudoku.getSize();
        this.boxSize = (int) Math.sqrt(size);
        logger.info("Sudoku set with size " + size);
    }

    protected boolean isNumberInRow(int number, int row) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == number) {
                logger.info("Number " + number + " found in row " + row);
                return true;
            }
        }
        return false;
    }

    protected boolean isNumberInColumn(int number, int col) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == number) {
                logger.info("Number " + number + " found in column " + col);
                return true;
            }
        }
        return false;
    }

    protected boolean isNumberInBox(int number, int row, int column) {
        int boxRow = row - row % boxSize;
        int boxColumn = column - column % boxSize;

        for (int i = boxRow; i < boxRow + boxSize; i++) {
            for (int j = boxColumn; j < boxColumn + boxSize; j++) {
                if (board[i][j] == number) {
                    logger.info("Number " + number + " found in box at (" + row + ", " + column + ")");
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isValidPlacement(int number, int column, int row) {
        boolean valid = !isNumberInRow(number, row) &&
                        !isNumberInColumn(number, column) &&
                        !isNumberInBox(number, row, column);
        logger.info("Placement of number " + number + " at (" + row + ", " + column + ") is " + (valid ? "valid" : "invalid"));
        return valid;
    }

    public int[][] getBoard() {
        return this.board;
    }
}