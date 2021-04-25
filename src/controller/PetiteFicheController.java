package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modele.GestionaireMeubles;
import modele.Meuble;
import modele.PetiteFiche;



public class PetiteFicheController extends Controller {

    private Meuble meuble;
    private PetiteFiche petiteFiche;

    @FXML
    private VBox ficheContainer;

    @FXML
    private ImageView image;

    @FXML
    private Label nameLabel;

    @FXML
    private Label marqueLabel;

    @FXML
    private Label dimensionsText;

    @FXML
    private Label prixText;

    public PetiteFicheController(PetiteFiche petiteFiche){
        this.petiteFiche = petiteFiche;
        this.meuble = this.petiteFiche.getMeuble();
        setHandlers();
    }


    private void setHandlers(){
        this.petiteFiche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GestionaireMeubles.selection.getIsClickedMoveProperty().set(false);
                GestionaireMeubles.select(meuble);
                meuble.getIsClickedMoveProperty().set(true);
            }
        });
    }

    @FXML
    public void initialize(){ //Les link des variables vers fxml se font apres le constructeur & avant initialize
        //TODO link les listeners ? Si besoin
        Image img = meuble.getImg();
        if(img != null){
            image.setPreserveRatio(true);
            image.setImage(img);
        }
        setDescription();
    }

    /**
     * Insere la description du meuble dans sa fiche
     */
    private void setDescription(){
        this.nameLabel.setText(meuble.getNom());
        this.marqueLabel.setText(meuble.getConstructeur());
        this.dimensionsText.setText(meuble.getDimensions());
        this.prixText.setText(meuble.getPrix());
    }

}
