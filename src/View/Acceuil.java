package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Acceuil extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Application");

        Label welcomeLabel = new Label("Bienvenue dans l'application Sudoku");
        Button generateButton = new Button("Génération");
        Button solveButton = new Button("Résolution");
        Button sharedSudokuButton = new Button("Shared Sudoku");

        sharedSudokuButton.setOnAction(event -> {
            SharedSudokuSelection sharedSudokuSelection = new SharedSudokuSelection();
            try {
                sharedSudokuSelection.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox hbox = new HBox(10, generateButton, solveButton, sharedSudokuButton);
        VBox vbox = new VBox(10, welcomeLabel, hbox);
        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}