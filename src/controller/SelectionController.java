package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import modele.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SelectionController extends FXMLController {


    private Window owner;


    @FXML
    private Label filePath;

    @FXML
    private Button searchFile;

    @FXML
    private Button cancelFile;

    @FXML
    private TextField LARGEUR;

    @FXML
    private TextField HAUTEUR;


    private File fileSelection = null;


    private StringProperty filePathProperty = new SimpleStringProperty("Selectionnez/un/fichier");

    /**
     * Verifie si la cuisine a ete creee a l instant ou non
     */
    private BooleanProperty isNewCuisine = new SimpleBooleanProperty(true);

    /**
     * Cree le controlleur de la fenetre de selection
     * @param owner souvent primaryStage
     */
    public SelectionController(Window owner){
        this.owner = owner;
    }

    @FXML
    public void initialize(){
        this.LARGEUR.setTextFormatter(getDoubleFormater());
        this.HAUTEUR.setTextFormatter(getDoubleFormater());
        this.filePath.textProperty().bind(this.filePathProperty);
        searchFile.setOnAction((actionEvent)->{
            try {
                searchFileAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelFile.setOnAction((actionEvent)->{cancelFileAction();});
    }


    public double getLARGEUR(){
        //return Double.valueOf(this.LARGEUR.getText());
        return (double) this.LARGEUR.getTextFormatter().getValue();
    }

    public double getHAUTEUR(){
        //return Double.valueOf(this.HAUTEUR.getText());
        return (double) this.HAUTEUR.getTextFormatter().getValue();
    }

    /**
     * Renvoie les dimensions de la cuisine saisis par l utilisateur
     * @return les dimensions sous forme de Point2D
     */
    public Point2D getDimensions(){
        return new Point2D(getLARGEUR(), getHAUTEUR());
    }

    /**
     * Cree une session dans data en fonction de la maniere dont la cuisine a ete creee
     */
    public void loadSession(){
        if(this.fileSelection == null){
            Data.createNewSession(getDimensions());
        } else {
            System.out.println("Selectionner un fichier");
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.fileSelection))) {
                Data.Session session = (Data.Session) in.readObject() ;
                Data.loadSession(session);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    /**
     * L action du button search File
     */
    public void searchFileAction() throws IOException {
        cancelFileAction();
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Open File");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        this.fileSelection = filechooser.showOpenDialog(owner);
        if(this.fileSelection != null){
            this.filePath.setTextFill(Color.BLACK);
            this.filePathProperty.set(this.fileSelection.getCanonicalPath());
        } else {
            this.filePath.setTextFill(Color.RED);
            this.filePathProperty.set("Ce fichier n a pas pu etre recupere");
        }
    }

    /**
     * L action d annuler la selection de file
     */
    public void cancelFileAction(){
        this.filePath.setTextFill(Color.BLACK);
        this.fileSelection = null;
        this.filePathProperty.set("Selectionnez/un/fichier");
    }


    /**
     * Cree un nouveau text formatter ne prenant que des doubles.
     * <br/>Un textformatter ne peut etre utilise qu une seule fois
     * <br/><a href= "https://stackoverflow.com/questions/45977390/how-to-force-a-double-input-in-a-textfield-in-javafx">Source</a>
     * @return
     */
    private TextFormatter<Double> getDoubleFormater(){
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0.0, filter);
    }

}
