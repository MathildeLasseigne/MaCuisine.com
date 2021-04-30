package controller;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modele.GestionaireMeubles;
import modele.MeubleModele;

import java.util.HashMap;

public class LeftPannelController extends FXMLController {


    /**Vbox pour chaque type de meuble du panier**/

    @FXML
    private VBox panierMeublesVbox;
    @FXML
    private VBox panierTablesVbox;
    @FXML
    private VBox panierChaisesVbox;
    @FXML
    private VBox panierGElectroVbox;
    @FXML
    private VBox panierPElectroVbox;
    @FXML
    private VBox panierPlomberieVbox;


    /**Vbox pour chaque type de meuble du catalogue**/

    @FXML
    private VBox catalogueMeublesVbox;
    @FXML
    private VBox catalogueTablesVbox;
    @FXML
    private VBox catalogueChaisesVbox;
    @FXML
    private VBox catalogueGElectroVbox;
    @FXML
    private VBox cataloguePElectroVbox;
    @FXML
    private VBox cataloguePlomberieVbox;

    @FXML
    private Text totalprice;

    /*-----------------Pas FXML-----------------------*/

    /**Hashmap des types de meubles et de leur VBox associé**/
    private HashMap<MeubleModele.Type, VBox> panierVBox = new HashMap<>();
    private HashMap<MeubleModele.Type, VBox> catalogueVBox = new HashMap<>();


    public LeftPannelController(){

    }

    @FXML
    public void initialize(){
        linkMeublesVbox();
    }


    @FXML
    public void cancelSelectionHandler(){
        GestionaireMeubles.unselect();
    }

    /**
     * Renvoie la liste des conteners pour le Catalogue
     * @return
     */
    public HashMap<MeubleModele.Type, VBox> getCatalogueVBox() {
        return catalogueVBox;
    }

    /**
     * Renvoie la liste des conteners pour le Panier
     * @return
     */
    public HashMap<MeubleModele.Type, VBox> getPanierVBox() {
        return panierVBox;
    }

    /**
     * Permet l update de l affichage du prix total du panier
     * @param totalPrice
     */
    public void bindTotalPrice(DoubleProperty totalPrice){
        this.totalprice.textProperty().bind(totalPrice.asString());
    }

    /**
     * Relie les meubles à leur VBox associé dans les deux hashmap de catalogue et panier
     */
    public void linkMeublesVbox() {
        /**Pour le panier**/
        this.panierVBox.put(MeubleModele.Type.Meubles, panierMeublesVbox);
        this.panierVBox.put(MeubleModele.Type.Tables, panierTablesVbox);
        this.panierVBox.put(MeubleModele.Type.Chaises, panierChaisesVbox);
        this.panierVBox.put(MeubleModele.Type.Petits_electromenagers, panierPElectroVbox);
        this.panierVBox.put(MeubleModele.Type.Gros_electromenagers, panierGElectroVbox);
        this.panierVBox.put(MeubleModele.Type.Plomberie, panierPlomberieVbox);
        /**Récupérer pour le panier**/
        panierVBox.get(MeubleModele.Type.Meubles);
        panierVBox.get(MeubleModele.Type.Tables);
        panierVBox.get(MeubleModele.Type.Chaises);
        panierVBox.get(MeubleModele.Type.Petits_electromenagers);
        panierVBox.get(MeubleModele.Type.Gros_electromenagers);
        panierVBox.get(MeubleModele.Type.Plomberie);

        /**Pour le catalogue**/
        this.catalogueVBox.put(MeubleModele.Type.Meubles, catalogueMeublesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Tables, catalogueTablesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Chaises, catalogueChaisesVbox);
        this.catalogueVBox.put(MeubleModele.Type.Petits_electromenagers, cataloguePElectroVbox);
        this.catalogueVBox.put(MeubleModele.Type.Gros_electromenagers, catalogueGElectroVbox);
        this.catalogueVBox.put(MeubleModele.Type.Plomberie, cataloguePlomberieVbox);

        /**Récupérer pour le catalogue**/
        catalogueVBox.get(MeubleModele.Type.Meubles);
        catalogueVBox.get(MeubleModele.Type.Tables);
        catalogueVBox.get(MeubleModele.Type.Chaises);
        catalogueVBox.get(MeubleModele.Type.Petits_electromenagers);
        catalogueVBox.get(MeubleModele.Type.Gros_electromenagers);
        catalogueVBox.get(MeubleModele.Type.Plomberie);
    }

}
