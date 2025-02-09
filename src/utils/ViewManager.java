package utils;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsable de la gestion des vues dans l'application.
 * Permet de basculer entre différentes scènes.
 */
public class ViewManager {

    /** Map associant les noms des vues aux scènes correspondantes */
    private Map<String, Scene> scenes = new HashMap<>();

    /** Stage principal de l'application */
    private Stage primaryStage;

    /**
     * Constructeur pour initialiser le gestionnaire de vues avec le stage
     * principal.
     *
     * @param primaryStage le stage principal de l'application
     */
    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Ajoute une vue au gestionnaire de vues.
     *
     * @param name le nom de la vue
     * @param view le Pane représentant la vue
     */
    public void addView(String name, Pane view) {
        Scene scene = new Scene(view, 700, 700);
        scenes.put(name, scene);
    }

    /**
     * Affiche la vue correspondant au nom donné.
     *
     * @param name le nom de la vue à afficher
     */
    public void showView(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
