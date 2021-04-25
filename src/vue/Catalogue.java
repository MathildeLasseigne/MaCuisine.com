package vue;

import javafx.scene.Group;
import modele.Meuble;

public class Catalogue {

    private Group container;

    public Catalogue(Group container){
        this.container = container;
    }


    /**
     * Ajout initial
     * @param m
     */
    public void initCommit(Meuble m){
        m.getLittleFichePanier().setVisible(true);
        container.getChildren().add(m.getLittleFichePanier());
    }

}
