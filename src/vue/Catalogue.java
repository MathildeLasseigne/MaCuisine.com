package vue;

import javafx.scene.layout.VBox;
import modele.Data;
import modele.MeubleModele;

public class Catalogue {

    private VBox container;

    public Catalogue(VBox container){
        this.container = container;
        Data.panneaux.catalogue = this;
    }


    /**
     * Ajout initial
     * @param meubleModele le meuble a ajouter
     */
    public void initCommit(MeubleModele meubleModele){
        meubleModele.getLittleFicheCatalogue().setVisible(true);
        container.getChildren().add(meubleModele.getLittleFicheCatalogue());
    }

}
