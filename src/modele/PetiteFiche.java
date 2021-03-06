package modele;

import controller.PetiteFicheController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;

public class PetiteFiche extends Pane implements Serializable {

    private MeubleModele meubleModele;

    private PetiteFicheController controller;

    private Data.Origine origine = null;

    Parent content;
    String originalContentStyle;

    public PetiteFiche(MeubleModele meubleModele, Data.Origine origine) {
        this.origine = origine;
        this.meubleModele = meubleModele;
        this.controller = new PetiteFicheController(this);
        //TODO load le fxml et add to children
        Parent ficheContent = null;
        try{
            //ficheContent = this.controller.loadFXMLWithController("PetiteFicheMeuble.fxml");//Changer nom
            ficheContent = this.controller.loadFXMLWithController(getClass().getResource("../vue/PetiteFicheMeuble.fxml"));

        } catch (IOException e){
            e.printStackTrace();
        }
        if(ficheContent != null){
            this.getChildren().add(ficheContent);
        }
        //this.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
        this.content = ficheContent;
        originalContentStyle = content.getStyle();
        content.setStyle("-fx-border-color: black ; -fx-border-width: 2px ;"+originalContentStyle);
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
    }


    /**
     * Change le style de la fiche tout en gardant son style original
     * @param style
     */
    public void setContentStyle(String style){
        content.setStyle(style + originalContentStyle);
    }

    /**
     * Renvoie le meuble lie a la fiche
     * @return
     */
    public MeubleModele getMeubleModele() {
        return meubleModele;
    }


    /**
     * Renvoie l origine de la fiche (Panier, Catalogue, Plan)
     * @return
     */
    public Data.Origine getOrigine() {
        return origine;
    }
}
