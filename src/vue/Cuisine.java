package vue;

import controller.Controller;
import controller.ControllerManager;
import controller.CuisineController;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import modele.Data;

import java.io.IOException;

public class Cuisine extends Pane {

    /**Les dimensions du pane**/
    public Point2D size;

    private CuisineController controller;

    public Paint background;

    /*------Grille---------------*/
    private double grilleInsets = 10;


    /**
     * Cree une cuisine, pour laquelle le plan est au milieu
     * @param LARGEUR la largeur du plan
     * @param HAUTEUR la heuteur du plan
     */
    public Cuisine(double LARGEUR, double HAUTEUR){
        this.size = new Point2D(LARGEUR, HAUTEUR);
        this.background = Color.BEIGE;


        this.controller = new CuisineController(this);
        ControllerManager.cuisineController = this.controller;
        Parent contentContainer = null;
        try {
            //contentContainer = this.controller.loadFXMLWithController("CuisinePlan.fxml");
            contentContainer = this.controller.loadFXMLWithController(getClass().getResource("CuisinePlan.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.getChildren().add(contentContainer);

    }

    /**
     * Dessine tous les meubles du panier
     */
    public void draw(GraphicsContext gc){
        clean(gc);
        //TODO
    }

    /**
     * Clean le canvas
     */
    public void clean(GraphicsContext gc){
        gc.setFill(this.background);
        gc.fillRect(0,0,this.size.getX(),this.size.getY());
        this.drawGrille(gc);
    }


    /**
     * Dessine la grille sur le canvas si isGrille est active
     * @param gc le contexte graphique du plan
     * @see CuisineController#isGrilleProperty()
     */
    public void drawGrille(GraphicsContext gc){
        if(Data.properties.isGrilleVisible.get()){
            //TODO grilles with grilleInsets
            gc.setLineWidth(1);
            gc.setStroke(Color.BURLYWOOD);
            double currentPosX = this.grilleInsets;
            while(currentPosX <= this.size.getX()){ //Lignes verticales
                gc.strokeLine(currentPosX, 0, currentPosX, this.size.getY());
                currentPosX += this.grilleInsets;
            }
            double currentPosY = this.grilleInsets;
            while(currentPosY <= this.size.getX()){ //Lignes horizontales
                gc.strokeLine(0, currentPosY, this.size.getX(), currentPosY);
                currentPosY += this.grilleInsets;
            }
            gc.setLineWidth(2);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(0,0,this.size.getX(),this.size.getY());

            //gc.setFill(Color.GREEN);
            //gc.fillOval(50,50,50,50);
        }
    }

}