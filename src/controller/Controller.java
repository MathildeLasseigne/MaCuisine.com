package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Abstract class containing methods for FXML controllers
 */
public abstract class Controller {

    /**
     * The FXMLLoader used if Controller linked to fxml file with <i>loadFXMLWithController</i>
     * @see Controller#loadFXMLWithController(String)
     */
    protected FXMLLoader fxmlLoader;

    /**
     * Load the fxml with this controller instanciated.
     * <br/>Save the FXMLLoader
     * @param fxmlPath path to fxml file
     * @return the fxml file content as Parent
     * @throws IOException
     * @see Controller#loadFXMLWithController(String, Controller)
     * @see Controller#fxmlLoader
     */
    public Parent loadFXMLWithController(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        loader.setController(this);
        Parent fxmlContent = null;
        fxmlContent = loader.load();
        this.fxmlLoader = fxmlLoader;
        return fxmlContent;
    }

    /**
     * Load the fxml with an instanciated Controller
     * @param fxmlPath path to fxml file
     * @param controller the instanciated Controller
     * @return the fxml file content as Parent
     * @throws IOException
     * @see Controller#loadFXMLWithController(String)
     */
    public static Parent loadFXMLWithController(String fxmlPath, Controller controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
        loader.setController(controller);
        Parent fxmlContent = null;
        fxmlContent = loader.load();
        return fxmlContent;
    }
}
