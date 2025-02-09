package Model;

import java.util.ArrayList;

/**
 * Classe qui implémente une résolution de Sudoku similaire à celle d'un humain.
 * Cette classe étend SudokuSolver et utilise des techniques de résolution
 * logiques.
 */
public class HumanSolver extends SudokuSolver {

    /**
     * Constructeur par défaut
     */
    public HumanSolver() {

    }

    /**
     * Résout le Sudoku en utilisant des techniques humaines.
     * Continue à résoudre tant que des progrès sont possibles.
     *
     * @return true si le Sudoku est complètement résolu, false sinon
     */
    public boolean solveLikeHuman() {
        boolean progress;
        do {
            progress = solveOneStep();
        } while (progress);

        return isSudokuComplete();
    }

    /**
     * Effectue une seule étape de résolution.
     * Recherche d'abord les "naked singles" puis les "hidden singles".
     *
     * @return true si un nombre a été placé avec succès, false sinon
     */
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

    /**
     * Trouve tous les nombres possibles pour une case donnée.
     *
     * @param row l'index de la ligne
     * @param col l'index de la colonne
     * @return une liste des nombres possibles pour cette case
     */
    private ArrayList<Integer> findPossibleNumbers(int row, int col) {
        ArrayList<Integer> possibles = new ArrayList<>();
        for (int num = 1; num <= size; num++) {
            if (isPossible(row, col, num)) {
                possibles.add(num);
            }
        }
        return possibles;
    }

    /**
     * Vérifie si un nombre peut être placé dans une case donnée.
     *
     * @param row l'index de la ligne
     * @param col l'index de la colonne
     * @param num le nombre à vérifier
     * @return true si le nombre peut être placé, false sinon
     */
    private boolean isPossible(int row, int col, int num) {
        return !isNumberInRow(num, row) &&
                !isNumberInColumn(num, col) &&
                !isNumberInBox(num, row, col);
    }

    /**
     * Vérifie si le Sudoku est complètement rempli.
     *
     * @return true si toutes les cases sont remplies, false sinon
     */
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
