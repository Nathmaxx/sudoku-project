package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui implémente les algorithmes de résolution pour un Sudoku.
 * Cette classe fournit les méthodes nécessaires pour résoudre des grilles
 * de Sudoku standard et partagées.
 */
public class Solver {

    /** Le Sudoku à résoudre */
    private Sudoku sudoku;

    /** La solution trouvée pour le Sudoku */
    private int[][] solution;

    /**
     * Constructeur pour un Sudoku standard.
     * 
     * @param sudoku le Sudoku à résoudre
     */
    public Solver(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Constructeur pour un Sudoku partagé.
     * 
     * @param sudoku le SharedSudoku à résoudre
     */
    public Solver(SharedSudoku sudoku) {
        this.sudoku = sudoku;
        this.solution = new int[9][9];
    }

    /**
     * Vérifie si un nombre peut être placé en toute sécurité à une position donnée.
     * 
     * @param row la ligne où placer le nombre
     * @param col la colonne où placer le nombre
     * @param num le nombre à placer
     * @return true si le placement est sûr, false sinon
     */
    private boolean isSafe(int row, int col, int num) {
        // Check if it's safe to place the number in the given cell
        // Ensure that the shared area constraints are checked
        int subGridSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Réinitialise la solution du Sudoku.
     * Met à zéro toutes les cases de la grille solution.
     */
    public void reset() {
        this.solution = new int[9][9];
    }

    /**
     * Vérifie si une ligne respecte les règles du Sudoku.
     * Une ligne est valide si elle ne contient pas de doublons
     * (en ignorant les cases vides).
     *
     * @param row l'index de la ligne à vérifier
     * @return true si la ligne est valide, false sinon
     */
    public boolean checkRow(int row) {
        int[] values = new int[this.sudoku.getSize()];
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            values[i] = this.sudoku.get(row, i);
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Vérifie si une colonne respecte les règles du Sudoku.
     * Une colonne est valide si elle ne contient pas de doublons
     * (en ignorant les cases vides).
     *
     * @param column l'index de la colonne à vérifier
     * @return true si la colonne est valide, false sinon
     */
    public boolean checkColumn(int column) {
        int[] values = new int[this.sudoku.getSize()];
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            values[i] = this.sudoku.get(i, column);
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] == values[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Vérifie si une zone rectangulaire du Sudoku respecte les règles.
     * Une zone est valide si elle ne contient pas de doublons et si tous
     * les nombres sont dans l'intervalle valide.
     *
     * @param startX coordonnée X de départ de la zone
     * @param startY coordonnée Y de départ de la zone
     * @param sizeX  taille de la zone en X
     * @param sizeY  taille de la zone en Y
     * @return true si la zone est valide, false sinon
     */
    public boolean checkSquare(int startX, int startY, int sizeX, int sizeY) {
        boolean[] seen = new boolean[sudoku.getSize() + 1];
        for (int i = startX; i < startX + sizeX; i++) {
            for (int j = startY; j < startY + sizeY; j++) {
                int value = sudoku.get(i, j);
                if (value < 1 || value > sudoku.getSize() || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }

    /**
     * Vérifie si l'ensemble du Sudoku respecte les règles.
     * 
     * @return true si le Sudoku est valide, false sinon
     */
    public boolean checkSudoku() {
        return isValid(0, 0, 0);
    }

    /**
     * Vérifie si un nombre est valide à une position donnée.
     * Vérifie la ligne, la colonne et la sous-grille.
     * 
     * @param row la ligne à vérifier
     * @param col la colonne à vérifier
     * @param num le nombre à vérifier
     * @return true si le nombre est valide à cette position, false sinon
     */
    public boolean isValid(int row, int col, int num) {
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            if (this.sudoku.get(row, i) == num || this.sudoku.get(i, col) == num) {
                return false;
            }
        }
        int subGridSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Résout un Multidoku (plusieurs Sudokus interconnectés).
     * Cette méthode gère la résolution coordonnée de plusieurs grilles de Sudoku
     * qui partagent des cases communes.
     * 
     * @param row la ligne de départ
     * @param col la colonne de départ
     * @return true si une solution est trouvée pour tous les Sudokus, false sinon
     */
    public boolean solveMultiSudoku(int row, int col) {
        if (row == this.sudoku.getSize()) {
            row = 0;
            if (++col == this.sudoku.getSize()) {
                // Store the current solution in the solution array
                for (int i = 0; i < this.sudoku.getSize(); i++) {
                    for (int j = 0; j < this.sudoku.getSize(); j++) {
                        solution[i][j] = this.sudoku.get(i, j);
                    }
                }
                return true; // Sudoku solved
            }
        }
        if (this.sudoku.get(row, col) != 0) {
            return solveSudoku(row + 1, col); // Skip filled cells
        }
        for (int num = 1; num <= this.sudoku.getSize(); num++) {
            if (isValid(row, col, num)) {
                this.sudoku.set(row, col, num); // Try placing the number
                if (solveSudoku(row + 1, col)) {
                    return true; // If it leads to a solution, return true
                }
                this.sudoku.set(row, col, 0); // Backtrack if it doesn't work
            }
        }
        return false; // No valid number found for this cell
    }

    /**
     * Résout le Sudoku de manière récursive.
     * Utilise un algorithme de backtracking pour trouver une solution valide.
     * 
     * @param row la ligne de départ
     * @param col la colonne de départ
     * @return true si une solution est trouvée, false sinon
     */
    public boolean solveSudoku(int row, int col) {
        if (row == this.sudoku.getSize()) {
            row = 0;
            if (++col == this.sudoku.getSize()) {
                return true;
            }
        }
        if (this.sudoku.get(row, col) != 0) {
            return solveSudoku(row + 1, col);
        }
        Solver solver = new Solver(this.sudoku);
        for (int num = 1; num <= this.sudoku.getSize(); num++) {
            if (solver.isValid(row, col, num)) {
                this.sudoku.set(row, col, num);
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                this.sudoku.set(row, col, 0);
            }
        }
        return false;
    }

    /**
     * Résout le Sudoku et retourne la liste des étapes de résolution.
     * Chaque étape est représentée par une paire (position, valeur).
     * 
     * @param row la ligne de départ
     * @param col la colonne de départ
     * @return la liste des étapes de résolution sous forme de paires (position,
     *         valeur)
     */
    public List<Map.Entry<Integer, Integer>> solveSudokuWithSteps(int row, int col) {
        Sudoku sudokuCopy = new Sudoku(this.sudoku);
        Solver solver = new Solver(sudokuCopy);
        solver.solveSudoku(row, col);

        List<Map.Entry<Integer, Integer>> steps = new ArrayList<>();
        for (int i = 0; i < this.sudoku.getSize(); i++) {
            for (int j = 0; j < this.sudoku.getSize(); j++) {
                if (this.sudoku.get(i, j) == 0 && sudokuCopy.get(i, j) != 0) {
                    steps.add(new HashMap.SimpleEntry<>(i * this.sudoku.getSize() + j, sudokuCopy.get(i, j)));
                }
            }
        }
        return steps;
    }
}