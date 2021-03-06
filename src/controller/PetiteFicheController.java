package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import modele.Data;
import modele.GestionaireMeubles;
import modele.MeubleModele;
import modele.PetiteFiche;



public class PetiteFicheController extends FXMLController {

    private MeubleModele meubleModele;
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

    @FXML
    private Label nbMeubleMultiplicateur;

    @FXML
    private Label nbMeuble;

    public PetiteFicheController(PetiteFiche petiteFiche){
        this.petiteFiche = petiteFiche;
        this.meubleModele = this.petiteFiche.getMeubleModele();
        setHandlers();
    }


    private void setHandlers(){
        this.petiteFiche.setOnMouseClicked(event -> {
            MeubleModele oldSelection = Data.getCurrentSession().gestionaireMeubles.getSelection();
            if(oldSelection != null){
                if(! oldSelection.isEmptyMeubleSelection()){
                    oldSelection.getIsClickedMoveProperty().set(false);
                }
            }
            if(this.petiteFiche.getOrigine() == Data.Origine.Catalogue){
                meubleModele.createMeuble();
                meubleModele.getIsClickedMoveProperty().set(true);
            }
            Data.getCurrentSession().panneaux.cuisine.requestFocus();
            meubleModele.getMeuble().getParent().requestFocus();
            meubleModele.getMeuble().requestFocus();
            Data.getCurrentSession().gestionaireMeubles.select(meubleModele);
        });

        //Change la bordure de la fiche si le meuble est selectionne
        this.petiteFiche.getMeubleModele().isSelectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.petiteFiche.setContentStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            } else {
                this.petiteFiche.setContentStyle("-fx-border-color: black ; -fx-border-width: 2px ;");
            }
        });
    }

    @FXML
    public void initialize(){ //Les link des variables vers fxml se font apres le constructeur & avant initialize
        //TODO link les listeners ? Si besoin
        Image img = meubleModele.getImg();
        if(img != null){
            image.setPreserveRatio(true);
            image.setImage(img);
        }
        setDescription();

        if(this.petiteFiche.getOrigine() == Data.Origine.Panier){
            this.nbMeubleMultiplicateur.setText("x");
            this.nbMeuble.textProperty().bind(meubleModele.nbMeuble.asString());
        }
    }

    /**
     * Insere la description du meuble dans sa fiche
     */
    private void setDescription(){
        this.nameLabel.setText(meubleModele.getNom());
        this.marqueLabel.setText(meubleModele.getConstructeur());
        this.dimensionsText.setText(meubleModele.getDimensions());
        this.prixText.setText(meubleModele.getPrix());
    }

}
