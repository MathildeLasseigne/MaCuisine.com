package Tests.Total;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import modele.Data;
import modele.GestionaireMeubles;
import vue.Catalogue;
import vue.Cuisine;
import vue.Panier;

public class AppliTestController extends Controller {


    private Cuisine cuisine;


    @FXML
    private VBox panier;

    @FXML
    private VBox catalogue;

    @FXML
    private StackPane infoPane;

    @FXML
    private TextFlow baseInfoText;

    @FXML
    private Pane cuisinePlan;

    public AppliTestController(Cuisine cuisine){
        this.cuisine = cuisine;
    }

    @FXML
    public void initialize(){
        cuisinePlan.getChildren().add(cuisine);

        Data.panneaux.panier = new Panier(panier);
        Data.panneaux.catalogue = new Catalogue(catalogue);
    }

    @FXML
    public void cancelSelectionHandler(){
        GestionaireMeubles.unselect();
    }

}
