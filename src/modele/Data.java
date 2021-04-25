package modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

/**
 * Reuni la liste des booleans representant les options que l utilisateur peut afficher
 */
public class Data {


    /**La liste des properties, notament celles utilisees par la tools Bar**/
    public static Properties properties = new Properties();

    /**
     * La liste de tous les panes de l appli utilisables
     */
    public static Panneaux panneaux = new Panneaux();





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

    public static class Panneaux {

        public Panneaux(){

        }

        //TODO mettre panier

        //TODO mettre catalogue

        //TODO mettre infoPane


    }

}