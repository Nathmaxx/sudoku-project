package Model;

public class BackTrackingSolver {

    private int[][] board;
    private int size;

    public BackTrackingSolver(Sudoku sudoku) {
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
                if (board[i][j] == number)
            }
        }
    }

}
