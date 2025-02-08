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

    /*
     * public static void main(String[] args) {
     * int[][] initialBoard = {
     * { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
     * { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
     * { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
     * { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
     * { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
     * { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
     * { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
     * { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
     * { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
     * };
     * 
     * HumanSolver solver = new HumanSolver();
     * solver.setSudoku(new Sudoku(initialBoard));
     * for (int i = 0; i < 5; i++) {
     * System.out.println(solver.solveOneStep());
     * Sudoku s = new Sudoku(solver.getBoard());
     * System.out.println(s.afficher());
     * }
     * // Sudoku s = new Sudoku(solver.getBoard());
     * // System.out.println(s.afficher());
     * }
     */
}
