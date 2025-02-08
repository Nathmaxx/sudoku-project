package View.Components;

import javafx.scene.control.ComboBox;

public class SizeComboBox extends ComboBox<Integer> {

    public SizeComboBox(int initialValue) {
        super();
        getItems().addAll(4, 9, 16);
        setValue(initialValue);
    }
}
