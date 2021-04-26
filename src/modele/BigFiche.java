package modele;

import controller.BigFicheController;
import controller.PetiteFicheController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BigFiche extends Pane {

    private Meuble meuble;

    private BigFicheController controller;

    public BigFiche(Meuble meuble){
        this.meuble = meuble;
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
    public Meuble getMeuble() {
        return meuble;
    }


}
