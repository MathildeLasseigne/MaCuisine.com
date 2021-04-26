package modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import vue.Catalogue;
import vue.Cuisine;
import vue.InfoPane;
import vue.Panier;

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


    /**String de test. Les 2 premiers paragraphes de lorem ipsum**/
    public static String LoremIpsum = " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus eu tempor quam. Phasellus ullamcorper ornare mi, varius varius ligula pharetra nec. Duis dapibus justo hendrerit quam facilisis tristique. Sed volutpat, dolor in condimentum pulvinar, libero quam malesuada eros, ut venenatis tellus lorem vel nibh. Praesent molestie tincidunt augue. Suspendisse porta blandit dui, ut fringilla felis bibendum non. Duis tellus libero, feugiat eget dapibus at, facilisis in sem. Ut interdum, tortor sed iaculis viverra, quam leo accumsan dolor, feugiat feugiat ex sem quis orci. Aenean pretium eros est, at semper turpis egestas non.\n" +
            "\n" +
            "In lacinia commodo quam ut dapibus. Sed vel felis sapien. Nulla rutrum lectus eget est semper consequat. In felis urna, efficitur vitae ultricies id, viverra eu neque. Donec nec aliquet nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. In hac habitasse platea dictumst. Suspendisse vitae tincidunt magna. Praesent vestibulum tortor quis mattis sodales. Nulla finibus leo arcu, auctor ultrices est feugiat at. ";


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
        public BooleanProperty isMeubleMovable = new SimpleBooleanProperty(true);


    }

    public static class Panneaux {

        public Panneaux(){

        }

        /**
         * Commit le meuble a tous les panneaux
         * @param meuble le meuble a commit
         * @see Panier#initCommit(Meuble) 
         * @see Catalogue#initCommit(Meuble) 
         * @see InfoPane#initCommit(Meuble) 
         */
        public void initCommit(Meuble meuble){
            this.panier.initCommit(meuble);
            this.catalogue.initCommit(meuble);
            this.infoPane.initCommit(meuble);
        }
        
        /**La gestion de la vue du panier
         * <br/>Initialise par le controlleur de l appli**/
        public Panier panier;

        /**La gestion de la vue du catalogue
         * <br/>Initialise par le controlleur de l appli**/
        public Catalogue catalogue;

        /**La gestion de la vue du panneau information
         * <br/>Initialise par le controlleur de l appli**/
        public InfoPane infoPane;

        /**
         * La cuisine au milieu de l appli
         */
        public Cuisine cuisine;

    }

}