package modele;

import controller.DragController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class MeubleModele {

/*-------------ID--------------*/
    /**Le nombre total de modeles de meuble crees**/
    private static int nbMeublesModele = 0;
    /**L id du meuble. Est unique**/
    protected int id;

    /**Est ce que le meuble est la selection active**/
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    /**Est ce que le meuble se trouve dans le panier
     * <br/>Le gestionnaire de meuble y attache un change listener qui l ajoute ou l enleve du panier automatiquement**/
    private BooleanProperty inPanier = new SimpleBooleanProperty(false);

    private BooleanProperty inPlan = new SimpleBooleanProperty(false);

    /**Les dimentions du meuble**/
    protected double LARGEUR, HAUTEUR;

    private Image img = null;

    private String nom;
    private String constructeur;
    private double prix;
    private String description;

    /*-----------Meubles---------------*/

    /**La forme du meuble dans la cuisine**/
    private Shape meuble;
    /**The fill property of the forme while in normal state**/
    private Paint paintUsual = Color.AQUA;
    /**The fill property of the forme while in selected state**/
    private Paint paintSelected = Color.RED;

    private DragController dragController;

    /**Le nombre total de meubles crees. utilise pour l id des meubles**/
    private int nbTotalMeuble = 0;

    /**Le nombre de meubles actuellement dans le panier**/
    public IntegerProperty nbMeuble = new SimpleIntegerProperty(0);

    /**Le meuble selectionne. Peut ne pas etre dans le panier**/
    private Meuble selectionMeuble = null;

    /**La liste des meubles dans le panier**/
    private ArrayList<Meuble> listMeuble = new ArrayList<>();

    /*-----------------Fiche-------------------*/
    /**La petite fiche utilisee dans le catalogue**/
    private PetiteFiche ficheCatalogue;
    /**La petite fiche utilisee dans le panier**/
    private PetiteFiche fichePanier;
    /**La fiche ou est inscrite toutes les infos**/
    private BigFiche infoFiche;



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

    private DragController.SimpleChecker releaseChecker = null;
    /**Les bounds des dragController**/
    private Bounds dragBoundary, releaseBoundary = null;

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
     * @see GestionaireMeubles#addCatalogue(MeubleModele)
     */
    public MeubleModele(String nom, String constructeur, double prix, double largeur, double hauteur, String description){
        identify();

        this.LARGEUR = largeur;
        this.HAUTEUR = hauteur;
        this.nom = nom;
        this.constructeur = constructeur;
        this.prix = prix;
        this.description = description;
        contructFiches();
        buildForme();
        this.dragController = new DragController(this.meuble);

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
     * @see GestionaireMeubles#addCatalogue(MeubleModele)
     */
    public MeubleModele(String nom, String constructeur, double prix, double largeur, double hauteur){
        this(nom, constructeur, prix,largeur,hauteur, "Ce meuble n a pas de description."+Data.LoremIpsum);
    }


    /*------------------------------------------------------------------------*/

    /**Set the identity of the meubleModele. Unique for each meuble model
     * @see MeubleModele#nbMeublesModele
     * @see MeubleModele#id**/
    private void identify(){
        this.id = nbMeublesModele++;
    }

    /**Renvoie le nombre total de modeles de meuble crees**/
    public static int getNbMeublesModele() {
        return nbMeublesModele;
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
        return getDragController().getCurrentPos(getMeuble());
    }

    /**Renvoie les dimensions du meuble**/
    public Point2D getSize(){return new Point2D(this.LARGEUR, this.HAUTEUR);}

    /**
     * Renvoie la boolean property qui verifie si le meuble est dans le panier
     * <br/>Modifier sa valeur enleve ou ajoute le meuble au panier :
     * <br/>Le gestionnaire de meuble y a attache un change listener qui l ajoute ou l enleve du panier automatiquement
     * @return le boolean property
     * @see MeubleModele#addToPanier()
     * @see MeubleModele#removeFromPanier()
     */
    public BooleanProperty isInPanier(){
        return inPanier;
    }

    /**
     * Verifie si un meuble est egal a un autre par leur id
     * @param m le meuble a comparer
     * @return le resultat de la comparaison
     */
    public boolean equals(MeubleModele m){
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
     * Selectionne le meuble actuel
     * @param meuble
     */
    private void select(Meuble meuble){
        unselectMeuble();
        this.selectionMeuble = meuble;
        meuble.selected.set(true);

        meuble.getForme().toFront();
    }

    /**
     * Deselectionne le modele meuble, et supprime le meuble en cours si il n est pas dans le panier
     * @see MeubleModele#unselectMeuble()
     */
    public void unselect(){
        this.getDragController().resetDrag();
        this.isClickedMove.set(false);
        this.selected.set(false);
        if(this.selectionMeuble != null){
            unselectMeuble();
        }
    }

    /**
     * Deselectionne le meuble selectionne et le supprime si il n est pas dans le panier
     */
    public void unselectMeuble(){
        if(this.selectionMeuble != null){
            if( ! this.selectionMeuble.inPanier.get()){
                this.selectionMeuble.unbind();
            } else {
                this.selectionMeuble.selected.set(false);
            }
            this.selectionMeuble = null;
        }
    }

    /**
     * Verifie si la selection de meuble actuelle est == null
     * @return
     */
    public boolean isEmptyMeubleSelection(){
        return this.selectionMeuble == null;
    }


    /**
     * Verifie si le meuble modele est selectionne
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
     * Reset la position initiale sur le point a gauche-milieu du parent et retire du plan en le rendant invisible
     */
    public void reset(){
        getMeuble().setTranslateX(0);
        getMeuble().setTranslateY(0);
        getMeuble().relocate(0, getMeuble().getParent().getBoundsInLocal().getHeight()/2);
        this.inPlan.set(false);
    }

    /**
     * Cree un nouveau meuble qui n est pas dans le panier
     */
    private void createMeuble(){
        unselectMeuble();
        this.selectionMeuble = new Meuble();
    }

    /**
     * Supprime le meuble donne du panier
     * <br/>A utiliser avec unselect pour gerer le cas ou le meuble est selectionne
     * @param meuble
     * @see MeubleModele#unselectMeuble()
     */
    private void removeMeuble(Meuble meuble){
        meuble.unbind();
        if(meuble.inPanier.get()){
            meuble.inPanier.set(false);
        }
    }

    /**
     * Ajoute le meuble modele au panier si il ne l est pas deja, ainsi que son meuble selectionne
     */
    public void addToPanier(){
        if(! getMeuble().inPanier.get()){
            getMeuble().inPanier.set(true);
        }
        if(! isInPanier().get()){
            isInPanier().set(true);
        }
    }

    /**
     * Retire le meuble du panier si il l est
     */
    public void removeFromPanier(){
        if(getMeuble().inPanier.get()){
            getMeuble().inPanier.set(false);
        }
        if(nbMeuble.get() == 0){
            isInPanier().set(false);
        }
    }

    /**
     * Verifie que le meuble selectionne est visible dans le plan.
     * @return
     * @see MeubleModele#getMeuble()
     */
    public BooleanProperty isInPlanProperty(){
        return this.getMeuble().inPlan;
    }




    /*---------------------------Selection Handlers----------------*/
    private void setHandlers(){
        setEventHandlersDragAndReleaseByClic();
        MeubleModele self = this;
        getMeuble().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GestionaireMeubles.select(self);
            }
        });

        selected.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                getMeuble().setFill(paintSelected);
            } else {
                getMeuble().setFill(paintUsual);
            }
        });

        // Cas clic to move

        isClickedMove.addListener((observable, oldValue, newValue) -> {
            if (newValue && ! oldValue) {
                getDragController().getDraggableProperty().unbind();
                getDragController().getDraggableProperty().set(false);
                getMeuble().setTranslateX(0); //Reset au cas ou on clique plusieurs fois dessus
                getMeuble().setTranslateY(0);
                grabFormeByFiche();
                getMeuble().getParent().addEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                getMeuble().getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, releaseByClic);
            } else if(! newValue && oldValue) {
                getMeuble().getParent().removeEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                getMeuble().getParent().removeEventFilter(MouseEvent.MOUSE_PRESSED, releaseByClic);
                getMeuble().setTranslateX(0); //Reset au cas ou annule
                getMeuble().setTranslateY(0);
                getDragController().getDraggableProperty().bind(savedDragBind);
            }
        });

        inPlan.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(()->{
                getMeuble().getParent().requestFocus();
                getMeuble().requestFocus();
            });
        });

    }

    /**
     * Defini les handlers pour le drag by clic
     */
    private void setEventHandlersDragAndReleaseByClic(){
        this.dragByClic = event -> {
            getMeuble().requestFocus();
            if(getDragController().getDragChecker() != null){
                if(getDragController().getDragChecker().check()){
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            getDragController().dragHandler(event);
                        }
                    });

                }
            } else {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        getDragController().dragHandler(event);
                    }
                });
                //this.dragController.dragHandler(event);
            }
        };

        this.releaseByClic = event -> {
            getMeuble().requestFocus();
            if(getDragController().getReleaseChecker() != null){
                if(getDragController().getReleaseChecker().check()){
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            if(getDragController().releaseHandler(event)){
                                isClickedMove.set(false);
                                addToPanier();
                            } else {
                                getMeuble().setTranslateX(0);
                                getMeuble().setTranslateY(0);
                                grabFormeByFiche();
                            }
                        }
                    });

                } else {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            getMeuble().setTranslateX(0);
                            getMeuble().setTranslateY(0);
                            grabFormeByFiche();
                        }
                    });

                }
            } else {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        if(getDragController().releaseHandler(event)){
                            isClickedMove.set(false);
                        }
                    }
                });

            }
        };

    }

    /**
     * Bind dragged property to the one given and save it for move by clic
     * @param propertyBindedTo
     */
    public void bindIsDraggedPropertyTo(BooleanProperty propertyBindedTo){
        getDragController().getDraggableProperty().bind(propertyBindedTo);
        this.savedDragBind = propertyBindedTo;
    }

    /**
     * Enregistre le checker pour les dragController
     * @param checker
     */
    public void setChecker(DragController.SimpleChecker checker){
        this.releaseChecker = checker;
    }

    /**
     * Enregistre les boundaries pour les dragController
     * @param dragBoundary
     * @param releaseBoundary
     */
    public void setBoundaries(Bounds dragBoundary, Bounds releaseBoundary){
        this.dragBoundary = dragBoundary;
        this.releaseBoundary = releaseBoundary;
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
     * @see MeubleModele#isClickedMove
     */
    private void grabFormeByFiche(){
        Point2D offset = new Point2D(this.LARGEUR/2, this.HAUTEUR/2);
        getDragController().setMouseOffsetFromNode(offset);

        Point2D posInParent = getDragController().getCurrentPos(getMeuble());
        double anchorInParentX = posInParent.getX() + offset.getX();
        double anchorInParentY = posInParent.getY() + offset.getY();
        Parent parent = getMeuble().getParent();
        Point2D anchorInScene = parent.localToScene(anchorInParentX, anchorInParentY);
        getDragController().setAnchors(anchorInScene);
    }

    /**
     * Renvoie le boolean qui defini si un move by clic va commencer du meuble selectionne.
     * <li>
     *     <ul>Si set a true, declanche automatiquement le debut du mouvement</ul>
     *     <ul>Si set a false, arrete le mouvement en cours</ul>
     * </li>
     * @return le boolean property
     * @see MeubleModele#isClickedMove
     */
    public BooleanProperty getIsClickedMoveProperty(){
        return getMeuble().getIsClickedMoveProperty();
    }

    /*--------------------------Forme-----------------------------*/

    /**
     * Cree la forme de la cuisine en fonction de ses dimensions
     */
    private void buildForme(){
        this.meuble = new Rectangle(this.LARGEUR, this.HAUTEUR);
        this.meuble.setFill(paintUsual);
        this.meuble.setStroke(Color.BLACK);
        this.meuble.setStrokeWidth(2);
        this.meuble.visibleProperty().bindBidirectional(this.inPlan);
    }


    /**
     * Renvoie le meuble selectionne par le modele.
     * <br/>Si aucun meuble n est selectionne, en cree un nouveau.
     * Ce nouveau meuble est temporaire tant qu il n a pas ete ajoute au panier
     * @return la forme du meuble
     * @see MeubleModele#isEmptyMeubleSelection()
     */
    public Meuble getMeuble() {
        if(this.selectionMeuble == null){
            createMeuble();
        }
        return selectionMeuble;
    }

    /**
     * Deplace la forme du meuble en utilisant la methode relocate de Node
     * @param x la position x du meuble sur le plan
     * @param y la position y du meuble sur le plan
     * @see javafx.scene.Node#relocate(double, double)
     */
    public void relocate(double x, double y){
        getMeuble().getForme().relocate(x,y);
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
    public boolean intersect(MeubleModele m){
        return getMeuble().intersects(getMeuble().sceneToLocal(m.getMeuble().localToScene(
                m.getMeuble().getBoundsInLocal())));
    }

    /**
     * Defini la forme du meuble comme enfant du parent donne
     * @param parent le parent du meuble
     */
    public void setParent(Pane parent){
        parent.getChildren().add(getMeuble());
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

        //Big fiche/info fiche
        this.infoFiche = new BigFiche(this);
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
    public  BigFiche getBigFiche(){
        return this.infoFiche;
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
     * Defini la description du meuble
     * @param description la description du meuble. Sans retour a la ligne
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Renvoie la description du meuble
     * @return la description du meuble en tant que String
     */
    public String getDescription() {
        return description;
    }

    /**
     * La classe qu un meuble represente dans le plan
     */
    public class Meuble{

        /*---------------ID----------------*/

        private IntegerProperty idModele = new SimpleIntegerProperty();
        private IntegerProperty idMeuble = new SimpleIntegerProperty();
        private StringProperty id = new SimpleStringProperty();


        /*----------------Etats---------------------------*/

        /**Verifie si la forme du meuble est visible dans le plan**/
        private BooleanProperty inPlan = new SimpleBooleanProperty(false);
        /**Verifie si le meuble est selectionne par le modele**/
        private BooleanProperty selected = new SimpleBooleanProperty(false);
        /**Est ce que le meuble se trouve dans le panier. Ajoute ou enleve automatiquement le meuble de la liste du modele**/
        private BooleanProperty inPanier = new SimpleBooleanProperty(false);

        /**Verifie si un drag by clic mouvement est en cours**/
        private BooleanProperty isClickedMove = new SimpleBooleanProperty(false);

        private EventHandler<MouseEvent> dragByClic;
        private EventHandler<MouseEvent> releaseByClic;

        private Shape forme;

        private DragController dragController;

        /**The fill property of the forme while in normal state**/
        private Paint paintUsual = Color.AQUA;
        /**The fill property of the forme while in selected state**/
        private Paint paintSelected = Color.GREEN;
        /**The fill property of the forme while meubleModele is selected state**/
        private Paint paintAllSelected = Color.RED;

        /**
         * Cree un meuble unique correspondant au modele qui l a cree.
         * <br/>Le meuble lie lui meme tous les listeners a partir de ceux enregistres dans le modele
         */
        public Meuble(){
            MeubleModele.this.nbMeuble.set(MeubleModele.this.nbMeuble.get()+1);
            initId();
            buildForme();
            Data.panneaux.cuisine.add(this);
            reset();
            this.dragController = new DragController(this.getForme());
            initDragController();
            setHandlers();
        }

        /**
         * Initialise l id
         */
        private void initId(){
            this.idModele.set(MeubleModele.this.id);
            this.idMeuble.set(MeubleModele.this.nbTotalMeuble++);
            this.id.bind(Bindings.concat(idModele,".",idMeuble));
        }

        /**
         * Renvoie l id du meuble. Cet id est unique au meuble
         * @return l id du meuble sous forme de String
         * @see Meuble#id
         */
        public String getId() {
            return id.get();
        }

        /**
         * Cree la forme de la cuisine en fonction de ses dimensions
         */
        private void buildForme(){
            this.forme = new Rectangle(MeubleModele.this.LARGEUR, MeubleModele.this.HAUTEUR);
            this.forme.setFill(paintUsual);
            this.forme.setStroke(Color.BLACK);
            this.forme.setStrokeWidth(2);
            this.forme.visibleProperty().bindBidirectional(this.inPlan);
        }


        /**
         * Retire tous les liens avec d autres proprietes, y compris les parents
         */
        public void unbind(){
            this.inPanier.set(false);
            this.inPanier.unbind();
            this.selected.set(false);
            this.selected.unbind();
            this.isClickedMove.set(false);
            Data.panneaux.cuisine.remove(this);
            MeubleModele.this.nbMeuble.set(MeubleModele.this.nbMeuble.get()-1);
            getDragController().getDraggableProperty().unbind();
        }

        /**
         * Reset la position initiale sur le point a gauche-milieu du parent et retire du plan en le rendant invisible
         */
        public void reset(){
            getForme().setTranslateX(0);
            getForme().setTranslateY(0);
            getForme().relocate(0, getForme().getParent().getBoundsInLocal().getHeight()/2);
            this.inPlan.set(false);
        }

        /**
         * Verifie si les 2 meubles sont egaux par leur id
         * @param meuble
         * @return
         */
        public boolean equals(Meuble meuble){
            return this.id.isEqualTo(meuble.id).get();
        }


        /*--------------------------Listeners---------------------*/


        /**
         * Link les listeners a tous les elements necessaires.
         */
        private void setHandlers(){
            setEventHandlersDragAndReleaseByClic();
            getForme().setOnMousePressed(event -> {
                GestionaireMeubles.select(MeubleModele.this);
                MeubleModele.this.select(Meuble.this);
            });


            selected.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    getForme().setFill(paintSelected);
                } else {
                    if(MeubleModele.this.selected.get()){
                        getForme().setFill(paintAllSelected);
                    } else {
                        getForme().setFill(paintUsual);
                    }
                }
            });


            MeubleModele.this.selected.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if(! this.selected.get()){
                        getForme().setFill(paintAllSelected);
                    }
                } else {
                    getForme().setFill(paintUsual);
                }
            });

            inPanier.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    listMeuble.add(this);
                    nbMeuble.set(nbMeuble.get()+1);
                } else {
                    listMeuble.remove(this);
                    nbMeuble.set(nbMeuble.get()-1);
                }
            });
            // Cas clic to move

            isClickedMove.addListener((observable, oldValue, newValue) -> {
                if (newValue && ! oldValue) {
                    getDragController().getDraggableProperty().unbind();
                    getDragController().getDraggableProperty().set(false);
                    getForme().setTranslateX(0); //Reset au cas ou on clique plusieurs fois dessus
                    getForme().setTranslateY(0);
                    grabFormeByFiche();
                    getForme().getParent().addEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                    getForme().getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, releaseByClic);
                } else if(! newValue && oldValue) {
                    getForme().getParent().removeEventFilter(MouseEvent.MOUSE_MOVED, dragByClic);
                    getForme().getParent().removeEventFilter(MouseEvent.MOUSE_PRESSED, releaseByClic);
                    getForme().setTranslateX(0); //Reset au cas ou annule
                    getForme().setTranslateY(0);
                    getDragController().getDraggableProperty().bind(savedDragBind);
                }
            });

            inPlan.addListener((observable, oldValue, newValue) -> {
                Platform.runLater(()->{
                    getForme().getParent().requestFocus();
                    getForme().requestFocus();
                });
            });

        }

        /**
         * Defini les handlers pour le drag by clic, les enregistre dans la variable globale correspondante
         */
        private void setEventHandlersDragAndReleaseByClic(){
            this.dragByClic = event -> {
                getForme().requestFocus();
                if(getDragController().getDragChecker() != null){
                    if(getDragController().getDragChecker().check()){
                        Platform.runLater(() -> getDragController().dragHandler(event));

                    }
                } else {
                    Platform.runLater(() -> getDragController().dragHandler(event));
                    //this.dragController.dragHandler(event);
                }
            };

            this.releaseByClic = event -> {
                getForme().requestFocus();
                if(getDragController().getReleaseChecker() != null){
                    if(getDragController().getReleaseChecker().check()){
                        Platform.runLater(() -> {
                            if(getDragController().releaseHandler(event)){
                                isClickedMove.set(false);
                                addToPanier();
                            } else {
                                getForme().setTranslateX(0);
                                getForme().setTranslateY(0);
                                grabFormeByFiche();
                            }
                        });

                    } else {
                        Platform.runLater(() -> {
                            getForme().setTranslateX(0);
                            getForme().setTranslateY(0);
                            grabFormeByFiche();
                        });

                    }
                } else {
                    Platform.runLater(() -> {
                        if(getDragController().releaseHandler(event)){
                            isClickedMove.set(false);
                        }
                    });

                }
            };

        }

        /**
         * Defini l action de saisir la forme de la fiche lorsque le mouvement par clic est active.
         * <br/>Initialise le drag by clic
         * <br/>Defini les anchor et offset dans le dragController
         * @see MeubleModele#isClickedMove
         */
        private void grabFormeByFiche(){
            Point2D offset = new Point2D(LARGEUR/2, HAUTEUR/2); //Anchor au milieu de la forme
            getDragController().setMouseOffsetFromNode(offset);

            Point2D posInParent = getDragController().getCurrentPos(getForme());
            double anchorInParentX = posInParent.getX() + offset.getX();
            double anchorInParentY = posInParent.getY() + offset.getY();
            Parent parent = getForme().getParent();
            Point2D anchorInScene = parent.localToScene(anchorInParentX, anchorInParentY);
            getDragController().setAnchors(anchorInScene);
        }

        /*---------------------Getters---------------------------*/

        /**
         * Renvoie la forme du meuble telle qu elle apparait sur le plan
         * @return
         */
        public Shape getForme() {
            return forme;
        }

        /**
         * Ajoute les checkers et les boundaries au drag controller
         */
        private void initDragController(){
            getDragController().setBoundaries(dragBoundary, releaseBoundary);
            getDragController().setReleaseChecker(releaseChecker);
            getDragController().getDraggableProperty().bind(savedDragBind);
        }



        /**
         * Renvoie le drag controller associe a la forme
         * @return
         */
        public DragController getDragController() {
            return dragController;
        }


        /**
         * Verifie si le meuble est present sur le plan
         * @return en tant que BooleanProperty, donc modifiable
         */
        public BooleanProperty inPlanProperty() {
            return inPlan;
        }

        /**
         * Renvoie le boolean qui defini si un move by clic va commencer.
         * <li>
         *     <ul>Si set a true, declanche automatiquement le debut du mouvement</ul>
         *     <ul>Si set a false, arrete le mouvement en cours</ul>
         * </li>
         * @return le boolean property
         * @see MeubleModele#isClickedMove
         */
        public BooleanProperty getIsClickedMoveProperty(){
            return isClickedMove;
        }

        /**
         * Renvoie le modele du meuble
         * @return
         */
        public MeubleModele getModele(){
            return MeubleModele.this;
        }

        /**
         * Renvoie le parent de la forme du meuble
         * @return
         * @see Shape#getParent()
         */
        public Parent getParent(){
            return getForme().getParent();
        }

        /**
         * Appel requestFocus() de la forme du meuble
         * @see Shape#requestFocus()
         */
        public void requestFocus(){
            getForme().requestFocus();
        }
    }
}