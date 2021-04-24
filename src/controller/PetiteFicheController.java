package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import modele.Meuble;
import modele.PetiteFiche;

import javax.swing.text.html.ImageView;

public class PetiteFicheController extends Controller {

    private Meuble meuble;
    private PetiteFiche petiteFiche;

    @FXML
    private VBox ficheContainer;

    @FXML
    private ImageView image;

    public PetiteFicheController(PetiteFiche petiteFiche){
        this.petiteFiche = petiteFiche;
        this.meuble = this.petiteFiche.getMeuble();
    }


    @FXML
    public void initialize(){ //Les link des variables vers fxml se font apres le constructeur & avant initialize
        //TODO link les listeners ? Si besoin
    }

}
