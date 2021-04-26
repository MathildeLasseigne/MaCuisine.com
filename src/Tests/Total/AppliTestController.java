package Tests.Total;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import modele.Data;
import modele.GestionaireMeubles;
import vue.Catalogue;
import vue.Cuisine;
import vue.InfoPane;
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
    private Pane baseInfoText;

    @FXML
    private AnchorPane cuisinePlan;

    @FXML
    private BorderPane container;

    @FXML
    private CheckBox grilleBox;

    @FXML
    public CheckBox moveBox;

    public AppliTestController(Cuisine cuisine){
        this.cuisine = cuisine;
    }

    @FXML
    public void initialize(){
        container.setCenter(cuisine);

        Data.panneaux.panier = new Panier(panier);
        Data.panneaux.catalogue = new Catalogue(catalogue);
        Data.panneaux.infoPane = new InfoPane(infoPane, baseInfoText);

        Data.properties.isGrilleVisible.bind(this.grilleBox.selectedProperty());
        Data.properties.isMeubleMovable.bind(this.moveBox.selectedProperty());
    }

    @FXML
    public void cancelSelectionHandler(){
        GestionaireMeubles.unselect();
    }

}
