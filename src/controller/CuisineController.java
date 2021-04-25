package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import modele.Data;
import modele.GestionaireMeubles;
import vue.Cuisine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;

public class CuisineController extends Controller{

    Cuisine cuisine;

    GestionaireMeubles meubles;

    /**Les dimensions du plan de la cuisine**/
    private Point2D size;



    /**Le graphical context de la cuisine dans la vue**/
    private GraphicsContext gc;


    /*------------FXML----------*/

    @FXML
    private BorderPane contentContainer;

    @FXML
    private Canvas plan;

    public CuisineController(Cuisine cuisine){
        this.cuisine = cuisine;
        this.size = this.cuisine.size;
    }



    /*------------------FXML----------*/

    @FXML
    public void initialize(){
        //Data
        setCanvasSize();
        this.gc = this.plan.getGraphicsContext2D();
        Data.properties.isGrilleVisible.bind(this.grilleBox.selectedProperty());
        Data.properties.isMeubleMovable.bind(this.moveBox.selectedProperty());
        //Listeners
        setChangeGrille();
        //Dessin
        cuisine.clean(this.gc);
    }

    /**
     * Defini la taille du canvas en fonction de la cuisine.
     * <br/>La taille du canvas ne peux pas etre modifiee dynamiquement
     */
    private void setCanvasSize(){
        plan.setWidth(this.size.getX());
        plan.setHeight(this.size.getY());
    }

    /**
     * Rend le dessin de la grille automatique
     */
    private void setChangeGrille(){
        Data.properties.isGrilleVisible.addListener((observable, oldValue, newValue) -> {
            this.cuisine.draw(this.gc);
        });
    }

    /*-----------------Checkbox grille-----------------*/


    @FXML
    private CheckBox grilleBox;

    @FXML
    public CheckBox moveBox;




    public Point2D getPosMouse(MouseEvent e){
        return new Point2D(e.getX(), e.getY());
    }


    /**
     * Renvoie les bounds du plan dans la cuisine
     * @return les bounds du plan
     */
    public Bounds getPlanBoundsInCuisine(){
        Bounds planInScene = plan.localToScene(plan.getBoundsInLocal());
        Bounds planInCuisine = this.cuisine.sceneToLocal(planInScene);
        return planInCuisine;
    }



}
