package Model;

public class BackTrackingSolver {

    private int[][] board;
    private int size;

    public BackTrackingSolver() {
    }

    public void setSudoku(Sudoku sudoku) {
        this.board = sudoku.getBoard();
        this.size = sudoku.getSize();
    }

    private boolean isNumberInRow(int number, int row) {
        for (int i = 0; i < size; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInColumn(int number, int col) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberInBox(int number, int row, int column) {
        int boxSize = (int) Math.sqrt(size);
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

    private boolean isValidPlacement(int number, int column, int row) {
        return !isNumberInRow(number, row) &&
                !isNumberInColumn(number, column) &&
                !isNumberInBox(number, row, column);
    }

    public boolean backTrackingSolve() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= size; numberToTry++) {
                        if (isValidPlacement(numberToTry, column, row)) {
                            board[row][column] = numberToTry;

                            if (backTrackingSolve()) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return this.board;
    }

}
