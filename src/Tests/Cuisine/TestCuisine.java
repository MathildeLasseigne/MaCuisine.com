package Tests.Cuisine;

import controller.DragController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.GestionaireMeubles;
import vue.Cuisine;

public class TestCuisine extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Test Cuisine");

        Cuisine cuisine = new Cuisine(300,300);

        primaryStage.setScene(new Scene(cuisine));
        primaryStage.show();
        GestionaireMeubles meubles = new GestionaireMeubles(cuisine);
        meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
