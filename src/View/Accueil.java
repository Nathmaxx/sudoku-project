package View;

import Controller.HomeController;
import Controller.HomeController.Views;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Accueil extends Application {

    private HomeController generationController;
    private VBox mainView;

    public Accueil() {
        this.mainView = new VBox(10);
        this.generationController = new HomeController(this);
        initializeUI();
    }

    protected void initializeUI() {
        mainView.setAlignment(Pos.CENTER);
        mainView.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Bienvenue dans l'application Sudoku");
        Button generateButton = new Button("Génération");
        generateButton.setOnAction(event -> generationController.handleChangeView(Views.GENERATION));

        Button solveButton = new Button("Résolution");
        solveButton.setOnAction(event -> generationController.handleChangeView(Views.SOLVE));

        Button sharedSudokuButton = new Button("Shared Sudoku");
        sharedSudokuButton.setOnAction(event -> generationController.handleChangeView(Views.MULTIDOKU));

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(generateButton, solveButton, sharedSudokuButton);
        hbox.setAlignment(Pos.CENTER);

        mainView.getChildren().addAll(welcomeLabel, hbox);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Application");

        Accueil accueil = new Accueil();

        Scene scene = new Scene(accueil.getView(), 800, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox getView() {
        return this.mainView;
    }

    public void setView(VBox newView) {
        mainView.getChildren().clear();
        mainView.getChildren().add(newView);
    }

}