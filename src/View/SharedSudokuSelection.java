package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SharedSudokuSelection extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select Shared Sudoku");

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
            SharedSudokuDisplay sharedSudokuDisplay = new SharedSudokuDisplay(selectedPattern, selectedDifficulty);
            try {
                sharedSudokuDisplay.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(10, patternComboBox, difficultyComboBox, selectButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}