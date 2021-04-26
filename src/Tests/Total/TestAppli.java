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

        AppliTestController globalController = new AppliTestController(cuisine);
        BorderPane root = (BorderPane) globalController.loadFXMLWithController(getClass().getResource("AppliTest3.fxml"));

        Rectangle dimEcran = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        double width = dimEcran.width-100;//800;
        double height = dimEcran.height-15;//600;
        root.setMinSize(width,height);
        root.setMaxSize(width,height);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        globalController.setGlobalEventHandler(root);
        GestionaireMeubles meubles = new GestionaireMeubles(cuisine);
        meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
