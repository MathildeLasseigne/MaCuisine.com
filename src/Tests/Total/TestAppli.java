package Tests.Total;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.GestionaireMeubles;
import vue.Cuisine;

import java.awt.*;

public class TestAppli extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Test Appli");

        Cuisine cuisine = new Cuisine(300,400);

        BorderPane root = (BorderPane) Controller.loadFXMLWithController(getClass().getResource("AppliTest3.fxml"), new AppliTestController(cuisine));

        Rectangle dimEcran = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        double width = dimEcran.width-100;//800;
        double height = dimEcran.height-15;//600;
        root.setMinSize(width,height);
        root.setMaxSize(width,height);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        GestionaireMeubles meubles = new GestionaireMeubles(cuisine);
        meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
