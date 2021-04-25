package Tests.Total;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestAppli extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Test Appli");

        //Cuisine cuisine = new Cuisine(300,300);

        Parent root =  FXMLLoader.load(getClass().getResource("AppliTest.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //GestionaireMeubles meubles = new GestionaireMeubles(cuisine);
        //meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
