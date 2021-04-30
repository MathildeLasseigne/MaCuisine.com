package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class InfoPaneController extends FXMLController {

    @FXML
    private StackPane infoPane;

    @FXML
    private Pane baseInfoText;

    public InfoPaneController(){

    }

    @FXML
    public void initialize(){

    }


    /**
     * Renvoie le container des info panes
     * @return
     */
    public StackPane getInfoPane() {
        return infoPane;
    }

    /**
     * Renvoie l info pane par defaut
     * @return
     */
    public Pane getBaseInfoText() {
        return baseInfoText;
    }
}
