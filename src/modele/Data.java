package modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

/**
 * Reuni la liste des booleans representant les options que l utilisateur peut afficher
 */
public class Data {

    /**
     * Defini si la grille est visible.
     * <br/>Initialise par le CuisineController
     */
    public static CheckBox isGrilleVisible;

    /**
     * Defini si il est possible de deplacer les meubles dans le plan.
     * <br/>Initialise par le CuisineController
     */
    public static CheckBox isMeubleMovable;

    /**La liste des properties, notament celles utilisees par la tools Bar**/
    public static Properties properties = new Properties();

    public static class Properties {

        public Properties(){

        }

        /**
         * Defini si la grille est visible.
         * <br/>Initialise par le CuisineController
         */
        public BooleanProperty isGrilleVisible = new SimpleBooleanProperty(false);

        /**
         * Defini si il est possible de deplacer les meubles dans le plan.
         * <br/>Initialise par le CuisineController
         */
        public BooleanProperty isMeubleMovable = new SimpleBooleanProperty(false);


    }


}
