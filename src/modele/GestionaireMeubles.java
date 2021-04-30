package modele;

import controller.ControllerManager;
import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.TestOnly;
import vue.Cuisine;

import java.util.ArrayList;

public class GestionaireMeubles {

    /**La cuisine sur laquelle sont affiches tous les meubles**/
    private Cuisine cuisine;


    private static MeubleModele selection = null;

    public BooleanProperty isMovable = new SimpleBooleanProperty();

    /**Le catalogue des meubles**/
    private ArrayList<MeubleModele> catalogue = new ArrayList<>();

    /**Les meubles dans le panier**/
    private ArrayList<MeubleModele> panier = new ArrayList<>();

    /**
     * Le gestionnaire de tous les meubles.
     * <br/>Pour une bonne initialisation des boundaries limitant les meubles, initialiser apres scene.show
     */
    public GestionaireMeubles(Cuisine cuisine){
        this.cuisine = cuisine;
        initCatalogue();
        this.isMovable.bind(Data.properties.isMeubleMovable);
        Data.gestionaireMeubles = this;
    }


    /**
     * Renvoie le meuble actuellement selectionne
     * @return le meuble selectionne
     * @see GestionaireMeubles#select(MeubleModele)
     * @see GestionaireMeubles#unselect()
     */
    public static MeubleModele getSelection() {
        return selection;
    }

    /**
     * Enleve tout meuble de la selection
     * @see GestionaireMeubles#getSelection()
     * @see GestionaireMeubles#select(MeubleModele)
     * @see MeubleModele#unselect()
     */
    public static void unselect(){
        if(selection != null){
            selection.unselect();
            selection = null;
        }
    }


    /**
     * Selectionne le meuble
     * @param meubleModele le meuble a selectionner
     * @see GestionaireMeubles#getSelection()
     * @see GestionaireMeubles#unselect()
     * @see MeubleModele#select()
     */
    public static void select(MeubleModele meubleModele){
        if(meubleModele != null){
            if(selection != null){
                if(! selection.equals(meubleModele)){
                    unselect();
                    selection = meubleModele;
                    meubleModele.select();
                }
            } else {
                unselect();
                selection = meubleModele;
                meubleModele.select();
            }
        } else {
            throw new IllegalStateException("MeubleModel selected must not be null");
        }
    }

    /**
     * Renvoie le meuble correspondant à la sélection
     * @param posMeuble
     * @return
     */
    public MeubleModele getMeuble(Point2D posMeuble){
        return null;
    }


    /*-------------Panier---------------*/


    /**
     * Ajoute un meuble au panier.
     * <br/>Tout ajout dans le panier doit se faire a partir du meuble
     * @param meubleModele meuble a placer
     * @see MeubleModele#isInPanierProperty()
     * @see MeubleModele#addToPanier()
     */
    private void addToPanier(MeubleModele meubleModele){
        this.panier.add(meubleModele);
        Data.panneaux.panier.add(meubleModele);
    }

    /**
     * Verifie si un meuble est dans le panier
     * @param meubleModele verifie si le meuble est dans le panier
     * @return
     */
    public boolean isInPanier(MeubleModele meubleModele){
        return this.panier.contains(meubleModele);
    }

    /**
     * Enleve un meuble modele du panier
     * <br/>Tout retrait du le panier doit se faire a partir du meuble
     * @param meubleModele meuble a enlever
     * @see MeubleModele#isInPanierProperty()
     * @see MeubleModele#removeFromPanier()
     */
    private void removeFromPanier(MeubleModele meubleModele){
        this.panier.remove(meubleModele);
        //meubleModele.isInPlanProperty().set(false);
        //meubleModele.reset();
        Data.panneaux.panier.remove(meubleModele);
    }




    /**
     * Set les checkers sur le meuble
     * @param meubleModele le meuble a initialiser
     */
    private void setChecker(MeubleModele meubleModele){
        DragController.SimpleChecker release = new DragController.SimpleChecker() {
            @Override
            public boolean check() {
                for(MeubleModele m : panier){
                    if(m.intersect(meubleModele)){
                        return false;
                    }
                }
                return true;
            }
        };
        meubleModele.setChecker(release);
    }
    /*--------------Catalogue----------------*/

    /**
     * Ajoute un meuble au catalogue et <b>l'initialise</b>
     * @param meubleModele le meuble a ajouter
     */
    public void addCatalogue(MeubleModele meubleModele){
        this.catalogue.add(meubleModele);
        //this.cuisine.add(meubleModele);
        meubleModele.setBoundaries(this.cuisine.getBoundsInLocal(), ControllerManager.cuisineController.getPlanBoundsInCuisine());
        meubleModele.bindIsDraggedPropertyTo(this.isMovable);
        meubleModele.isInPanierProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addToPanier(meubleModele);
            } else {
                removeFromPanier(meubleModele);
            }
        });
        setChecker(meubleModele);
        Data.panneaux.initCommit(meubleModele);
        //meubleModele.reset();
    }


    /**
     * Initialise le catalogue, cree tous les meubles
     */
    private void initCatalogue(){
        //TODO init les meubles et les met dans la vue
        MeubleModele m1 = new MeubleModele("Meuble1", "MaCuisine.com", 100,100,50);
        addCatalogue(m1);
        MeubleModele m2 = new MeubleModele("Meuble2", "MaCuisine.com", 100,60,60);
        addCatalogue(m2);
        MeubleModele m3 = new MeubleModele("Meuble3", "MaCuisine.com", 100,30,60);
        addCatalogue(m3);
        MeubleModele m4 = new MeubleModele("Meuble4", "MaCuisine.com", 100,20,50);
        addCatalogue(m4);
        MeubleModele m5 = new MeubleModele("Meuble5", "MaCuisine.com", 30,30,30);
        addCatalogue(m5);
    }

    /**
     * Ajoute les nb premiers elements du catalogue au panier.
     * <br/>A utiliser uniquement pour les tests
     * @param nb le nombre d elements a prendre dans le catalogue
     */
    @TestOnly
    public void initPanierTest(int nb){
        int itMax = nb -1;
        if(nb > this.catalogue.size()){
            itMax = this.catalogue.size()-1;
        }
        for(int i = 0; i<= itMax; i++){
            this.catalogue.get(i).addToPanier();
        }
    }

}
