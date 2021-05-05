package Tests.Total;

import controller.FXMLController;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import modele.Data;
import modele.MeubleModele;
import vue.Cuisine;

import java.util.HashMap;


public class AppliTestController extends FXMLController {


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
    @FXML
    private VBox panierTablesVbox;
    @FXML
    private VBox panierChaisesVbox;
    @FXML
    private VBox panierGElectroVbox;
    @FXML
    private VBox panierPElectroVbox;
    @FXML
    private VBox panierPlomberieVbox;


    /**Vbox pour chaque type de meuble du catalogue**/

    @FXML
    private VBox catalogueMeublesVbox;
    @FXML
    private VBox catalogueTablesVbox;
    @FXML
    private VBox catalogueChaisesVbox;
    @FXML
    private VBox catalogueGElectroVbox;
    @FXML
    private VBox cataloguePElectroVbox;
    @FXML
    private VBox cataloguePlomberieVbox;

    @FXML
    private ImageView moveImageView;

    @FXML
    private Button suppr;

    @FXML
    private Button rotateRight;

    @FXML
    private Button rotateLeft;

    @FXML
    private ImageView grilleImageView;

    @FXML
    private MenuItem sauvegarder;

    @FXML
    private MenuItem imprimer;


    /*-----------------Pas FXML-----------------------*/

    /**Hashmap des types de meubles et de leur VBox associé**/
    private HashMap<MeubleModele.Type, VBox> panierVBox = new HashMap<>();
    private HashMap<MeubleModele.Type, VBox> catalogueVBox = new HashMap<>();

    /**Les images du button move**/
    private Image moveImage, dontMoveImage;
    /**Les images du bouton grille**/
    private Image grilleImage, grilleCrossedImage;

    private Stage primarystage;

    public AppliTestController(Stage primaryStage){
        this.primarystage = primaryStage;
        //this.cuisine = cuisine;
        this.moveImage = new Image(getClass().getResourceAsStream("../../Sprites/move.png"));
        this.dontMoveImage = new Image(getClass().getResourceAsStream("../../Sprites/dont-move.png"));
        this.grilleImage = new Image(getClass().getResourceAsStream("../../Sprites/hashtag.png"));
        this.grilleCrossedImage = new Image(getClass().getResourceAsStream("../../Sprites/la-grille.png"));
    }

    @FXML
    public void initialize(){
       //loadSession();
        sauvegarder.setOnAction((actionEvent)->{Data.saveSession(primarystage);});
        imprimer.setOnAction((actionEvent)->{printCuisine();});
        //rotateRight.setOnAction((actionEvent)->{rotateRightHandler();});
        //rotateLeft.setOnAction((actionEvent)->{rotateLeftHandler();});
    }

    /**
     * Load the current session into the application
     */
    public void loadSession(){
        container.setCenter(Data.getCurrentSession().panneaux.cuisine);
        container.setRight(Data.getCurrentSession().panneaux.infoPane.getInfoPane());
        container.setLeft(Data.getCurrentSession().panneaux.leftPanel.getLeftPannel());
        Data.getCurrentSession().properties.isMeubleMovable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.moveImageView.setImage(this.moveImage);
            } else {
                this.moveImageView.setImage(this.dontMoveImage);
            }
        });

        Data.getCurrentSession().properties.isGrilleVisible.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.grilleImageView.setImage(this.grilleImage);
            } else {
                this.grilleImageView.setImage(this.grilleCrossedImage);
            }
        });
    }

    @FXML
    public void cancelSelectionHandler(){
        Data.getCurrentSession().gestionaireMeubles.unselect();
    }

    @FXML
    public void supprHandler(){
        MeubleModele selection = Data.getCurrentSession().gestionaireMeubles.getSelection();
        if(selection != null){
            selection.removeFromPanier();
        }
    }

    @FXML
    public void moveHandler(){
        Data.getCurrentSession().properties.isMeubleMovable.set(! Data.getCurrentSession().properties.isMeubleMovable.get());
    }

    @FXML
    public void grilleHandler(){
        Data.getCurrentSession().properties.isGrilleVisible.set(! Data.getCurrentSession().properties.isGrilleVisible.get());
    }

    @FXML
    public void rotateRightHandler(){
        if(Data.getCurrentSession().properties.isMeubleMovable.get()) {
            MeubleModele selection = Data.getCurrentSession().gestionaireMeubles.getSelection();
            Rotate rotate = new Rotate();
            rotate.setPivotX(selection.getMeuble().getForme().getBoundsInLocal().getCenterX());
            rotate.setPivotY(selection.getMeuble().getForme().getBoundsInLocal().getCenterX());
            rotate.setAngle(Data.rotationPas);
            if (selection != null) {
                if (!selection.isEmptyMeubleSelection()) {
                    selection.getMeuble().getForme().getTransforms().add(rotate);
                }
            }
        }
    }

    @FXML
    public void rotateLeftHandler(){
        if(Data.getCurrentSession().properties.isMeubleMovable.get()) {
            MeubleModele selection = Data.getCurrentSession().gestionaireMeubles.getSelection();
            Rotate rotate = new Rotate();
            rotate.setPivotX(selection.getMeuble().getForme().getBoundsInLocal().getCenterX());
            rotate.setPivotY(selection.getMeuble().getForme().getBoundsInLocal().getCenterX());
            rotate.setAngle(-Data.rotationPas);
            if (selection != null) {
                if (!selection.isEmptyMeubleSelection()) {
                    selection.getMeuble().getForme().getTransforms().add(rotate);
                }
            }
        }
    }


    @FXML
    public void printCuisine(){
        PrinterJob job = PrinterJob.createPrinterJob();
        Cuisine root = (Cuisine) container.getCenter();

        if(job != null && job.showPrintDialog(root.getScene().getWindow())){
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);

            //root.getTop().setVisible(false);
            //root.getTop().setManaged(false);

            double width = root.getWidth();
            double height = root.getHeight();

            PrintResolution resolution = job.getJobSettings().getPrintResolution();

            width /= resolution.getFeedResolution();

            height /= resolution.getCrossFeedResolution();

            double scaleX = pageLayout.getPrintableWidth()/width/600;
            double scaleY = pageLayout.getPrintableHeight()/height/600;

            Scale scale = new Scale(scaleX, scaleY);

            root.getTransforms().add(scale);

            boolean success = job.printPage(pageLayout, root);
            if(success){
                job.endJob();
            }
            root.getTransforms().remove(scale);
        }
        //root.getTop().setManaged(true);
        //root.getTop().setVisible(true);
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
