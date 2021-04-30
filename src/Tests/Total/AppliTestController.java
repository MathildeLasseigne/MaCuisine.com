package Tests.Total;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import modele.Data;
import modele.GestionaireMeubles;
import modele.MeubleModele;
import vue.Catalogue;
import vue.Cuisine;
import vue.InfoPane;
import vue.Panier;

import java.util.HashMap;


public class AppliTestController extends Controller {


    private Cuisine cuisine;


    @FXML
    private VBox panier;

    @FXML
    private VBox catalogue;

    @FXML
    private StackPane infoPane;

    @FXML
    private Pane baseInfoText;

    @FXML
    private AnchorPane cuisinePlan;

    @FXML
    private BorderPane container;

    @FXML
    private AnchorPane anchorPanier;

    @FXML
    private AnchorPane anchorCatalogue;

    /**Vbox pour chaque type de meuble du panier**/

    @FXML
    private VBox panierMeublesVbox;
    private VBox panierTablesVbox;
    private VBox panierChaisesVbox;
    private VBox panierGElectroVbox;
    private VBox panierPElectroVbox;
    private VBox panierPlomberieVbox;


    /**Vbox pour chaque type de meuble du catalogue**/

    @FXML
    private VBox catalogueMeublesVbox;
    private VBox catalogueTablesVbox;
    private VBox catalogueChaisesVbox;
    private VBox catalogueGElectroVbox;
    private VBox cataloguePElectroVbox;
    private VBox cataloguePlomberieVbox;

    @FXML
    private ImageView moveImageView;

    @FXML
    private Button suppr;

    @FXML
    private ImageView grilleImageView;


    /*-----------------Pas FXML-----------------------*/

    /**Hashmap des types de meubles et de leur VBox associé**/
    private HashMap<MeubleModele.Type, VBox> panierVBox = new HashMap<>();
    private HashMap<MeubleModele.Type, VBox> catalogueVBox = new HashMap<>();

    /**Les images du button move**/
    private Image moveImage, dontMoveImage;
    /**Les images du bouton grille**/
    private Image grilleImage, grilleCrossedImage;

    public AppliTestController(Cuisine cuisine){
        this.cuisine = cuisine;
        this.moveImage = new Image(getClass().getResourceAsStream("../../Sprites/move.png"));
        this.dontMoveImage = new Image(getClass().getResourceAsStream("../../Sprites/dont-move.png"));
        this.grilleImage = new Image(getClass().getResourceAsStream("../../Sprites/hashtag.png"));
        this.grilleCrossedImage = new Image(getClass().getResourceAsStream("../../Sprites/la-grille.png"));
    }

    @FXML
    public void initialize(){
        container.setCenter(cuisine);

        new Panier(panierVBox);
        new Catalogue(catalogueVBox);
        new InfoPane(infoPane, baseInfoText);
        //new AnchorPane(anchorCatalogue, anchorPanier);

        Data.properties.isMeubleMovable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.moveImageView.setImage(this.moveImage);
            } else {
                this.moveImageView.setImage(this.dontMoveImage);
            }
        });

        Data.properties.isGrilleVisible.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.grilleImageView.setImage(this.grilleImage);
            } else {
                this.grilleImageView.setImage(this.grilleCrossedImage);
            }
        });
    }

    @FXML
    public void cancelSelectionHandler(){
        GestionaireMeubles.unselect();
    }

    @FXML
    public void supprHandler(){
        MeubleModele selection = GestionaireMeubles.getSelection();
        if(selection != null){
            selection.isInPanier().set(false);
            selection.isInPlanProperty().set(false);
        }
    }

    @FXML
    public void moveHandler(){
        Data.properties.isMeubleMovable.set(! Data.properties.isMeubleMovable.get());
    }

    @FXML
    public void grilleHandler(){
        Data.properties.isGrilleVisible.set(! Data.properties.isGrilleVisible.get());
    }

    /**
     * Link les events globaux.
     * @param root la scene qui fire les events
     */
    public void setGlobalEventHandler(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.DELETE) {
                suppr.fire();
                ev.consume();
            }
        });
    }

    /**
     * Relie les meubles à leur VBox associé dans les deux hashmap de catalogue et panier
     */
    public void linkMeublesVbox() {
        /**Pour le panier**/
        this.panierVBox.put(MeubleModele.Type.Meubles, panierMeublesVbox);
        this.panierVBox.put(MeubleModele.Type.Tables, panierTablesVbox);
        this.panierVBox.put(MeubleModele.Type.Chaises, panierChaisesVbox);
        this.panierVBox.put(MeubleModele.Type.Petits_electromenagers, panierPElectroVbox);
        this.panierVBox.put(MeubleModele.Type.Gros_electromenagers, panierGElectroVbox);
        this.panierVBox.put(MeubleModele.Type.Plomberie, panierPlomberieVbox);
        /**Récupérer pour le panier**/
        panierVBox.get(MeubleModele.Type.Meubles);
        panierVBox.get(MeubleModele.Type.Tables);
        panierVBox.get(MeubleModele.Type.Chaises);
        panierVBox.get(MeubleModele.Type.Petits_electromenagers);
        panierVBox.get(MeubleModele.Type.Gros_electromenagers);
        panierVBox.get(MeubleModele.Type.Plomberie);

        /**Pour le catalogue**/
        this.catalogueVBox.put(MeubleModele.Type.Meubles, catalogueMeublesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Tables, catalogueTablesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Chaises, catalogueChaisesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Petits_electromenagers, cataloguePElectroVbox);
        this.catalogueVBox.put(MeubleModele.Type.Gros_electromenagers, catalogueGElectroVbox);
        this.catalogueVBox.put(MeubleModele.Type.Plomberie, cataloguePlomberieVbox);
        /**Récupérer pour le catalogue**/
        catalogueVBox.get(MeubleModele.Type.Meubles);
        catalogueVBox.get(MeubleModele.Type.Tables);
        catalogueVBox.get(MeubleModele.Type.Chaises);
        catalogueVBox.get(MeubleModele.Type.Petits_electromenagers);
        catalogueVBox.get(MeubleModele.Type.Gros_electromenagers);
        catalogueVBox.get(MeubleModele.Type.Plomberie);
    }

}
