package vue;

import controller.InfoPaneController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import modele.Data;
import modele.MeubleModele;

import java.io.IOException;
import java.io.Serializable;

public class InfoPane implements Serializable {

    private Pane container;

    private StackPane infoPane;

    private Pane defaultPane;

    private InfoPaneController ctrl;

    /**
     * Defini le panneau d informations sous la forme d un stackPane.
     * @see StackPane
     */
    public InfoPane(){

        this.ctrl = new InfoPaneController();
        Parent p = null;
        try {
            p = this.ctrl.loadFXMLWithController(getClass().getResource("InfoPane.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.container = (Pane) p;



        this.infoPane = this.ctrl.getInfoPane();
        this.defaultPane = this.ctrl.getBaseInfoText();
        //Data.getCurrentSession().panneaux.infoPane = this;
    }

    /**
     * Ajoute la fiche d info du meuble au panneau d infos
     * @param meubleModele le meuble a ajouter
     */
    public void initCommit(MeubleModele meubleModele){
        this.infoPane.getChildren().add(meubleModele.getBigFiche());
        unselect();
    }

    /**
     * Affiche le panneau par defaut
     */
    public void unselect(){
        this.defaultPane.toFront();
    }

    /**
     * Affiche le panneau d informations du meuble
     * @param meuble
     */
    public void select(MeubleModele meuble){
        meuble.getBigFiche().toFront();
    }

    /**
     * Renvoie la pane complete de l infoPane.
     * @return
     */
    public Pane getInfoPane(){
        return this.container;
    }

}
