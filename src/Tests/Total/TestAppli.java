package Tests.Total;

import controller.SelectionController;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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

        //Création de la cuisine
        javafx.scene.control.Dialog<String> dialog = new Dialog<String>();
        SelectionController dialogCtrl = new SelectionController(primaryStage);
        Node selectionPane = dialogCtrl.loadFXMLWithController(getClass().getResource("../../vue/SelectionCuisine.fxml"));
        //Setting the title
        dialog.setTitle("Creation de la cuisine :");
        ButtonType type = new ButtonType("Appliquer", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setDialogPane((DialogPane) selectionPane);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);


        //Data.createNewSession(new Point2D(300,400));

        AppliTestController globalController = new AppliTestController();
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

        dialog.showAndWait();
        dialogCtrl.loadSession();
        globalController.loadSession();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
