package Model;

public class Multidoku {

    public SharedSudoku[] sudokus;

    public Multidoku(SharedSudoku[] sudokus) {
        this.sudokus = sudokus;
    }

    public SharedSudoku[] getSudokus() {
        return this.sudokus;
    }

    public void addSudoku(SharedSudoku sudoku) {
        SharedSudoku[] newSudokus = new SharedSudoku[this.sudokus.length + 1];
        for (int i = 0; i < this.sudokus.length; i++) {
            newSudokus[i] = this.sudokus[i];
        }
        newSudokus[this.sudokus.length] = sudoku;
        this.sudokus = newSudokus;
    }
}
