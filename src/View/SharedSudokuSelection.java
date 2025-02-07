package View;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import utils.ViewManager;
import Controller.MultidokuController;
import Controller.NavigationController;
import View.Components.HomeButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SharedSudokuSelection extends BaseView {

    private MultidokuController multidokuController;
    private NavigationController navigationController;

    public SharedSudokuSelection(ViewManager vm) {
        super();
        this.navigationController = new NavigationController(vm);
        this.multidokuController = new MultidokuController(this);
        initializeUI();

    }

    @Override
    protected void initializeUI() {
        HomeButton homeButton = new HomeButton(navigationController);

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

        mainView.getChildren().addAll(
                homeButton,
                patternComboBox,
                difficultyComboBox,
                selectButton);
        mainView.setAlignment(Pos.CENTER);
        mainView.setPadding(new Insets(20));
    }

}