package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Button button = new Button("Hello, JavaFX!");
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}