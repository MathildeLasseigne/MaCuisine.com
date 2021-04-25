package Tests.Total;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.GestionaireMeubles;
import vue.Cuisine;

public class TestAppli extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Test Appli");

        Cuisine cuisine = new Cuisine(300,300);

        Parent root = Controller.loadFXMLWithController(getClass().getResource("AppliTest.fxml"), new AppliTestController(cuisine));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        GestionaireMeubles meubles = new GestionaireMeubles(cuisine);
        meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
