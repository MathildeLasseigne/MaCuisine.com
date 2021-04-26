package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import modele.BigFiche;
import modele.Data;
import modele.MeubleModele;


public class BigFicheController extends Controller {

    @FXML
    private ImageView image;

    @FXML
    private Label nom;

    @FXML
    private Label marque;

    @FXML
    private Label dimensions;

    @FXML
    private Label prix;

    @FXML
    private Text description;

    private BigFiche bigFiche;

    private MeubleModele meuble;

    public BigFicheController(BigFiche bigFiche){
        this.bigFiche = bigFiche;
        this.meuble = bigFiche.getMeubleModele();
        setHandler();
    }

    @FXML
    public void initialize(){
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
        this.nom.setText(meuble.getNom());
        this.marque.setText(meuble.getConstructeur());
        this.dimensions.setText(meuble.getDimensions());
        this.prix.setText(meuble.getPrix());
        this.description.setText(meuble.getDescription());
    }

    /**
     * Defini les listeners
     */
    private void setHandler(){
        this.meuble.isSelectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Data.panneaux.infoPane.select(meuble);
            } else {
                Data.panneaux.infoPane.unselect();
            }
        });
    }

}
