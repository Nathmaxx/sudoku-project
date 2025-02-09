package Model;

import java.util.Hashtable;
import java.util.Map;

/**
 * Classe représentant un Sudoku qui partage des zones avec d'autres Sudokus.
 * Cette classe étend Sudoku et implémente SharedAreaListener pour gérer
 * les zones partagées entre plusieurs grilles.
 */
public class SharedSudoku extends Sudoku implements SharedAreaListener {

    /** Les zones partagées avec d'autres Sudokus */
    private SharedArea[] sharedAreas;

    /** Dictionnaire associant les coordonnées aux zones partagées */
    public Hashtable<Integer[], SharedArea> sharedAreasDico;

    /** Flag indiquant si une mise à jour est en cours */
    private boolean isUpdating = false;

    /**
     * Constructeur créant un SharedSudoku à partir d'un Sudoku et d'une zone
     * partagée.
     *
     * @param sudoku     le Sudoku de base
     * @param sharedArea la zone partagée à ajouter
     */
    public SharedSudoku(Sudoku sudoku, SharedArea sharedArea) {
        super(sudoku);
        this.sharedAreas = new SharedArea[1];
        this.sharedAreas[0] = sharedArea;
        this.sharedAreasDico = new Hashtable<>();
        sharedArea.addSharedSudoku(this);
        addListener(sharedArea);
    }

    /**
     * Constructeur créant un SharedSudoku à partir d'un Sudoku et plusieurs zones
     * partagées.
     *
     * @param sudoku      le Sudoku de base
     * @param sharedAreas tableau des zones partagées
     */
    public SharedSudoku(Sudoku sudoku, SharedArea[] sharedAreas) {
        super(sudoku);
        this.sharedAreas = sharedAreas;
        this.sharedAreasDico = new Hashtable<>();
        for (SharedArea sharedArea : sharedAreas) {
            sharedArea.addSharedSudoku(this);
            addListener(sharedArea);
        }
    }

    /**
     * Constructeur créant un SharedSudoku à partir d'une grille et d'une zone
     * partagée.
     *
     * @param board      la grille initiale
     * @param sharedArea la zone partagée à ajouter
     */
    public SharedSudoku(int[][] board, SharedArea sharedArea) {
        super(board);
        this.sharedAreas = new SharedArea[1];
        this.sharedAreas[0] = sharedArea;
        this.sharedAreasDico = new Hashtable<>();
        sharedArea.addSharedSudoku(this);
        addListener(sharedArea);
    }

    /**
     * Constructeur créant un SharedSudoku à partir d'une grille et plusieurs zones
     * partagées.
     *
     * @param board       la grille initiale
     * @param sharedAreas tableau des zones partagées à ajouter
     */
    public SharedSudoku(int[][] board, SharedArea[] sharedAreas) {
        super(board);
        this.sharedAreas = sharedAreas;
        this.sharedAreasDico = new Hashtable<>();
        for (SharedArea sharedArea : sharedAreas) {
            sharedArea.addSharedSudoku(this);
            addListener(sharedArea);
        }
    }

    /**
     * Constructeur créant un SharedSudoku avec une zone partagée définie par des
     * coordonnées.
     *
     * @param size       taille de la grille
     * @param sharedArea zone partagée à ajouter
     * @param x1         coordonnée x du premier point
     * @param y1         coordonnée y du premier point
     * @param x2         coordonnée x du deuxième point
     * @param y2         coordonnée y du deuxième point
     */
    public SharedSudoku(int size, SharedArea sharedArea, int x1, int y1, int x2, int y2) {
        super(size);
        // Sync the board with the shared area at the correct coordinates
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                this.set(i, j, sharedArea.get(i - x1, j - y1));
            }
        }
        this.sharedAreas = new SharedArea[1];
        this.sharedAreas[0] = sharedArea;
        this.sharedAreasDico = new Hashtable<>();
        sharedArea.addSharedSudoku(this);
        addListener(sharedArea);

        // Add the shared area to the dictionary
        Integer[] coordinates = { x1, y1, x2, y2 };
        sharedAreasDico.put(coordinates, sharedArea);
    }

    /**
     * Ajoute un écouteur pour une zone partagée.
     *
     * @param sharedArea la zone partagée à écouter
     */
    public void addListener(SharedArea sharedArea) {
        sharedArea.addListener(this);
    }

    /**
     * Gère la notification de mise à jour d'une zone partagée.
     *
     * @param row   la ligne de la case modifiée
     * @param col   la colonne de la case modifiée
     * @param value la nouvelle valeur
     */
    @Override
    public void onSharedAreaUpdate(int i, int j, int value) {
        if (!isUpdating) {
            isUpdating = true;
            for (int k = 0; k < sharedAreas.length; k++) {
                SharedArea area = sharedAreas[k];
                if (area == sharedAreas[k]) {
                    for (Map.Entry<Integer[], SharedArea> entry : sharedAreasDico.entrySet()) {
                        Integer[] coordinates = entry.getKey();
                        int x1 = coordinates[0];
                        int y1 = coordinates[1];
                        int x2 = coordinates[2];
                        int y2 = coordinates[3];
                        i = i + x1;
                        j = j + y1;
                        this.set(i, j, value);
                    }
                }
            }
            isUpdating = false;
        }
    }

    /**
     * Met à jour une case depuis une zone partagée.
     * Cette méthode est appelée lorsqu'une mise à jour est reçue d'une zone
     * partagée.
     *
     * @param sharedArea la zone partagée source de la mise à jour
     * @param i          ligne de la case à mettre à jour
     * @param j          colonne de la case à mettre à jour
     * @param value      nouvelle valeur à placer
     */
    public void updateFromSharedArea(SharedArea sharedArea, int i, int j, int value) {
        // Update the corresponding cell in the SharedSudoku board
        for (Map.Entry<Integer[], SharedArea> entry : sharedAreasDico.entrySet()) {
            if (entry.getValue() == sharedArea) {
                Integer[] coordinates = entry.getKey();
                int x1 = coordinates[0];
                int y1 = coordinates[1];
                this.set(x1 + i, y1 + j, value);
            }
        }
    }

    /**
     * Met à jour la valeur d'une case dans la grille.
     * Propage la mise à jour aux zones partagées si nécessaire.
     *
     * @param row   la ligne de la case
     * @param col   la colonne de la case
     * @param value la nouvelle valeur
     */
    @Override
    public void set(int i, int j, int value) {
        super.set(i, j, value);
        if (!isUpdating && sharedAreas != null) {
            for (SharedArea sharedArea : sharedAreas) {
                for (Map.Entry<Integer[], SharedArea> entry : sharedAreasDico.entrySet()) {
                    if (entry.getValue() == sharedArea) {
                        Integer[] coordinates = entry.getKey();
                        int x1 = coordinates[0];
                        int y1 = coordinates[1];
                        if (i >= x1 && i <= coordinates[2] && j >= y1 && j <= coordinates[3]) {
                            sharedArea.set(i - x1, j - y1, value);
                        }
                    }
                }
            }
        }
    }

    /**
     * Retourne les zones partagées de ce Sudoku.
     *
     * @return tableau des zones partagées
     */
    public SharedArea[] getSharedAreas() {
        return sharedAreas;
    }

    /**
     * Retourne la zone partagée associée à une position.
     *
     * @return la zone partagée correspondant à la position, ou null si aucune zone
     *         partagée n'est trouvée
     */
    public SharedArea getSharedArea() {
        return sharedAreas[0];
    }
}