package Model;

public class SudokuCreator {

    private Sudoku sudoku;

    public SudokuCreator(int size) {
        this.sudoku = new Sudoku(size);
        this.fillSudoku();
    }

    public Sudoku getSudoku() {
        return this.sudoku;
    }

    public Sudoku generateSudoku(int size, int percentage) {
        this.sudoku = new Sudoku(size);
        this.fillSudoku();
        this.removeNumbers(percentage);
        return this.sudoku;
    }

    private void fillSudoku() {
        System.out.println("Filling sudoku of size " + this.sudoku.getSize());
        int size = this.sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);

        // Place one instance of each possible number randomly on the grid
        for (int num = 1; num <= size; num++) {
            int row, col;
            do {
                row = (int) (Math.random() * size);
                col = (int) (Math.random() * size);
            } while (this.sudoku.get(row, col) != 0);
            this.sudoku.set(row, col, num);
        }

        // Use the solver to fill the rest of the grid
        Solver solver = new Solver(this.sudoku);
        if (solver.solveSudoku(0, 0)) {
            System.out.println("Sudoku successfully created:");
        } else {
            System.out.println("Failed to create a valid Sudoku.");
        }
    }

    public void removeNumbers(int percentage) {
        int size = this.sudoku.getSize();
        int cellsToRemove = (int) Math.round((size * size) * (percentage / 100.0));
        for (int i = 0; i < cellsToRemove; i++) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (this.sudoku.get(row, col) != 0) {
                this.sudoku.set(row, col, 0);
            } else {
                i--;
            }
        }
    }

}
