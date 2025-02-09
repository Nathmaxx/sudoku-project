package View;

import javafx.scene.layout.VBox;

/**
 * Classe de base pour toutes les vues de l'application.
 * Fournit des méthodes communes pour gérer l'interface utilisateur.
 */
public abstract class BaseView {

    /** Conteneur principal de la vue */
    protected VBox mainView;

    /**
     * Constructeur pour initialiser la vue de base.
     * Crée un VBox avec un espacement de 10 pixels.
     */
    public BaseView() {
        this.mainView = new VBox(10);
    }

    /**
     * Méthode abstraite pour initialiser l'interface utilisateur.
     * Doit être implémentée par les classes dérivées.
     */
    protected abstract void initializeUI();

    /**
     * Retourne le conteneur principal de la vue.
     *
     * @return le VBox principal de la vue
     */
    public VBox getView() {
        return this.mainView;
    }

    /**
     * Définit un nouveau conteneur principal pour la vue.
     * Efface les enfants existants et ajoute le nouveau conteneur.
     *
     * @param newView le nouveau VBox à définir comme conteneur principal
     */
    public void setView(VBox newView) {
        this.mainView.getChildren().clear();
        this.mainView.getChildren().add(newView);
    }
}
