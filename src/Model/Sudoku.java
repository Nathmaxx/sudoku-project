package Model;

public class Sudoku {

    private int[][] board;

    public Sudoku(int[][] board) {
        this.board = board;
    }

    public Sudoku(int size) {
        this.board = new int[size][size];
    }

    public Sudoku(Sudoku sudoku) {
        this.board = new int[sudoku.board.length][sudoku.board[0].length];
        for (int i = 0; i < sudoku.board.length; i++) {
            for (int j = 0; j < sudoku.board[0].length; j++) {
                this.board[i][j] = sudoku.board[i][j];
            }
        }
    }

    public void set(int i, int j, int value) {
        this.board[i][j] = value;
    }

    public int get(int i, int j) {
        return this.board[i][j];
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getSize() {
        return this.board.length;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                result += this.board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    public String afficher() {
        String result = "";
        int subGridSize = (int) Math.sqrt(this.board.length);

        for (int i = 0; i < this.board.length; i++) {
            if (i % subGridSize == 0) {
                for (int j = 0; j < this.board[0].length; j++) {
                    result += "--";
                }
                result += "----\n";
            }
            for (int j = 0; j < this.board[0].length; j++) {
                if (j % subGridSize == 0) {
                    result += "|";
                }
                result += this.board[i][j] + " ";
            }
            result += "|\n";
        }
        for (int j = 0; j < this.board[0].length; j++) {
            result += "--";
        }
        result += "----\n";
        return result;
    }
}
