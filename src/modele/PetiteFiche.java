package modele;

import controller.PetiteFicheController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PetiteFiche extends Pane {

    private Meuble meuble;

    private PetiteFicheController controller;

    public PetiteFiche(Meuble meuble) {
        this.meuble = meuble;
        this.controller = new PetiteFicheController(this);
        //TODO load le fxml et add to children
        Parent ficheContent = null;
        try{
            //ficheContent = this.controller.loadFXMLWithController("PetiteFicheMeuble.fxml");//Changer nom
            ficheContent = this.controller.loadFXMLWithController(getClass().getResource("PetiteFicheMeuble.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }
        if(ficheContent != null){
            this.getChildren().add(ficheContent);
        }
        this.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
    }




    /**
     * Renvoie le meuble lie a la fiche
     * @return
     */
    public Meuble getMeuble() {
        return meuble;
    }

    //TODO les listeners
}
