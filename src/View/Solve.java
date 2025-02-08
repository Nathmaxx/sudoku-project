package View;

import Controller.NavigationController;
import Model.Sudoku;
import View.Components.HomeButton;
import View.Components.SudokuGrid;
import javafx.geometry.Pos;
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
        HomeButton homeButton = new HomeButton(navigationController);
        SudokuGrid sudokuGrid = new SudokuGrid(new Sudoku(9));
        sudokuGrid.setAlignment(Pos.CENTER);
        mainView.getChildren().addAll(homeButton, sudokuGrid);
        mainView.setAlignment(Pos.CENTER);
    }

}
