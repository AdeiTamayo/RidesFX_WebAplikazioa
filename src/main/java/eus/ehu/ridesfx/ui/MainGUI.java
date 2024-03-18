package eus.ehu.ridesfx.ui;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.uicontrollers.MainGUIController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import eus.ehu.ridesfx.uicontrollers.Controller;
import eus.ehu.ridesfx.uicontrollers.MainGUIController;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

    private BlFacade businessLogic;
    private Stage stage;
    private Scene scene;
    private MainGUIController mainGUIController; // Declare MainGUIController instance

    public BlFacade getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic(BlFacade afi) {
        businessLogic = afi;
    }

    public MainGUI(BlFacade bl) {
        Platform.startup(() -> {
            try {
                setBusinessLogic(bl);
                mainGUIController = new MainGUIController(bl); // Initialize MainGUIController instance
                init(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void init(Stage stage) throws IOException {
        showMain();
    }

    public void showMain() {
        mainGUIController.showScene("main");
    }

    public void showQueryRides() {
        mainGUIController.showScene("queryRides");
    }

    public void showCreateRide() {
        mainGUIController.showScene("createRide");
    }
}