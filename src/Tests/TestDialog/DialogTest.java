package Tests.TestDialog;

import controller.SelectionController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class DialogTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        SelectionController dialogCtrl = new SelectionController(stage);
        Node selectionGraphic = dialogCtrl.loadFXMLWithController(getClass().getResource("../../vue/SelectionCuisine.fxml"));

        //Setting the title
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Appliquer", ButtonData.OK_DONE);
        //Setting the content of the dialog
        //dialog.setContentText("This is a sample dialog");
        //dialog.setGraphic(selectionGraphic);
        //dialog.getDialogPane().setContent(selectionGraphic);
        dialog.setDialogPane((DialogPane) selectionGraphic);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        //Setting the label
        Text txt = new Text("Click the button to show the dialog");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        txt.setFont(font);
        //Creating a button
        Button button = new Button("Show Dialog");
        //Showing the dialog on clicking the button
        button.setOnAction(e -> {
            dialog.showAndWait();
            System.out.println("wait");
            System.out.println(dialogCtrl.getDimensions());
        });
        //Creating a vbox to hold the button and the label
        HBox pane = new HBox(15);
        //Setting the space between the nodes of a HBox pane
        pane.setPadding(new Insets(50, 150, 50, 60));
        pane.getChildren().addAll(txt, button);
        //Creating a scene object
        Scene scene = new Scene(new Group(pane), 595, 250, Color.BEIGE);
        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
