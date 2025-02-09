package View;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import utils.ViewManager;
import Controller.MultidokuController;
import Controller.NavigationController;
import View.Components.HomeButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Classe représentant la vue de sélection de Sudoku partagé.
 * Gère l'affichage des options de sélection de modèle et de difficulté
 * pour les grilles de Sudoku partagées.
 */
public class SharedSudokuSelection extends BaseView {

    /** Contrôleur pour gérer les interactions avec les Sudokus partagés */
    private MultidokuController multidokuController;

    /** Contrôleur pour gérer la navigation entre les différentes pages */
    private NavigationController navigationController;

    /**
     * Constructeur pour initialiser la vue de sélection de Sudoku partagé.
     *
     * @param vm le gestionnaire de vues
     */
    public SharedSudokuSelection(ViewManager vm) {
        super();
        this.navigationController = new NavigationController(vm);
        this.multidokuController = new MultidokuController(this, vm);
        initializeUI();

    }

    /**
     * Initialise l'interface utilisateur de la vue de sélection de Sudoku partagé.
     * Configure les composants et les ajoute à la scène.
     */
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