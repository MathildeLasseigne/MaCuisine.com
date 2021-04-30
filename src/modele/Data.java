package modele;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import vue.*;

import java.io.Serializable;

/**
 * Reuni la liste des booleans representant les options que l utilisateur peut afficher
 */
public class Data {

    /**
     * Les differents panneaux de l appli
     */
    public enum Origine {Catalogue, Panier, Plan};

    private static Session currentSession = null;

    /**
     * Renvoie la session en cours.
     * <br/>Si aucune session n est en cours, cree une nouvelle session avec une cuisine de 300*300
     * @return
     */
    public static Session getCurrentSession() {
        if(currentSession != null){
            return currentSession;
        } else {
            createNewSession(new Point2D(300,300));
            return getCurrentSession();
        }
    }

    /**
     * Enregistre la session donnee comme la session actuelle
     * @param session
     */
    public static void loadSession(Session session){
        currentSession = session;
    }

    /**
     * Cree une nouvelle session
     * @param dimensionsCuisine
     */
    public static void createNewSession(Point2D dimensionsCuisine){
        Session s = new Session(dimensionsCuisine);
        loadSession(s);
        s.panneaux.setCuisine();
        Platform.runLater(()->{s.createGestionnaireMeubles();});
    }



    /**String de test. Les 2 premiers paragraphes de lorem ipsum**/
    public static String LoremIpsum = " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus eu tempor quam. Phasellus ullamcorper ornare mi, varius varius ligula pharetra nec. Duis dapibus justo hendrerit quam facilisis tristique. Sed volutpat, dolor in condimentum pulvinar, libero quam malesuada eros, ut venenatis tellus lorem vel nibh. Praesent molestie tincidunt augue. Suspendisse porta blandit dui, ut fringilla felis bibendum non. Duis tellus libero, feugiat eget dapibus at, facilisis in sem. Ut interdum, tortor sed iaculis viverra, quam leo accumsan dolor, feugiat feugiat ex sem quis orci. Aenean pretium eros est, at semper turpis egestas non.\n" +
            "\n" +
            "In lacinia commodo quam ut dapibus. Sed vel felis sapien. Nulla rutrum lectus eget est semper consequat. In felis urna, efficitur vitae ultricies id, viverra eu neque. Donec nec aliquet nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. In hac habitasse platea dictumst. Suspendisse vitae tincidunt magna. Praesent vestibulum tortor quis mattis sodales. Nulla finibus leo arcu, auctor ultrices est feugiat at. ";


    public static class Session  implements Serializable {

        private Point2D dimensionsCuisine = null;

        /**La liste des properties, notament celles utilisees par la tools Bar**/
        public Properties properties;

        /**
         * La liste de tous les panes de l appli utilisables
         */
        public Panneaux panneaux;


        /**Le gestionnaire de meuble**/
        public GestionaireMeubles gestionaireMeubles = null;

        public Session(Point2D dimensionsCuisine){
            this.dimensionsCuisine = dimensionsCuisine;
            this.properties = new Properties();
            this.panneaux = new Panneaux();

        }

        /**
         * Cree un nouveau gestionnaire de meubles, a invoquer apres .show
         */
        public void createGestionnaireMeubles(){
            this.gestionaireMeubles = new GestionaireMeubles(panneaux.cuisine);
            this.panneaux.leftPannel.getCtrl().bindTotalPrice(this.gestionaireMeubles.totalPricePanierProperty());
        }

        public static class Properties implements Serializable {

            /**
             * Les properties de la session actuelle
             */
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

        public class Panneaux implements Serializable {

            /**
             * Instancie la session actuelle
             */
            public Panneaux(){
                this.infoPane = new InfoPane();
                this.leftPannel = new LeftPannel();
                this.catalogue = leftPannel.getCatatalogue();
                this.panier = leftPannel.getPanier();
            }

            /**
             * Commit le meuble a tous les panneaux
             * @param meuble le meuble a commit
             * @see Panier#initCommit(MeubleModele)
             * @see Catalogue#initCommit(MeubleModele)
             * @see InfoPane#initCommit(MeubleModele)
             */
            public void initCommit(MeubleModele meuble){
                this.getPanier().initCommit(meuble);
                this.getCatalogue().initCommit(meuble);
                this.infoPane.initCommit(meuble);
            }

            /**La gestion de la vue du panier
             * <br/>Initialise par le controlleur de l appli, mais s ajoute lui meme**/
            public Panier panier;

            /**La gestion de la vue du catalogue
             * <br/>Initialise par le controlleur de l appli, mais s ajoute lui meme**/
            public Catalogue catalogue;

            /**La gestion de la vue du panneau information
             * <br/>Initialise par le controlleur de l appli, mais s ajoute lui meme**/
            public InfoPane infoPane;

            /**
             * La cuisine au milieu de l appli
             */
            public Cuisine cuisine;

            public LeftPannel leftPannel;

            /**
             * La gestion de la vue du panier
             * @return
             */
            public Panier getPanier() {
                return panier;
            }

            /**
             * La gestion de la vue du catalogue
             * @return
             */
            public Catalogue getCatalogue() {
                return catalogue;
            }

            protected void setCuisine() {
                this.cuisine = new Cuisine(dimensionsCuisine.getX(),dimensionsCuisine.getY());
            }
        }
    }




}