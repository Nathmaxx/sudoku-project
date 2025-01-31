package View;

import Model.SharedSudoku;
import Model.SharedArea;
import Model.Solver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SharedSudokuDisplay extends Application {

    private SharedSudoku sharedSudoku;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shared Sudoku");

        // For demonstration, create a sample SharedSudoku
        int[][] sampleBoard = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        SharedArea sharedArea = new SharedArea(sampleBoard);
        sharedSudoku = new SharedSudoku(sampleBoard, sharedArea);

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(event -> {
            Solver solver = new Solver(sharedSudoku);
            if (solver.solveSudoku(0, 0)) {
                System.out.println("Sudoku solved!");
            } else {
                System.out.println("Unable to solve Sudoku.");
            }
        });

        VBox vbox = new VBox(10, solveButton);
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