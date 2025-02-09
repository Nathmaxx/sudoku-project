
package Controller;

import utils.ViewManager;

/**
 * Contrôleur pour gérer la navigation entre les différentes vues de
 * l'application.
 */
public class NavigationController {

    /** Gestionnaire de vues pour afficher les différentes pages */
    private ViewManager vm;

    /**
     * Constructeur pour initialiser le contrôleur avec le gestionnaire de vues.
     *
     * @param vm le gestionnaire de vues
     */
    public NavigationController(ViewManager vm) {
        this.vm = vm;
    }

    /**
     * Navigue vers la page spécifiée.
     *
     * @param pageName le nom de la page à afficher
     */
    public void navigation(String pageName) {
        vm.showView(pageName);
    }
}