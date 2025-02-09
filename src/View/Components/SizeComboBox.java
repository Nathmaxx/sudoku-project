package View.Components;

import javafx.scene.control.ComboBox;

/**
 * Classe représentant une ComboBox pour sélectionner la taille de la grille de
 * Sudoku.
 */
public class SizeComboBox extends ComboBox<Integer> {

    /**
     * Constructeur pour initialiser la ComboBox avec une valeur initiale.
     *
     * @param initialValue la valeur initiale de la ComboBox
     */
    public SizeComboBox(int initialValue) {
        super();
        getItems().addAll(4, 9, 16);
        setValue(initialValue);
    }
}
