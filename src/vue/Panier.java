package vue;

import javafx.scene.layout.VBox;
import modele.Data;
import modele.MeubleModele;

import java.util.HashMap;

public class Panier {


    /**
     * Le panier dans l application
     */
    private VBox container;
    private HashMap<MeubleModele.Type, VBox> listTypes;

    public Panier(HashMap<MeubleModele.Type, VBox> listTypes){
        //this.container = container;
        this.listTypes = listTypes;
        Data.getCurrentSession().panneaux.panier = this;
    }

    /**
     * Ajout initial
     * @param meubleModele le meuble a ajouter
     */
    public void initCommit(MeubleModele meubleModele){
        listTypes.get(meubleModele.getType()).getChildren().add(meubleModele.getLittleFichePanier());
        meubleModele.getLittleFichePanier().setVisible(false);
    }

    /**
     * Fait apparaitre le meuble dans la liste du panier
     * @param meubleModele
     */
    public void add(MeubleModele meubleModele){
        meubleModele.getLittleFichePanier().setVisible(true);
    }

    /**
     * Fait disparaitre le meuble dans la liste du panier
     * @param meubleModele
     */
    public void remove(MeubleModele meubleModele){
        meubleModele.getLittleFichePanier().setVisible(false);
    }

}
