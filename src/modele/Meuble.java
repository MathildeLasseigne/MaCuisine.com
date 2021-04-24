package modele;

import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import java.awt.image.BufferedImage;

public class Meuble {

/*-------------ID--------------*/
    /**Le nombre total de meubles crees**/
    private static int nbMeubles = 0;
    /**L id du meuble. Est unique**/
    protected int id;

    /**Est ce que le meuble est la selection active**/
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    /**Est ce que le meuble se trouve dans le panier**/
    public BooleanProperty inPanier = new SimpleBooleanProperty(false);

    /**Est ce que le meuble est deplace sans press&drag&drop (depuis catalogue ou panier)**/
    public BooleanProperty isDraggedFromCatalogue = new SimpleBooleanProperty(false);

    /**Les dimentions du meuble**/
    protected double LARGEUR, HAUTEUR;

    private BufferedImage img = null;

    private String nom;
    private String constructeur;
    private String prix;
    private String description;


    /**La forme du meuble dans la cuisine**/
    private Shape forme;
    /**The fill property of the forme while in normal state**/
    private Paint paintUsual = Color.AQUA;
    /**The fill property of the forme while in selected state**/
    private Paint paintSelected = Color.RED;

    private DragController dragController;

    public Meuble(String nom, String constructeur, double prix, double largeur, double hauteur, String description){
        identify();

        this.LARGEUR = largeur;
        this.HAUTEUR = hauteur;
        this.nom = nom;
        this.constructeur = constructeur;
        this.prix = Double.toString(prix) + "€";
        this.description = description;
        contructFiches();
        buildForme();
        this.dragController = new DragController(this.forme);

        setHandlers();
    }

    public Meuble(String nom, String constructeur, double prix, double largeur, double hauteur){
        this(nom, constructeur, prix,largeur,hauteur, "Ce meuble n a pas de description.");
    }

    /**Set the identity of the meuble. Unique for each meuble
     * @see Meuble#nbMeubles
     * @see Meuble#id**/
    private void identify(){
        this.id = nbMeubles; nbMeubles++;
    }

    /**Renvoie le nombre total de meubles crees**/
    public static int getNbMeubles() {
        return nbMeubles;
    }

    /**
     * Renvoie l id du meuble
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Defini une image pour le meuble
     * @param img
     */
    public void setImg(BufferedImage img){
        this.img = img;
    }

    /**
     * Deplace le point vers celui donne
     * @param p
     */
    public void move(Point2D p){
        //TODO move ? Check its use
    }

    public Point2D getPos(){
        return this.dragController.getCurrentPos(this.forme);
    }

    /**
     * Verifie si un meuble est egal a un autre par leur id
     * @param m le meuble a comparer
     * @return le resultat de la comparaison
     */
    public boolean equals(Meuble m){
        return this.id == m.id;
     }


    /*-------------------Selection----------------------*/

    /**
     * Selectionne le meuble
     */
    public void select(){
        this.selected.set(true);
    }

    /**
     * Deselectionne le meuble
     */
    public void unselect(){
        this.selected.set(false);
    }

    /**
     * Verifie si le meuble est selectionne
     * @return
     */
    public boolean isSelected(){
        return this.selected.get();
    }



    /*---------------------------Selection Handlers----------------*/

    private void setHandlers(){
        Meuble self = this;
        this.forme.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GestionaireMeubles.select(self);
            }
        });

        selected.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                forme.setFill(paintSelected);
            } else {
                forme.setFill(paintUsual);
            }
        });
    }

    /*----------------------------Deplacement--------------------------*/

    /**
     * Renvoie le dragController du meuble
     * @return
     */
    public DragController getDragController() {
        return dragController;
    }


    /*--------------------------Forme-----------------------------*/

    /**
     * Cree la forme de la cuisine en fonction de ses dimensions
     */
    private void buildForme(){
        this.forme = new Rectangle(this.LARGEUR, this.HAUTEUR);
        this.forme.setFill(paintUsual);
        this.forme.setStroke(Color.BLACK);
        this.forme.setStrokeWidth(2);
    }


    /**
     * Renvoie la forme avec laquelle le meuble apparait dans le plan
     * @return la forme du meuble
     */
    public Shape getForme() {
        return forme;
    }

    /**
     * Deplace la forme du meuble en utilisant la methode relocate de Node
     * @param x la position x du meuble sur le plan
     * @param y la position y du meuble sur le plan
     * @see javafx.scene.Node#relocate(double, double)
     */
    public void relocate(double x, double y){
        this.forme.relocate(x,y);
    }

    /**
     * Verifie si la position donnee est dans le meuble
     * @param pos
     * @return
     */
    public boolean isInside(Point2D pos){
        //TODO isInside meuble
        return false;
    }

    /**
     * Verifie si les 2 meubles se superposent
     * @param m le meuble a comparer
     * @return le resultat de la comparaison
     */
    public boolean intersect(Meuble m){
        return this.forme.intersects(this.forme.sceneToLocal(m.getForme().localToScene(
                m.getForme().getBoundsInLocal())));
    }

    /**
     * Verifie si il y a collision entre 2 meubles
     * @param m
     * @return
     */
    public boolean collision(Meuble m){
        return false;
    }

    /*--------------------------Fiche----------------------------------*/
    /**
     * Construit les 2 fiches du meuble
     */
    public void contructFiches(){
        //TODO construire les 2 fiches avec element javafx. Construit dans var globale. Petite & grande fiche
    }

    /**
     * Renvoie la fiche du meuble //Pas void !!
     */
    public void getLittleFiche(){
        //TODO renvoie la variable globale
    }

    /**
     * Renvoie la grande fiche du meuble avec sa description
     */
    public  void getBigFiche(){
        //TODO renvoie variable globale
    }

}
