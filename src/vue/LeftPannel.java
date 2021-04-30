package vue;

import controller.InfoPaneController;
import controller.LeftPannelController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LeftPannel {

    private Pane leftPannel;

    private LeftPannelController ctrl;

    private Catalogue catatalogue;

    private Panier panier;


    public LeftPannel(){
        this.ctrl = new LeftPannelController();
        Parent p = null;
        try {
            p = this.ctrl.loadFXMLWithController(getClass().getResource("LeftPanel.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.leftPannel = (Pane) p;


        this.panier = new Panier(this.ctrl.getPanierVBox());
        this.catatalogue = new Catalogue(this.ctrl.getCatalogueVBox());

    }


    /**
     * Renvoie le panneau de gauche
     * @return
     */
    public Pane getLeftPannel() {
        return leftPannel;
    }

    /**
     * Renvoie le panier du panneau de gauche
     * @return
     */
    public Panier getPanier() {
        return panier;
    }

    /**
     * Renvoie le catalogue du panneau de gauche
     * @return
     */
    public Catalogue getCatatalogue() {
        return catatalogue;
    }
}
