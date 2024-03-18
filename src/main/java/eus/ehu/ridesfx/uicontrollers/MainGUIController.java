package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGUIController {
    private Window CreateRideWin, QueryRidesWin, MainWin;

    @FXML
    private Label selectOptionLbl;

    @FXML
    private Label lblDriver;


    @FXML
    private Button queryRidesBtn;

    @FXML
    private Button createRideBtn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    private BlFacade businessLogic;
    /*
    public MainGUIController() {}
    */

    //Borderpane kodean ez dago baina programa honen funtzionamendurako behar da
    public MainGUIController(BlFacade blFacade) {
        businessLogic = blFacade;
    }




    public class Window {
        private Controller controller;
        private Parent ui;
    }

    private Window load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
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


    @FXML
    private BorderPane mainPane;

    @FXML
    void queryRides(ActionEvent event) {
        showScene("queryRides");
    }

    @FXML
    void createRide(ActionEvent event) {
        showScene("createRide");
    }



    /*
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

     */



    @FXML
    void initialize() {

        CreateRideWin = load("CreateRide.fxml");
        QueryRidesWin = load("QueryRides.fxml");
        MainWin = load("MainGUI.fxml");

        showScene("main");

        // set current driver name
        lblDriver.setText(businessLogic.getCurrentDriver().getName());
    }

    public void showScene(String scene) {
        switch (scene) {
            case "createRide" -> mainPane.setCenter(CreateRideWin.ui);
            case "queryRides" -> mainPane.setCenter(QueryRidesWin.ui);
            case "main" -> mainPane.setCenter(MainWin.ui);

        }
    }
}

//FIXME error: java.lang.NullPointerException: Cannot read field "ui" because "this.MainWin" is null
