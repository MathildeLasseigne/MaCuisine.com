package modele;

import controller.BigFicheController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BigFiche extends Pane {

    private MeubleModele meubleModele;

    private BigFicheController controller;

    public BigFiche(MeubleModele meubleModele){
        this.meubleModele = meubleModele;
        this.controller = new BigFicheController(this);
        Parent ficheContent = null;
        try{
            ficheContent = this.controller.loadFXMLWithController(getClass().getResource("../vue/BigFiche.fxml"));

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
