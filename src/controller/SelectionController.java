package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SelectionController extends Controller{



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

    @FXML
    private Button appliquerButton;

    /**
     * Verifie si la cuisine a ete creee a l instant ou non
     */
    private BooleanProperty isNewCuisine = new SimpleBooleanProperty(true);

    public SelectionController(){

    }

    @FXML
    public void initialize(){
        this.LARGEUR.setTextFormatter(getDoubleFormater());
        this.HAUTEUR.setTextFormatter(getDoubleFormater());
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
     * Cree une session dans data en fonction de la maniere dont la cuisine a ete creee
     */
    public void createSession(){

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
