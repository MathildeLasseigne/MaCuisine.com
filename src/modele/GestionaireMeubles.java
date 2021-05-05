package modele;

import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
//import org.jetbrains.annotations.TestOnly;
import vue.Cuisine;

import java.io.Serializable;
import java.util.ArrayList;

public class GestionaireMeubles implements Serializable {

    /**La cuisine sur laquelle sont affiches tous les meubles**/
    private Cuisine cuisine;


    private MeubleModele selection = null;

    public BooleanProperty isMovable = new SimpleBooleanProperty();

    /**Le catalogue des meubles**/
    private ArrayList<MeubleModele> catalogue = new ArrayList<>();

    /**Les meubles dans le panier**/
    private ArrayList<MeubleModele> panier = new ArrayList<>();

    private DoubleProperty totalPricePanier = new SimpleDoubleProperty(0);

    /**
     * Le gestionnaire de tous les meubles.
     * <br/>Pour une bonne initialisation des boundaries limitant les meubles, initialiser apres scene.show
     */
    public GestionaireMeubles(Cuisine cuisine){
        this.cuisine = cuisine;
        initCatalogue();
        this.isMovable.bind(Data.getCurrentSession().properties.isMeubleMovable);
        Data.getCurrentSession().gestionaireMeubles = this;
    }


    /**
     * Renvoie le meuble actuellement selectionne
     * @return le meuble selectionne
     * @see GestionaireMeubles#select(MeubleModele)
     * @see GestionaireMeubles#unselect()
     */
    public MeubleModele getSelection() {
        return selection;
    }

    /**
     * Enleve tout meuble de la selection
     * @see GestionaireMeubles#getSelection()
     * @see GestionaireMeubles#select(MeubleModele)
     * @see MeubleModele#unselect()
     */
    public void unselect(){
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
    public void select(MeubleModele meubleModele){
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
        Data.getCurrentSession().panneaux.getPanier().add(meubleModele);
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
        Data.getCurrentSession().panneaux.getPanier().remove(meubleModele);
    }


    /**
     * Met a jour le prix total du panier
     */
    public void updateTotalPricePanier(){
        double price = 0;
        for(MeubleModele m : panier){
            price += m.getPrixTotal();
        }
        this.totalPricePanierProperty().set(price);
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
        meubleModele.setBoundaries(this.cuisine.getBoundsInLocal(), this.cuisine.getController().getPlanBoundsInCuisine());
        meubleModele.bindIsDraggedPropertyTo(this.isMovable);
        meubleModele.isInPanierProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addToPanier(meubleModele);
            } else {
                removeFromPanier(meubleModele);
            }
            updateTotalPricePanier();
        });
        setChecker(meubleModele);
        Data.getCurrentSession().panneaux.initCommit(meubleModele);
        //meubleModele.reset();
    }


    /**
     * Initialise le catalogue, cree tous les meubles
     */
    private void initCatalogue(){
        for(MeubleModele m : Data.getCurrentSession().getMeubleModeleCatalogue()){
            addCatalogue(m);
        }
    }

    /**
     * Ajoute les nb premiers elements du catalogue au panier.
     * <br/>A utiliser uniquement pour les tests
     * @param nb le nombre d elements a prendre dans le catalogue
     */
    //@TestOnly
    public void initPanierTest(int nb){
        int itMax = nb -1;
        if(nb > this.catalogue.size()){
            itMax = this.catalogue.size()-1;
        }
        for(int i = 0; i<= itMax; i++){
            this.catalogue.get(i).addToPanier();
        }
    }

    /**
     * Renvoie le cout total du panier
     * @return
     */
    public DoubleProperty totalPricePanierProperty() {
        return totalPricePanier;
    }
}
