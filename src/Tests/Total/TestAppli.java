package Tests.Total;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.Data;
import modele.GestionaireMeubles;
import vue.Cuisine;

import java.awt.*;

public class TestAppli extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Test Appli");

        Data.createNewSession(new Point2D(300,400));

        AppliTestController globalController = new AppliTestController(Data.getCurrentSession().panneaux.cuisine);
        BorderPane root = (BorderPane) globalController.loadFXMLWithController(getClass().getResource("AppliTest4.fxml"));

        Rectangle dimEcran = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        double width = dimEcran.width-100;//800;
        double height = dimEcran.height-15;//600;
        root.setMinSize(width,height);
        root.setMaxSize(width,height);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../../Sprites/plan.png")));
        primaryStage.show();
        globalController.setGlobalEventHandler(root);
        //Data.getCurrentSession().createGestionnaireMeubles();
        //meubles.initPanierTest(4);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
