package Tests.Total;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import modele.Data;
import modele.GestionaireMeubles;
import modele.Meuble;
import vue.Catalogue;
import vue.Cuisine;
import vue.InfoPane;
import vue.Panier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


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
    private ImageView moveImageView;

    @FXML
    private Button suppr;

    @FXML
    private ImageView grilleImageView;


    /*-----------------Pas FXML-----------------------*/


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

        Data.panneaux.panier = new Panier(panier);
        Data.panneaux.catalogue = new Catalogue(catalogue);
        Data.panneaux.infoPane = new InfoPane(infoPane, baseInfoText);

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
        Meuble selection = GestionaireMeubles.getSelection();
        if(selection != null){
            selection.isInPanier().set(false);
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

}
