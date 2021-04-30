package vue;

import javafx.scene.layout.VBox;
import modele.Data;
import modele.MeubleModele;

import java.util.HashMap;

public class Catalogue {

    private VBox container;
    private HashMap<MeubleModele.Type, VBox> listTypes;

    //TODO : Remplacer par Hashmap
    public Catalogue(HashMap<MeubleModele.Type, VBox> listTypes){
        //this.container = container;
        this.listTypes = listTypes;
        Data.panneaux.catalogue = this;
    }


    /**
     * Ajout initial
     * @param meubleModele le meuble a ajouter
     */
    public void initCommit(MeubleModele meubleModele){
        listTypes.get(meubleModele.getType()).getChildren().add(meubleModele.getLittleFicheCatalogue());
        meubleModele.getLittleFicheCatalogue().setVisible(true);
        //container.getChildren().add(meubleModele.getLittleFicheCatalogue());
    }

}
