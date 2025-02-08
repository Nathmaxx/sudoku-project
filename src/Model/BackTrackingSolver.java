package Model;

public class BackTrackingSolver extends SudokuSolver {

    public BackTrackingSolver() {
        super();
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

}
