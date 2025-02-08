package utils;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private Map<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addView(String name, Pane view) {
        Scene scene = new Scene(view, 700, 700);
        scenes.put(name, scene);
    }

    public void showView(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
