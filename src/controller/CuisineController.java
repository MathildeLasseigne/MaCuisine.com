package controller;

import modele.GestionaireMeubles;
import modele.Meuble;
import vue.Cuisine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;

public class CuisineController {

    Cuisine cuisine;

    GestionaireMeubles meubles;

    /**Le graphical context de la cuisine dans la vue**/
    private GraphicsContext gc;

    public CuisineController(Cuisine cuisine){
        this.cuisine = cuisine;
    }

    public Point2D getPosMouse(MouseEvent e){
        return new Point2D(e.getX(), e.getY());
    }


    /**
     * Selectionne le meuble sous la souris
     * @param e
     */
    public void selectMeuble(MouseEvent e){
        meubles.select(getPosMouse(e));
    }

    /**
     * Deplace le meuble selectionne
     * @param e
     */
    public void dragMeuble(MouseEvent e){
        Meuble m = meubles.selection;
        if(m != null){
            m.move(getPosMouse(e));
        }
    }

    /**
     * Lache le meuble selectionne
     * @param e
     */
    public boolean lacheMeuble(MouseEvent e){
        Meuble m = meubles.selection;
        if(m != null){
            m.move(getPosMouse(e));
            meubles.unselect();
            return true;
        } else {
            return false;
        }
    }

}
