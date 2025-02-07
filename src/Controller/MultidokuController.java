package Controller;

import View.SharedSudokuDisplay;
import View.SharedSudokuSelection;

public class MultidokuController {

    private SharedSudokuSelection sharedSudokuSelection;

    public MultidokuController(SharedSudokuSelection sharedSudokuSelection) {
        this.sharedSudokuSelection = sharedSudokuSelection;
    }

    public void displaySharedSudoku(String selectedPattern, String selectedDifficulty) {
        SharedSudokuDisplay display = new SharedSudokuDisplay(selectedPattern, selectedDifficulty);
        sharedSudokuSelection.setView(display.getView());
    }

}
