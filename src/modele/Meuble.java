package modele;

import controller.DragController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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

    /**Est ce que le meuble se trouve dans le panier
     * <br/>Le gestionnaire de meuble y attache un change listener qui l ajoute ou l enleve du panier automatiquement**/
    private BooleanProperty inPanier = new SimpleBooleanProperty(false);


    /**Les dimentions du meuble**/
    protected double LARGEUR, HAUTEUR;

    private Image img = null;

    private String nom;
    private String constructeur;
    private double prix;
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



    /*----------------------Mouvement par fiche-----------------*/

    /**La sauvegarde de la property a laquelle est liee le dragController**/
    private BooleanProperty savedDragBind;

    /**Defini si le mouvement de la forme se fait a partir de la fiche / avec un clic et pas un drag
     * <br/> -> Est ce que le meuble est deplace sans press&drag&drop (depuis catalogue ou panier)
     * <li>
     *     <ul>Si set a true, declanche automatiquement le debut du mouvement</ul>
     *     <ul>Si set a false, arrete le mouvement en cours</ul>
     * </li>
     */
    private BooleanProperty isClickedMove = new SimpleBooleanProperty(false);

    private EventHandler<MouseEvent> dragByClic;
    private EventHandler<MouseEvent> releaseByClic;

    /**
     * Cree un meuble possedant une forme et des fiches. Possede un controlleur integre
     * <br/>Il est possible d ajouter une image a la description du meuble.
     * <br/>Ne pas utiliser le meuble tant qu il n a pas ete ajoute au catalogue du gestionnaire de meubles
     * @param nom Le nom du meuble
     * @param constructeur Le constructeur du meuble
     * @param prix le prix du meuble en euros
     * @param largeur la largeur du meuble en cm
     * @param hauteur la hauteur du meuble en cm
     * @param description la description du meuble
     * @see GestionaireMeubles#addCatalogue(Meuble) 
     */
    public Meuble(String nom, String constructeur, double prix, double largeur, double hauteur, String description){
        identify();

        this.LARGEUR = largeur;
        this.HAUTEUR = hauteur;
        this.nom = nom;
        this.constructeur = constructeur;
        this.prix = prix;
        this.description = description;
        contructFiches();
        buildForme();
        this.dragController = new DragController(this.forme);

        setHandlers();
    }

    /**
     * Cree un meuble possedant une forme et des fiches. Possede un controlleur integre.
     * <br/>Il est possible d ajouter une image a la description du meuble.
     * <br/>Ne pas utiliser le meuble tant qu il n a pas ete ajoute au catalogue du gestionnaire de meubles
     * @param nom Le nom du meuble
     * @param constructeur Le constructeur du meuble
     * @param prix le prix du meuble en euros
     * @param largeur la largeur du meuble en cm
     * @param hauteur la hauteur du meuble en cm
     * @see GestionaireMeubles#addCatalogue(Meuble) 
     */
    public Meuble(String nom, String constructeur, double prix, double largeur, double hauteur){
        this(nom, constructeur, prix,largeur,hauteur, "Ce meuble n a pas de description.");
    }


    /*------------------------------------------------------------------------*/

    /**Set the identity of the meuble. Unique for each meuble
     * @see Meuble#nbMeubles
     * @see Meuble#id**/
    private void identify(){
        this.id = nbMeubles++;
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
     * Renvoie la boolean property qui verifie si le meuble est dans le panier
     * <br/>Modifier sa valeur enleve ou ajoute le meuble au panier :
     * <br/>Le gestionnaire de meuble y a attache un change listener qui l ajoute ou l enleve du panier automatiquement
     * @return le boolean property
     */
    public BooleanProperty isInPanier(){
        return inPanier;
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

    /**
     * Renvoie la propriete verifiant si le meuble est selectionne
     * @return
     */
    public BooleanProperty isSelectedProperty(){
        return this.selected;
    }

    /**
     * Reset la position initiale sur le point a gauche-milieu du parent
     */
    public void reset(){
        this.forme.setTranslateX(0);
        this.forme.setTranslateY(0);
        this.forme.relocate(0, this.forme.getParent().getBoundsInLocal().getHeight()/2);
    }




    /*---------------------------Selection Handlers----------------*/
    private void setHandlers(){
        setEventHandlersDragAndReleaseByClic();
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

        // Cas clic to move

        isClickedMove.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                dragController.getDraggableProperty().unbind();
                dragController.getDraggableProperty().set(false);
                forme.setTranslateX(0); //Reset au cas ou on clique plusieurs fois dessus
                forme.setTranslateY(0);
                grabFormeByFiche();
                forme.getParent().addEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                forme.getParent().addEventFilter(MouseEvent.MOUSE_CLICKED, releaseByClic);
            } else {
                forme.getParent().removeEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                forme.getParent().removeEventFilter(MouseEvent.MOUSE_CLICKED, releaseByClic);
                forme.setTranslateX(0); //Reset au cas ou annule
                forme.setTranslateY(0);
                dragController.getDraggableProperty().bind(savedDragBind);
            }
        });

    }

    /**
     * Defini les handlers pour le drag by clic
     */
    private void setEventHandlersDragAndReleaseByClic(){
        this.dragByClic = event -> {
            if(this.dragController.getDragChecker() != null){
                if(this.dragController.getDragChecker().check()){
                    this.dragController.dragHandler(event);
                }
            } else {
                this.dragController.dragHandler(event);
            }
        };

        this.releaseByClic = event -> {
            if(this.dragController.getReleaseChecker() != null){
                if(this.dragController.getReleaseChecker().check()){
                    if(this.dragController.releaseHandler(event)){
                        isClickedMove.set(false);
                        isInPanier().set(true);
                    }
                }
            } else {
                if(this.dragController.releaseHandler(event)){
                    isClickedMove.set(false);
                }
            }
        };

    }

    /**
     * Bind dragged property to the one given and save it for move by clic
     * @param propertyBindedTo
     */
    public void bindIsDraggedPropertyTo(BooleanProperty propertyBindedTo){
        dragController.getDraggableProperty().bind(propertyBindedTo);
        this.savedDragBind = propertyBindedTo;
    }

    /*----------------------------Deplacement--------------------------*/

    /**
     * Renvoie le dragController du meuble
     * @return
     */
    public DragController getDragController() {
        return dragController;
    }

    /**
     * Defini l action de saisir la forme de la fiche lorsque le mouvement par clic est active.
     * <br/>Defini les anchor et offset dans le dragController
     * @see Meuble#isClickedMove
     */
    private void grabFormeByFiche(){
        Point2D offset = new Point2D(this.LARGEUR/2, this.HAUTEUR/2);
        dragController.setMouseOffsetFromNode(offset);

        Point2D posInParent = dragController.getCurrentPos(forme);
        double anchorInParentX = posInParent.getX() + offset.getX();
        double anchorInParentY = posInParent.getY() + offset.getY();
        Parent parent = forme.getParent();
        Point2D anchorInScene = parent.localToScene(anchorInParentX, anchorInParentY);
        dragController.setAnchors(anchorInScene);
    }

    /**
     * Renvoie le boolean qui defini si un move by clic va commencer.
     * <li>
     *     <ul>Si set a true, declanche automatiquement le debut du mouvement</ul>
     *     <ul>Si set a false, arrete le mouvement en cours</ul>
     * </li>
     * @return le boolean property
     * @see Meuble#isClickedMove
     */
    public BooleanProperty getIsClickedMoveProperty(){
        return isClickedMove;
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
     * Defini la forme du meuble comme enfant du parent donne
     * @param parent le parent du meuble
     */
    public void setParent(Pane parent){
        parent.getChildren().add(this.forme);
    }

    /*--------------------------Fiche----------------------------------*/
    /**
     * Construit les 2 fiches du meuble
     */
    public void contructFiches(){
        this.fichePanier = new PetiteFiche(this);
        this.ficheCatalogue = new PetiteFiche(this);
        //Si il est visible = false, le parent va se reorganiser
        this.fichePanier.managedProperty().bind(this.fichePanier.visibleProperty());
        this.ficheCatalogue.managedProperty().bind(this.ficheCatalogue.visibleProperty());
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
        return Double.toString(prix) + "€";
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
    public void getDescription(String description) {
        this.description = description;
    }
}
