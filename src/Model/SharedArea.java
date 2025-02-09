package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une zone partagée entre plusieurs Sudokus.
 * Cette classe étend Sudoku et gère la synchronisation des valeurs
 * entre différentes grilles de Sudoku.
 */
public class SharedArea extends Sudoku {

    /** Liste des écouteurs de la zone partagée */
    private List<SharedAreaListener> listeners = new ArrayList<>();

    /** Liste des Sudokus qui partagent cette zone */
    private List<SharedSudoku> sharedSudokus = new ArrayList<>();

    /** Flag indiquant si une mise à jour est en cours */
    private boolean isUpdating = false;

    /**
     * Constructeur créant une zone partagée à partir d'une grille existante.
     *
     * @param board la grille initiale
     */
    public SharedArea(int[][] board) {
        super(board);
    }

    /**
     * Constructeur créant une zone partagée vide de taille donnée.
     *
     * @param size la taille de la zone
     */
    public SharedArea(int size) {
        super(size);
    }

    /**
     * Définit une valeur dans la zone partagée et notifie les écouteurs.
     * Si une mise à jour n'est pas déjà en cours, propage le changement.
     *
     * @param row   la ligne de la case à modifier
     * @param col   la colonne de la case à modifier
     * @param value la nouvelle valeur à placer
     */
    @Override
    public void set(int row, int col, int value) {
        if (!isUpdating) {
            super.set(row, col, value);
            notifyListeners(row, col, value);
        }
    }

    /**
     * Ajoute un écouteur à la zone partagée.
     *
     * @param listener l'écouteur à ajouter
     */
    public void addListener(SharedAreaListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifie tous les écouteurs d'un changement dans la zone partagée.
     * Cette méthode est appelée après chaque modification d'une valeur.
     *
     * @param row   la ligne de la case modifiée
     * @param col   la colonne de la case modifiée
     * @param value la nouvelle valeur de la case
     */
    public void notifyListeners(int row, int col, int value) {
        isUpdating = true;
        for (SharedAreaListener listener : listeners) {
            listener.onSharedAreaUpdate(row, col, value);
        }
        isUpdating = false;
    }

    /**
     * Ajoute un Sudoku à la liste des Sudokus partageant cette zone.
     *
     * @param sharedSudoku le Sudoku partagé à ajouter
     */
    public void addSharedSudoku(SharedSudoku sharedSudoku) {
        sharedSudokus.add(sharedSudoku);
    }

    /**
     * Affiche la zone partagée dans la console.
     * Méthode utilitaire pour le débogage.
     */
    public void print() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                System.out.print(this.get(i, j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Retourne la grille de la zone partagée.
     *
     * @return le tableau 2D représentant la zone partagée
     */
    public int[][] getSharedSquare() {
        return this.getBoard();
    }

    /**
     * Définit la grille de la zone partagée.
     *
     * @param sharedSquare1 le nouveau tableau 2D pour la zone partagée
     */
    public void setSharedSquare(int[][] sharedSquare1) {
        this.setBoard(sharedSquare1);
    }

    /**
     * Définit la grille du Sudoku.
     * Méthode utilisée pour initialiser ou mettre à jour la grille complète.
     *
     * @param board le nouveau tableau 2D pour la grille
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }
}