package modele;

import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
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

    private Image img = null;

    private String nom;
    private String constructeur;
    private String prix;
    private String description;

/*-----------Forme---------------*/

    /**La forme du meuble dans la cuisine**/
    private Shape forme;
    /**The fill property of the forme while in normal state**/
    private Paint paintUsual = Color.AQUA;
    /**The fill property of the forme while in selected state**/
    private Paint paintSelected = Color.RED;

    private DragController dragController;


    /*-----------------Fiche-------------------*/
    /**La petite fiche utilisee dans le catalogue**/
    private PetiteFiche ficheCatalogue;
    /**La petite fiche utilisee dans le panier**/
    private PetiteFiche fichePanier;


    /**
     * Cree un meuble possedant une forme et des fiches. Possede un controlleur integre
     * <br/>Il est possible d ajouter une image a la description du meuble
     * @param nom Le nom du meuble
     * @param constructeur Le constructeur du meuble
     * @param prix le prix du meuble en euros
     * @param largeur la largeur du meuble en cm
     * @param hauteur la hauteur du meuble en cm
     * @param description la description du meuble
     */
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

    /**
     * Cree un meuble possedant une forme et des fiches. Possede un controlleur integre
     * <br/>Il est possible d ajouter une image a la description du meuble
     * @param nom Le nom du meuble
     * @param constructeur Le constructeur du meuble
     * @param prix le prix du meuble en euros
     * @param largeur la largeur du meuble en cm
     * @param hauteur la hauteur du meuble en cm
     */
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
    public void setImg(Image img){
        this.img = img;
    }

    /**
     * Renvoie l image du meuble. Peu etre null
     * @return l image du meuble
     */
    public Image getImg(){return this.img;}

    /**
     * Deplace le point vers celui donne
     * @param p
     */
    public void move(Point2D p){
        //TODO move ? Check its use
    }

    /**Renvoie la position du meuble
     * @see DragController#getCurrentPos(Node) **/
    public Point2D getPos(){
        return this.dragController.getCurrentPos(this.forme);
    }

    /**Renvoie les dimensions du meuble**/
    public Point2D getSize(){return new Point2D(this.LARGEUR, this.HAUTEUR);}

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
        this.fichePanier = new PetiteFiche(this);
        this.ficheCatalogue = new PetiteFiche(this);
    }

    /**
     * Renvoie la fiche du meuble pour le panier
     */
    public PetiteFiche getLittleFichePanier(){
        return this.fichePanier;
    }

    /**
     * Renvoie la fiche du meuble pour le catalogue
     */
    public PetiteFiche getLittleFicheCatalogue(){
        return this.ficheCatalogue;
    }

    /**
     * Renvoie la grande fiche du meuble avec sa description
     */
    public  void getBigFiche(){
        //TODO renvoie variable globale
    }


    /*-----------------------Informations meuble-------------------*/

    /**
     * Renvoie le nom du meuble
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le prix du meuble en euros
     * @return information en tant que String
     */
    public String getPrix() {
        return prix;
    }

    /**
     * Renvoie les dimensions du meuble
     * @return information en tant que String sous forme 0 x 0
     */
    public String getDimensions(){
        return this.LARGEUR + " x " + this.HAUTEUR;
    }

    /**
     * Renvoie le constructeur du meuble
     * @return information en tant que String
     */
    public String getConstructeur() {
        return constructeur;
    }

    /**
     * Renvoie la description du meuble
     * @param description information en tant que String
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
