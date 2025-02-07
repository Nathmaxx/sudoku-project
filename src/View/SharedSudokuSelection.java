package View;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import Controller.MultidokuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SharedSudokuSelection extends BaseView {

    private MultidokuController multidokuController;

    public SharedSudokuSelection() {
        super();
        this.multidokuController = new MultidokuController(this);
        initializeUI();

    }

    @Override
    protected void initializeUI() {
        ComboBox<String> patternComboBox = new ComboBox<>();
        patternComboBox.getItems().addAll("Pattern 1", "Pattern 2", "Pattern 3");
        patternComboBox.setValue("Pattern 1");

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");

        Button selectButton = new Button("Select Sudoku");

        selectButton.setOnAction(event -> {
            String selectedPattern = patternComboBox.getValue();
            String selectedDifficulty = difficultyComboBox.getValue();
            multidokuController.displaySharedSudoku(selectedPattern, selectedDifficulty);
        });

        mainView.getChildren().addAll(patternComboBox, difficultyComboBox, selectButton);
        mainView.setAlignment(Pos.CENTER);
        mainView.setPadding(new Insets(20));
    }

}