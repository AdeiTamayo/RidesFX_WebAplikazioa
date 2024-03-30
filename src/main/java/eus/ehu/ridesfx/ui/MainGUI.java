package eus.ehu.ridesfx.ui;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.uicontrollers.MainGUIController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import eus.ehu.ridesfx.uicontrollers.Controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {


    private BlFacade businessLogic;
    private Stage stage;
    private Scene scene;

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
                init(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void init(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("MainGUI.fxml"), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
        loader.setControllerFactory(controllerClass -> {
            try {
                return controllerClass
                        .getConstructor(BlFacade.class)
                        .newInstance(businessLogic);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(loader.load());
        stage.setTitle("ShareTrip BorderLayout");
        stage.setScene(scene);
        stage.setHeight(550);
        stage.setWidth(1000);
        stage.show();

    }




//  public void start(Stage stage) throws IOException {
//      init(stage);
//  }


//  public static void main(String[] args) {
//    launch();
//  }
}
