package modele;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vue.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Reuni la liste des booleans representant les options que l utilisateur peut afficher
 */
public class Data<rotationPas> {

    /**
     * Les differents panneaux de l appli
     */
    public enum Origine {Catalogue, Panier, Plan};

    private static Session currentSession = null;

    public final static int rotationPas = 10;

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


    /**
     * Sauvegarde la session a l aide d un fileChooser
     * @param primaryStage
     */
    public static void saveSession(Stage primaryStage){
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = chooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(getCurrentSession());
                out.flush();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }

    }



    /*---------------------------Session-----------------------------------*/




    public static class Session  implements Serializable {

        private ArrayList<MeubleModele> MeubleModeleCatalogue = initMeubleModeleCatalogue();

        /**
         * Renvoie le catalogue des modeles de meuble
         * @return
         */
        private ArrayList<MeubleModele> initMeubleModeleCatalogue(){
            ArrayList<MeubleModele> catalogue = new ArrayList<>();
            // Création meubles
            MeubleModele Table1 = new MeubleModele("Table acier noir", "MaCuisine.com", MeubleModele.Type.Tables,29,110,67);
            MeubleModele Table2 = new MeubleModele("Petite table ronde", "MaCuisine.com", MeubleModele.Type.Tables,100,60,60);
            MeubleModele Table3 = new MeubleModele("Table 4pers. blanche", "MaCuisine.com", MeubleModele.Type.Tables,499,160,95);
            MeubleModele Table4 = new MeubleModele("Table 8pers. noire", "MaCuisine.com", MeubleModele.Type.Tables,599,240,105);
            MeubleModele Table5 = new MeubleModele("Table murale blanche", "MaCuisine.com", MeubleModele.Type.Tables,39,74,60);
            MeubleModele Table6 = new MeubleModele("Table murale noire", "MaCuisine.com", MeubleModele.Type.Tables,49,90,50);
            MeubleModele Meuble = new MeubleModele("Grandes étagères", "MaCuisine.com", MeubleModele.Type.Meubles,99,147,147);
            MeubleModele Chaise = new MeubleModele("Chaise blanche cuir", "MaCuisine.com", MeubleModele.Type.Chaises,59,30,30);
            //MeubleModele Machine_A_Laver = new MeubleModele("Machine à laver Bosch", "Bosch", MeubleModele.Type.Petits_electromenagers,499,100,100);
            //MeubleModele Plan_de_travail = new MeubleModele("Plan de travail avec évier", "MaCuisine.com", MeubleModele.Type.Gros_electromenagers,130,200,60);
            // Images meubles
            //Image i = new Image(getClass().getResourceAsStream("../Sprites/table1.png"));
            //if (i == null) {
            //    System.out.println("Erreur");
            //}
            Table1.setImg(new Image(getClass().getResourceAsStream("../Sprites/table1.png")));
            Table2.setImg(new Image(getClass().getResourceAsStream("../Sprites/table2.png")));
            Table3.setImg(new Image(getClass().getResourceAsStream("../Sprites/table3.png")));
            Table4.setImg(new Image(getClass().getResourceAsStream("../Sprites/table4.png")));
            Table5.setImg(new Image(getClass().getResourceAsStream("../Sprites/table5.png")));
            Table6.setImg(new Image(getClass().getResourceAsStream("../Sprites/table6.png")));
            Meuble.setImg(new Image(getClass().getResourceAsStream("../Sprites/meuble.png")));
            Chaise.setImg(new Image(getClass().getResourceAsStream("../Sprites/chaise.png")));
            // add catalogue
            catalogue.add(Table1);
            catalogue.add(Table2);
            catalogue.add(Table3);
            catalogue.add(Table4);
            catalogue.add(Table5);
            catalogue.add(Table6);
            catalogue.add(Meuble);
            catalogue.add(Chaise);
            //catalogue.add(Machine_A_Laver);
            //catalogue.add(Plan_de_travail);
            return catalogue;
        }

        /**
         * Renvoie le catalogue des modeles de meubles
         * @return
         */
        public ArrayList<MeubleModele> getMeubleModeleCatalogue() {
            return MeubleModeleCatalogue;
        }

        private Point2D dimensionsCuisine = null;

        /**Le facteur multiplicateur de la taille de la cuisine**/
        private double facteurSize = 1;

        /**
         * Rend le point multiplie par le facteur de dimension de la session
         * @param sizePoint
         * @return
         */
        public double adjustSize(double sizePoint){
            return sizePoint*facteurSize;
        }

        /**
         * Rend le point multiplie par le facteur de dimension de la session
         * @param size
         * @return
         */
        public Point2D adjustSize(Point2D size){
            return size.multiply(facteurSize);
        }

        /**
         * Calcul le facteur de dimension en fonction de la dimension max de la cuisine
         * @return
         */
        private void setFacteurSize() {
            if(dimensionsCuisine.getX()>dimensionsCuisine.getY()){
                facteurSize = 640/dimensionsCuisine.getX();
            } else {
                facteurSize = 460/dimensionsCuisine.getY();
            }
        }

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
            setFacteurSize();

        }

        /**
         * Cree un nouveau gestionnaire de meubles, a invoquer apres .show
         */
        public void createGestionnaireMeubles(){
            this.gestionaireMeubles = new GestionaireMeubles(panneaux.cuisine);
            this.panneaux.leftPanel.getCtrl().bindTotalPrice(this.gestionaireMeubles.totalPricePanierProperty());
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
                this.leftPanel = new LeftPanel();
                this.catalogue = leftPanel.getCatatalogue();
                this.panier = leftPanel.getPanier();
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

            public LeftPanel leftPanel;

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
                this.cuisine = new Cuisine(adjustSize(dimensionsCuisine.getX()),adjustSize(dimensionsCuisine.getY()));

            }
        }
    }




}