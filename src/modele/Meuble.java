package modele;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

import java.awt.image.BufferedImage;

public class Meuble {


    private boolean selected = false;

    /**Les dimentions du meuble**/
    protected double LARGEUR, HAUTEUR;

    private BufferedImage img = null;

    private String nom;
    private String constructeur;
    private String prix;
    private String description;

    /**
     * La position du meuble
     */
    private double posX, posY;

    /**La forme du meuble dans la cuisine**/
    private Shape forme;

    private Transform transform;

    public Meuble(String nom, String constructeur, double prix, double largeur, double hauteur, String description){
        this.LARGEUR = largeur;
        this.HAUTEUR = hauteur;

        this.nom = nom;
        this.constructeur = constructeur;
        this.prix = Double.toString(prix) + "€";
        this.description = description;
        contructFiches();
    }

    /**
     * Defini une image pour le meuble
     * @param img
     */
    public void setImg(BufferedImage img){
        this.img = img;
    }

    /**
     * Deplace le point vers celui donne
     * @param p
     */
    public void move(Point2D p){
        this.posX = p.getX();
        this.posY = p.getY();
    }

    public Point2D getPos(){
        return new Point2D(this.posX, this.posY);
    }

    /**
     * Selectionne le meuble
     */
    public void select(){
        this.selected = true;
    }

    /**
     * Deselectionne le meuble
     */
    public void unselect(){
        this.selected = false;
    }

    /**
     * Verifie si le meuble est selectionne
     * @return
     */
    public boolean isSelected(){
        return this.selected;
    }

    /*--------------------------Forme-----------------------------*/


    /**
     * Verifie si la position donnee est dans le meuble
     * @param pos
     * @return
     */
    public boolean isInside(Point2D pos){
        //TODO isInside meuble
        return false;
    }

    /**
     * Verifie si il y a collision entre 2 meubles
     * @param m
     * @return
     */
    public boolean collision(Meuble m){
        return false;
    }

    /*--------------------------Fiche----------------------------------*/
    /**
     * Construit les 2 fiches du meuble
     */
    public void contructFiches(){
        //TODO construire les 2 fiches avec element javafx. Construit dans var globale. Petite & grande fiche
    }

    /**
     * Renvoie la fiche du meuble //Pas void !!
     */
    public void getLittleFiche(){
        //TODO renvoie la variable globale
    }

    /**
     * Renvoie la grande fiche du meuble avec sa description
     */
    public  void getBigFiche(){
        //TODO renvoie variable globale
    }

}
