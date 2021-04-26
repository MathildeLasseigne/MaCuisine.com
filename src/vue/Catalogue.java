package vue;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import modele.Meuble;

public class Catalogue {

    private VBox container;

    public Catalogue(VBox container){
        this.container = container;
    }


    /**
     * Ajout initial
     * @param m
     */
    public void initCommit(Meuble m){
        m.getLittleFicheCatalogue().setVisible(true);
        container.getChildren().add(m.getLittleFicheCatalogue());
    }

}
