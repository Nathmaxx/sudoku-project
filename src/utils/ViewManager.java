package utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewManager {

    private Map<String, Pane> views = new HashMap<>();
    private Stage primaryStage;

    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addView(String name, Pane view) {
        views.put(name, view);
    }

    public void showView(String name) {
        Pane view = views.get(name);
        if (view != null) {
            primaryStage.setScene(new Scene(view, 800, 800));
            primaryStage.show();
        }
    }
}
