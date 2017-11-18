package ch.hesso.santour.db;

/**
 * Created by Maxime on 18.11.2017.
 */

public interface DBCallback {
    // si la fonction est passée
    void resolve(Object o);
    //TODO il faut le reject en cas d'erreur
}
