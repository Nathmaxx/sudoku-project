package View;

import javafx.scene.control.ComboBox;

public class Generation extends BaseView {

    private int gridSize = 9;

    public Generation() {

        initializeUI();
    }

    @Override
    protected void initializeUI() {
        ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(4, 9, 16, 25, 36, 49);
        sizeComboBox.setValue(gridSize);
        sizeComboBox.setOnAction(event -> gridSize = sizeComboBox.getValue());

        mainView.getChildren().addAll(sizeComboBox);
    }

}
