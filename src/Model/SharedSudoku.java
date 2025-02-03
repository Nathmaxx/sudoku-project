package Model;

import java.util.Hashtable;
import java.util.Map;

public class SharedSudoku extends Sudoku implements SharedAreaListener {
    private SharedArea[] sharedAreas;
    public Hashtable<Integer[], SharedArea> sharedAreasDico;
    private boolean isUpdating = false;

    public SharedSudoku(Sudoku sudoku, SharedArea sharedArea) {
        super(sudoku);
        this.sharedAreas = new SharedArea[1];
        this.sharedAreas[0] = sharedArea;
        this.sharedAreasDico = new Hashtable<>();
        sharedArea.addSharedSudoku(this);
        addListener(sharedArea);
    }

    public SharedSudoku(Sudoku sudoku, SharedArea[] sharedAreas) {
        super(sudoku);
        this.sharedAreas = sharedAreas;
        this.sharedAreasDico = new Hashtable<>();
        for (SharedArea sharedArea : sharedAreas) {
            sharedArea.addSharedSudoku(this);
            addListener(sharedArea);
        }
    }

    public SharedSudoku(int[][] board, SharedArea sharedArea) {
        super(board);
        this.sharedAreas = new SharedArea[1];
        this.sharedAreas[0] = sharedArea;
        this.sharedAreasDico = new Hashtable<>();
        sharedArea.addSharedSudoku(this);
        addListener(sharedArea);
    }

    public SharedSudoku(int[][] board, SharedArea[] sharedAreas) {
        super(board);
        this.sharedAreas = sharedAreas;
        this.sharedAreasDico = new Hashtable<>();
        for (SharedArea sharedArea : sharedAreas) {
            sharedArea.addSharedSudoku(this);
            addListener(sharedArea);
        }
    }

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

    public void addListener(SharedArea sharedArea) {
        sharedArea.addListener(this);
    }

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

    public SharedArea[] getSharedAreas() {
        return sharedAreas;
    }
}