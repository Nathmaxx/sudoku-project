package Model;

import java.util.logging.Logger;

public class BackTrackingSolver extends SudokuSolver {

    private static final Logger logger = Logger.getLogger(BackTrackingSolver.class.getName());

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
                            logger.info("Placed " + numberToTry + " at (" + row + ", " + column + ")");

                            if (backTrackingSolve()) {
                                return true;
                            } else {
                                board[row][column] = 0;
                                logger.info("Removed " + numberToTry + " from (" + row + ", " + column + ")");
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