package View;

import javafx.scene.layout.VBox;

public abstract class BaseView {

    protected VBox mainView;

    public BaseView() {
        this.mainView = new VBox(10);
    }

    protected abstract void initializeUI();

    public VBox getView() {
        return this.mainView;
    }

    public void setView(VBox newView) {
        this.mainView.getChildren().clear();
        this.mainView.getChildren().add(newView);
    }
}
