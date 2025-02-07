package View;

import Controller.NavigationController;
import View.Components.HomeButton;
import javafx.scene.text.Text;
import utils.ViewManager;

public class Solve extends BaseView {

    private NavigationController navigationController;

    public Solve(ViewManager vm) {
        super();
        this.navigationController = new NavigationController(vm);
        initializeUI();
    }

    @Override
    protected void initializeUI() {
        Text mainText = new Text("Page de résolution");
        HomeButton homeButton = new HomeButton(navigationController);
        mainView.getChildren().addAll(mainText, homeButton);
    }

}
