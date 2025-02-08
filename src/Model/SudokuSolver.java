package Model;

public class SudokuSolver {

    protected int[][] board;
    protected int size;
    protected int boxSize;

    public SudokuSolver() {

    }

    public void setSudoku(Sudoku sudoku) {
        this.board = sudoku.getBoard();
        this.size = sudoku.getSize();
        this.boxSize = (int) Math.sqrt(size);
    }

    protected boolean isNumberInRow(int number, int row) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    protected boolean isNumberInColumn(int number, int col) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == number) {
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
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isValidPlacement(int number, int column, int row) {
        return !isNumberInRow(number, row) &&
                !isNumberInColumn(number, column) &&
                !isNumberInBox(number, row, column);
    }

    public int[][] getBoard() {
        return this.board;
    }
}
