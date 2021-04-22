package modele;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class GestionaireMeubles {

    public Meuble selection = null;

    /**Le catalogue des meubles**/
    private ArrayList<Meuble> catalogue = new ArrayList<>();

    /**Les meubles dans le panier**/
    private ArrayList<Meuble> panier = new ArrayList<>();

    /**
     * Le gestionnaire de tous les meubles
     */
    public GestionaireMeubles(){
        initCatalogue();
    }


    /**
     * Enleve tout meuble de la selection
     */
    public void unselect(){
        if(this.selection != null){
            this.selection.unselect();
        }
        this.selection = null;
    }

    /**
     * Selectionne le meuble situe a la position donnee
     * @param posMeuble
     */
    public void select(Point2D posMeuble){
        Meuble m = getMeuble(posMeuble);
        if(m != null){
            this.selection = m;
            m.select();
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
     * Ajoute un meuble au panier
     * @param meuble
     */
    public void addToPanier(Meuble meuble){
        this.panier.add(meuble);
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
     * @param meuble
     */
    public void removeFromPanier(Meuble meuble){
        this.panier.remove(meuble);
    }


    /*--------------Catalogue----------------*/

    /**
     * Initialise le catalogue, cree tous les meubles
     */
    private void initCatalogue(){
        //TODO init les meubles et les met dans la vue
    }

}
