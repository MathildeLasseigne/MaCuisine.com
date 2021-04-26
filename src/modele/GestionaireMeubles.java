package modele;

import controller.ControllerManager;
import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import vue.Cuisine;

import java.util.ArrayList;

public class GestionaireMeubles {

    /**La cuisine sur laquelle sont affiches tous les meubles**/
    private Cuisine cuisine;


    private static Meuble selection = null;

    public BooleanProperty isMovable = new SimpleBooleanProperty();

    /**Le catalogue des meubles**/
    private ArrayList<Meuble> catalogue = new ArrayList<>();

    /**Les meubles dans le panier**/
    private ArrayList<Meuble> panier = new ArrayList<>();

    /**
     * Le gestionnaire de tous les meubles.
     * <br/>Pour une bonne initialisation des boundaries limitant les meubles, initialiser apres scene.show
     */
    public GestionaireMeubles(Cuisine cuisine){
        this.cuisine = cuisine;
        initCatalogue();
        this.isMovable.bind(Data.properties.isMeubleMovable);
    }


    /**
     * Renvoie le meuble actuellement selectionne
     * @return le meuble selectionne
     * @see GestionaireMeubles#select(Meuble)
     * @see GestionaireMeubles#unselect()
     */
    public static Meuble getSelection() {
        return selection;
    }

    /**
     * Enleve tout meuble de la selection
     * @see GestionaireMeubles#getSelection()
     * @see GestionaireMeubles#select(Meuble)
     */
    public static void unselect(){
        if(selection != null){
            selection.unselect();
            selection = null;
        }
    }


    /**
     * Selectionne le meuble
     * @param meuble le meuble a selectionner la selection
     * @see GestionaireMeubles#getSelection()
     * @see GestionaireMeubles#unselect()
     */
    public static void select(Meuble meuble){
        if(meuble != null){
            if(selection != null){
                if(! selection.equals(meuble)){
                    unselect();
                    selection = meuble;
                    meuble.select();
                    meuble.getForme().toFront();
                }
            } else {
                unselect();
                selection = meuble;
                meuble.select();
                meuble.getForme().toFront();
            }
        } else {
            throw new IllegalStateException("Meuble selected must not be null");
        }
    }

    /**
     * Renvoie le meuble correspondant à la sélection
     * @param posMeuble
     * @return
     */
    public Meuble getMeuble(Point2D posMeuble){
        return null;
    }


    /*-------------Panier---------------*/


    /**
     * Ajoute un meuble au panier.
     * <br/>Tout ajout dans le panier doit se faire a partir du meuble
     * @param meuble meuble a placer
     * @see Meuble#isInPanier()
     */
    private void addToPanier(Meuble meuble){
        this.panier.add(meuble);
        meuble.getForme().setVisible(true);
        Data.panneaux.panier.add(meuble);
    }

    /**
     * Verifie si un meuble est dans le panier
     * @param meuble
     * @return
     */
    public boolean isInPanier(Meuble meuble){
        return this.panier.contains(meuble);
    }

    /**
     * Enleve un meuble du panier
     * <br/>Tout retrait du le panier doit se faire a partir du meuble
     * @param meuble meuble a enlever
     * @see Meuble#isInPanier()
     */
    private void removeFromPanier(Meuble meuble){
        this.panier.remove(meuble);
        meuble.getForme().setVisible(false);
        meuble.reset();
        Data.panneaux.panier.remove(meuble);
    }




    /**
     * Set les checkers sur le meuble
     * @param meuble le meuble a initialiser
     */
    private void setChecker(Meuble meuble){
        DragController mCtrl = meuble.getDragController();
        DragController.SimpleChecker release = new DragController.SimpleChecker() {
            @Override
            public boolean check() {
                for(Meuble m : panier){
                    if(! meuble.equals(m)){
                        if(meuble.intersect(m)){
                            return false;
                        }
                    }
                }
                return true;
            }
        };
        mCtrl.setReleaseChecker(release);
    }
    /*--------------Catalogue----------------*/

    /**
     * Ajoute un meuble au catalogue et <b>l'initialise</b>
     * @param m le meuble a ajouter
     */
    public void addCatalogue(Meuble m){
        this.catalogue.add(m);
        this.cuisine.add(m);
        m.getDragController().setBoundaries(this.cuisine.getBoundsInLocal(), ControllerManager.cuisineController.getPlanBoundsInCuisine());
        m.bindIsDraggedPropertyTo(this.isMovable);
        m.isInPanier().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addToPanier(m);
            } else {
                removeFromPanier(m);
            }
        });
        m.getForme().setVisible(false);
        setChecker(m);
        Data.panneaux.initCommit(m);
    }


    /**
     * Initialise le catalogue, cree tous les meubles
     */
    private void initCatalogue(){
        //TODO init les meubles et les met dans la vue
        Meuble m1 = new Meuble("Meuble1", "MaCuisine.com", 100,100,50);
        addCatalogue(m1);
        Meuble m2 = new Meuble("Meuble2", "MaCuisine.com", 100,60,60);
        addCatalogue(m2);
        Meuble m3 = new Meuble("Meuble3", "MaCuisine.com", 100,30,60);
        addCatalogue(m3);
        Meuble m4 = new Meuble("Meuble4", "MaCuisine.com", 100,20,50);
        addCatalogue(m4);
        Meuble m5 = new Meuble("Meuble5", "MaCuisine.com", 30,30,30);
        addCatalogue(m5);
        //Location pour tests
        m1.relocate(50, 50);
        m2.relocate(300,200);
        m3.relocate(100,300);
        m4.relocate(100,300);
    }

    /**
     * Ajoute les nb premiers elements du catalogue au panier.
     * <br/>A utiliser uniquement pour les tests
     * @param nb le nombre d elements a prendre dans le catalogue
     */
    public void initPanierTest(int nb){
        int itMax = nb -1;
        if(nb > this.catalogue.size()){
            itMax = this.catalogue.size()-1;
        }
        for(int i = 0; i<= itMax; i++){
            this.catalogue.get(i).isInPanier().set(true);
        }
    }

}
