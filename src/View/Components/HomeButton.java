package View.Components;

import Controller.NavigationController;
import javafx.scene.control.Button;

/**
 * Classe représentant un bouton pour retourner à l'accueil.
 */
public class HomeButton extends Button {

    /**
     * Constructeur pour initialiser le bouton avec une action de navigation.
     *
     * @param navigationController le contrôleur de navigation pour gérer le
     *                             changement de vue
     */
    public HomeButton(NavigationController navigationController) {
        super("Retour à l'accueil");
        this.setOnAction(event -> navigationController.navigation("accueil"));
    }
}
