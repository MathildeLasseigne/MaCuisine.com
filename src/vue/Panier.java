package vue;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import modele.Meuble;

public class Panier {


    /**
     * Le panier dans l application
     */
    private VBox container;

    public Panier(VBox container){
        this.container = container;
    }

    /**
     * Ajout initial
     * @param m
     */
    public void initCommit(Meuble m){
        m.getLittleFichePanier().setVisible(false);
        container.getChildren().add(m.getLittleFichePanier());
    }

    /**
     * Fait apparaitre le meuble dans la liste du panier
     * @param meuble
     */
    public void add(Meuble meuble){
        meuble.getLittleFichePanier().setVisible(true);
    }

    /**
     * Fait disparaitre le meuble dans la liste du panier
     * @param meuble
     */
    public void remove(Meuble meuble){
        meuble.getLittleFichePanier().setVisible(false);
    }

}
