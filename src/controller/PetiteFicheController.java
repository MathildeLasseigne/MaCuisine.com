package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modele.Data;
import modele.GestionaireMeubles;
import modele.MeubleModele;
import modele.PetiteFiche;



public class PetiteFicheController extends Controller {

    private MeubleModele meuble;
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
        this.meuble = this.petiteFiche.getMeubleModele();
        setHandlers();
    }


    private void setHandlers(){
        this.petiteFiche.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MeubleModele oldSelection = GestionaireMeubles.getSelection();
                if(oldSelection != null){
                    oldSelection.getIsClickedMoveProperty().set(false);
                }
                meuble.getIsClickedMoveProperty().set(true);
                Data.panneaux.cuisine.requestFocus();
                meuble.getForme().getParent().requestFocus();
                meuble.getForme().requestFocus();
                GestionaireMeubles.select(meuble);
            }
        });

        //Change la bordure de la fiche si le meuble est selectionne
        this.petiteFiche.getMeubleModele().isSelectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.petiteFiche.setContentStyle("-fx-border-color: red ; -fx-border-width: 3px ;");
            } else {
                this.petiteFiche.setContentStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
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
