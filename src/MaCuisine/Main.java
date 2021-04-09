package MaCuisine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("Initialisation...");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rightPanelRoot = FXMLLoader.load(getClass().getResource("RightPanel.fxml"));
        Scene rightPanel = new Scene(rightPanelRoot);

        Parent maCuisineRoot = FXMLLoader.load(getClass().getResource("MaCuisine.fxml"));
        Scene maCuisine = new Scene(maCuisineRoot);
        primaryStage.setTitle("MaCuisine");
        primaryStage.setScene(maCuisine);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Fermeture...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
