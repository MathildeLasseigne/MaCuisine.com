package vue;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import modele.Meuble;

public class InfoPane {

    private StackPane infoPane;

    private Pane defaultPane;

    /**
     * Defini le panneau d informations sous la forme d un stackPane.
     * @param infoPane le contener du panneau
     * @param initPane le premier panneau enregistre, celui par default
     * @see StackPane
     */
    public InfoPane(StackPane infoPane, Pane initPane){
        this.infoPane = infoPane;
        this.defaultPane = initPane;
    }

    /**
     * Ajoute la fiche d info du meuble au panneau d infos
     * @param meuble
     */
    public void initCommit(Meuble meuble){
        this.infoPane.getChildren().add(meuble.getBigFiche());
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
    public void select(Meuble meuble){
        meuble.getBigFiche().toFront();
    }

}
