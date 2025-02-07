package View;

import javafx.scene.text.Text;

public class Solve extends BaseView {

    public Solve() {
        super();
        initializeUI();
    }

    @Override
    protected void initializeUI() {
        Text mainText = new Text("Page de résolution");
        mainView.getChildren().addAll(mainText);
    }

}
