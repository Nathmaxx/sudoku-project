package Model;

/**
 * Interface pour écouter les mises à jour des zones partagées.
 */
public interface SharedAreaListener {

    /**
     * Méthode appelée lorsqu'une zone partagée est mise à jour.
     *
     * @param i     la ligne de la case mise à jour
     * @param j     la colonne de la case mise à jour
     * @param value la nouvelle valeur de la case
     */
    void onSharedAreaUpdate(int i, int j, int value);
}