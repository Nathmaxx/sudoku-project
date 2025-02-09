package Model;

import java.util.function.Consumer;

/**
 * Classe responsable de la création et génération de Sudokus.
 * Permet de générer des grilles valides et de retirer des nombres
 * pour créer des puzzles à résoudre.
 */
public class SudokuCreator {

    /** Le Sudoku en cours de création */
    private Sudoku sudoku;

    /** Écouteur de progression pour suivre l'avancement de la génération */
    private Consumer<Double> progressListener;

    /**
     * Constructeur qui initialise un créateur de Sudoku.
     *
     * @param size la taille de la grille à créer
     */
    public SudokuCreator(int size) {
        this.sudoku = new Sudoku(size);
    }

    /**
     * Génère un nouveau Sudoku avec un pourcentage de cases vides.
     *
     * @param size       la taille de la grille
     * @param percentage le pourcentage de cases à vider
     * @return le Sudoku généré
     */
    public Sudoku generateSudoku(int size, int percentage) {
        this.sudoku = new Sudoku(size);
        this.fillSudoku();
        System.out.println("Removing " + percentage + "% of numbers from the sudoku");
        this.removeNumbers(percentage);
        return this.sudoku;
    }

    /**
     * Retire un certain pourcentage de nombres de la grille.
     *
     * @param percentage le pourcentage de nombres à retirer
     */
    private void removeNumbers(int percentage) {
        int size = this.sudoku.getSize();
        int totalCells = size * size;
        int cellsToRemove = (int) (totalCells * (percentage / 100.0));

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (this.sudoku.get(row, col) != 0) {
                this.sudoku.set(row, col, 0);
                cellsToRemove--;
            }
        }
    }

    /**
     * Remplit la grille de Sudoku avec des nombres valides.
     */
    private void fillSudoku() {
        System.out.println("Filling sudoku of size " + this.sudoku.getSize());
        int size = this.sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);

        // Fill the diagonal sub-grids
        for (int i = 0; i < size; i += sqrtSize) {
            fillDiagonalSubGrid(i, i);
            updateProgress((double) i / size);
        }

        // Solve the partially filled Sudoku
        Solver solver = new Solver(this.sudoku);
        if (solver.solveSudoku(0, 0)) {
            System.out.println("Sudoku successfully created:");
        } else {
            System.out.println("Failed to create a valid Sudoku.");
        }
    }

    /**
     * Remplit une sous-grille diagonale du Sudoku avec des nombres valides.
     * Cette méthode est utilisée comme point de départ pour générer un Sudoku
     * complet.
     *
     * @param row ligne de départ de la sous-grille
     * @param col colonne de départ de la sous-grille
     */
    private void fillDiagonalSubGrid(int row, int col) {
        int size = this.sudoku.getSize();
        int sqrtSize = (int) Math.sqrt(size);
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                int num;
                do {
                    num = (int) (Math.random() * size) + 1;
                } while (!isSafeToPlace(row + i, col + j, num));
                this.sudoku.set(row + i, col + j, num);
            }
        }
    }

    /**
     * Vérifie si un nombre peut être placé en toute sécurité à une position donnée.
     * Vérifie la ligne, la colonne et la sous-grille correspondante.
     *
     * @param row ligne où placer le nombre
     * @param col colonne où placer le nombre
     * @param num nombre à placer
     * @return true si le placement est sûr, false sinon
     */
    private boolean isSafeToPlace(int row, int col, int num) {
        for (int x = 0; x < this.sudoku.getSize(); x++) {
            if (this.sudoku.get(row, x) == num || this.sudoku.get(x, col) == num) {
                return false;
            }
        }
        int sqrtSize = (int) Math.sqrt(this.sudoku.getSize());
        int startRow = row - row % sqrtSize;
        int startCol = col - col % sqrtSize;
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                if (this.sudoku.get(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Définit l'écouteur de progression.
     *
     * @param listener le Consumer qui sera notifié de la progression
     */
    public void setProgressListener(Consumer<Double> progressListener) {
        this.progressListener = progressListener;
    }

    /**
     * Notifie l'écouteur de progression de l'avancement de la génération.
     *
     * @param progress valeur de progression entre 0.0 et 1.0
     */
    private void updateProgress(double progress) {
        if (progressListener != null) {
            progressListener.accept(progress);
        }
    }

    /**
     * Génère un Sudoku avec des cases pré-remplies.
     * Cette méthode crée un nouveau Sudoku en utilisant une grille existante comme
     * base,
     * puis retire un certain pourcentage de nombres pour créer le puzzle.
     *
     * @param size       la taille de la grille
     * @param percentage le pourcentage de cases à vider
     * @param preFilled  la grille pré-remplie à utiliser comme base
     * @return le Sudoku généré avec les cases pré-remplies
     */
    public Sudoku generateSudokuWithPreFilled(int size, int removalPercentage, int[][] preFilled, int untouchedStartRow,
            int untouchedStartCol, int untouchedEndRow, int untouchedEndCol) {
        this.sudoku = new Sudoku(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.sudoku.set(i, j, preFilled[i][j]);
            }
        }

        fillRemainingCells();

        removeNumbers(removalPercentage);

        // Set the untouched area back to the original values
        for (int i = untouchedStartRow; i <= untouchedEndRow; i++) {
            for (int j = untouchedStartCol; j <= untouchedEndCol; j++) {
                this.sudoku.set(i, j, preFilled[i][j]);
            }
        }

        return this.sudoku;
    }

    /**
     * Tente de remplir le reste de la grille de manière récursive.
     * Utilise un algorithme de backtracking pour trouver une solution valide.
     *
     * @return true si le remplissage est réussi, false sinon
     */
    private void fillRemainingCells() {
        Solver solver = new Solver(this.sudoku);
        solver.solveSudoku(0, 0);
    }
}