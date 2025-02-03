package Model;

import java.util.ArrayList;
import java.util.List;

public class SharedArea extends Sudoku {
    private List<SharedAreaListener> listeners = new ArrayList<>();
    private List<SharedSudoku> sharedSudokus = new ArrayList<>();
    private boolean isUpdating = false;

    public SharedArea(int[][] board) {
        super(board);
    }

    public SharedArea(int size) {
        super(size);
    }

    @Override
    public void set(int i, int j, int value) {
        super.set(i, j, value);
        if (!isUpdating) {
            notifyListeners(i, j, value);
        }
    }

    public void addListener(SharedAreaListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(int i, int j, int value) {
        isUpdating = true;
        for (SharedAreaListener listener : listeners) {
            listener.onSharedAreaUpdate(i, j, value);
        }
        isUpdating = false;
    }

    public void addSharedSudoku(SharedSudoku sharedSudoku) {
        sharedSudokus.add(sharedSudoku);
    }

    public void print() {
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                System.out.print(this.get(i, j) + " ");
            }
            System.out.println();
        }
    }
}