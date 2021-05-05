package modele;

import controller.GrandeFicheController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;

public class GrandeFiche extends Pane implements Serializable {

    private MeubleModele meubleModele;

    private GrandeFicheController controller;

    public GrandeFiche(MeubleModele meubleModele){
        this.meubleModele = meubleModele;
        this.controller = new GrandeFicheController(this);
        Parent ficheContent = null;
        try{
            ficheContent = this.controller.loadFXMLWithController(getClass().getResource("../vue/GrandeFicheMeuble.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }
        if(ficheContent != null){
            this.getChildren().add(ficheContent);
        }
    }


    /**
     * Renvoie le meuble associé à la fiche
     * @return
     */
    public MeubleModele getMeubleModele() {
        return meubleModele;
    }


}
