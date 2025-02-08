package Model;

import java.util.ArrayList;

public class HumanSolver extends SudokuSolver {

    public boolean solveLikeHuman() {
        boolean progress;
        do {
            progress = solveOneStep();
        } while (progress);

        return isSudokuComplete();
    }

    public boolean solveOneStep() {
        // Chercher les naked singles
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    ArrayList<Integer> possibles = findPossibleNumbers(row, col);
                    if (possibles.size() == 1) {
                        board[row][col] = possibles.get(0);
                        return true;
                    }
                }
            }
        }

        // Chercher les hidden singles dans les lignes
        for (int row = 0; row < size; row++) {
            for (int num = 1; num <= size; num++) {
                ArrayList<Integer> possibleCols = new ArrayList<>();
                for (int col = 0; col < size; col++) {
                    if (board[row][col] == 0 && isPossible(row, col, num)) {
                        possibleCols.add(col);
                    }
                }
                if (possibleCols.size() == 1) {
                    board[row][possibleCols.get(0)] = num;
                    return true;
                }
            }
        }

        return false;
    }

    private ArrayList<Integer> findPossibleNumbers(int row, int col) {
        ArrayList<Integer> possibles = new ArrayList<>();
        for (int num = 1; num <= size; num++) {
            if (isPossible(row, col, num)) {
                possibles.add(num);
            }
        }
        return possibles;
    }

    private boolean isPossible(int row, int col, int num) {
        return !isNumberInRow(num, row) &&
                !isNumberInColumn(num, col) &&
                !isNumberInBox(num, row, col);
    }

    private boolean isSudokuComplete() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0)
                    return false;
            }
        }
        return true;
    }

}
