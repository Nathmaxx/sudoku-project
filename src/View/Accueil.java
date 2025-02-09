package View;

//import Controller.HomeController;
//import Controller.HomeController.Views;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import utils.ViewManager;
import Controller.NavigationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Classe représentant la vue d'accueil de l'application.
 * Gère l'affichage des options de navigation vers les différentes
 * fonctionnalités.
 */
public class Accueil extends BaseView {

    /** Contrôleur de navigation pour gérer les changements de vue */
    private NavigationController navigationController;

    /**
     * Constructeur pour initialiser la vue d'accueil.
     *
     * @param vm le gestionnaire de vues
     */
    public Accueil(ViewManager vm) {
        this.navigationController = new NavigationController(vm);
        initializeUI();
    }

    /**
     * Initialise l'interface utilisateur de la vue d'accueil.
     * Configure les composants et les ajoute à la scène.
     */
    protected void initializeUI() {
        mainView.setAlignment(Pos.CENTER);
        mainView.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Bienvenue dans l'application Sudoku");
        Button generateButton = new Button("Génération");
        generateButton.setOnAction(event -> navigationController.navigation("generation"));

        Button solveButton = new Button("Résolution");
        solveButton.setOnAction(event -> navigationController.navigation("solve"));

        Button sharedSudokuButton = new Button("Multidoku");
        sharedSudokuButton.setOnAction(event -> navigationController.navigation("multidoku"));

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(generateButton, solveButton, sharedSudokuButton);
        hbox.setAlignment(Pos.CENTER);

        mainView.getChildren().addAll(welcomeLabel, hbox);
    }

}