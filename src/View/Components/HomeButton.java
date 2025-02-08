package View.Components;

import Controller.NavigationController;
import javafx.scene.control.Button;

public class HomeButton extends Button {

    public HomeButton(NavigationController navigationController) {
        super("Retour à l'accueil");
        this.setOnAction(event -> navigationController.navigation("accueil"));
    }
}
