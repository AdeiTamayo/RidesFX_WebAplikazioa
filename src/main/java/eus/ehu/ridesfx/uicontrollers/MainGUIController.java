package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.scene.layout.BorderPane;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUIController {

    @FXML
    public Label lblDriver;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private BorderPane mainWrapper;

    private BlFacade businessLogic;

    public MainGUIController() {
    }

    ;

    public MainGUIController(BlFacade blFacade) {
        this.businessLogic = blFacade;
    }


    @FXML
    void queryRides(ActionEvent event) {
        showScene("Query Rides");
    }

    @FXML
    void login(ActionEvent event) {
        showScene("Login");
    }

    @FXML
    void register(ActionEvent event) {
        showScene("Register");
    }

    @FXML
    void createRide(ActionEvent event) {
        showScene("Create Ride");
    }


    @FXML
    void initialize() throws IOException {

        setDriverName(businessLogic.getCurrentDriver().getName());


        queryRidesWin = load("QueryRides.fxml");
        createRideWin = load("CreateRide.fxml");
        loginWin = load("Login.fxml");
        registerWin = load("Register.fxml");

        showScene("Query Rides");
    }


    private Window createRideWin, queryRidesWin, loginWin, registerWin, mainWin;


    public void setDriverName(String name) {

        //FIXME when called from another class lbl is null and throws a null pointer exception
        lblDriver.setText(name);
    }


    public class Window {
        private Controller controller;
        private Parent ui;
    }


    private Window load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxml), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
            loader.setControllerFactory(controllerClass -> {
                try {
                    return controllerClass
                            .getConstructor(BlFacade.class)
                            .newInstance(businessLogic);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            Parent ui = loader.load();
            Controller controller = loader.getController();

            Window window = new Window();
            window.controller = controller;
            window.ui = ui;
            return window;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void showScene(String scene) {
        switch (scene) {
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Login" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
        }
    }


}
