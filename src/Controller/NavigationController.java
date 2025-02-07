
package Controller;

import utils.ViewManager;

public class NavigationController {

    private ViewManager vm;

    public NavigationController(ViewManager vm) {
        this.vm = vm;
    }

    public void navigation(String pageName) {
        vm.showView(pageName);
    }
}