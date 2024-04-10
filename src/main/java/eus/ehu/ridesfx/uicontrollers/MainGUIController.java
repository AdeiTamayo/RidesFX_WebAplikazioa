package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
//import org.kordamp.bootstrapfx.BootstrapFX;ff

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUIController {

    @FXML
    public Label lblDriver;

    @FXML
    private Label typeOfUser;

    /*
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

     */


    @FXML
    private BorderPane mainWrapper;

    private BlFacade businessLogic;

    @FXML
    private Button loginMainButton;
    @FXML
    private Button registerMainButton;

    @FXML
    private Button changeUserButton;

    @FXML
    private Button queryRidesBtn;

    @FXML
    private Button homeButton;

    @FXML
    Button createRideBtn;


    private LoginController loginController;


    private RegisterController registerController;

    private QueryRidesController queryRidesController;

    private CreateRideController createRideController;


    public MainGUIController() {
    }

    /**
     * Constructor of the MainGUIController class.
     *
     * @param blFacade The business logic facade.
     */
    public MainGUIController(BlFacade blFacade) {
        this.businessLogic = blFacade;
    }

    //The following methods are used to allow us to call this methods from another classes.


    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setRegisterController(RegisterController registerController) {
        this.registerController = registerController;
    }

    public void setQueryRidesController(QueryRidesController queryRidesController) {
        this.queryRidesController = queryRidesController;
    }

    public void setCreateRideController(CreateRideController createRideController) {
        this.createRideController = createRideController;
    }

    //The following methods are used to manage the buttons in the GUI

    /**
     * Shows the query rides scene in the GUI.
     *
     * @param event
     */
    @FXML
    void queryRides(ActionEvent event) {
        showScene("Query Rides");
    }

    /**
     * Shows the login scene in the GUI.
     *
     * @param event
     */

    @FXML
    void login(ActionEvent event) {
        showScene("Login");
    }


    /**
     * Shows the register scene in the GUI.
     *
     * @param event
     */
    @FXML
    void register(ActionEvent event) {
        showScene("Register");
    }

    /**
     * Shows the create ride scene in the GUI.
     *
     * @param event
     */

    @FXML
    void createRide(ActionEvent event) {
        showScene("Create Ride");
    }

    /**
     * Shows the login GUI scene in the GUI.
     *
     * @param event
     */

    @FXML
    void changeUser(ActionEvent event) {
        showScene("Login");

        //Method used to restart the apparence of login controller
        loginController.restartLogin();
    }



    /**
     * Shows the initial GUI scene in the GUI.
     *
     * @param event
     */

    @FXML
    void goHome(ActionEvent event) {
        showScene("InitialGUI");
    }


    /**
     * Sets the name of the driver in the label on the GUI.
     *
     * @param name The name of the driver to be displayed.
     */
    public void setDriverName(String name) {
        lblDriver.setText(name);
    }

    /**
     * Hides the login button on the GUI.
     */
    public void hideButtonLogin() {
        loginMainButton.setVisible(false);
    }

    /**
     * Hides the register button on the GUI.
     */
    public void hideButtonRegister() {
        registerMainButton.setVisible(false);
    }

    /**
     * Hides the query rides button on the GUI.
     */
    public void hideButtonQueryRides() {
        queryRidesBtn.setVisible(false);
    }

    /**
     * Shows the query rides button on the GUI.
     */
    public void showButtonQueryRides() {
        queryRidesBtn.setVisible(true);
    }

    /**
     * Shows the create ride button on the GUI.
     */
    public void showButtonCreateRide(){
        createRideBtn.setVisible(true);
    }

    /**
     * Hides the create ride button on the GUI.
     */
    public void hideButtonCreateRide() {
        createRideBtn.setVisible(false);
    }

    /**
     * Shows the change user button on the GUI.
     */
    public void showButtonChangeUserButton() {
        changeUserButton.setVisible(true);
    }

    /**
     * Shows the register scene in the GUI.
     */
    public void showRegister() {
        showScene("Register");
    }

    /**
     * Shows the login scene in the GUI.
     */
    public void showLogin() {
        showScene("Login");
    }

    /**
     * Sets the type of the driver in the label on the GUI.
     */
    public void setDriverType(String type) {
        typeOfUser.setText(type + ": ");
    }

    /**
     * Shows the initial GUI scene.
     */
    public void showInitialGUI() {
        showScene("InitialGUI");
    }

    //New method

    /**
     * Returns the login controller.
     *
     * @return The login controller.
     */
    public LoginController getLoginController() {
        return loginController;
    }


    @FXML
    void initialize() throws IOException {

        setDriverName(businessLogic.getCurrentUser().getName());
        setDriverType(businessLogic.getCurrentUser().getClass().getSimpleName());

        if(businessLogic.getCurrentUser().getClass().getSimpleName().equals("Driver")){
            hideButtonQueryRides();
        }else if(businessLogic.getCurrentUser().getClass().getSimpleName().equals("Traveler")){
            hideButtonCreateRide();
        }


        changeUserButton.setVisible(false);

        queryRidesWin = load("QueryRides.fxml");
        createRideWin = load("CreateRide.fxml");
        loginWin = load("Login.fxml");
        registerWin = load("Register.fxml");
        InitialGUIWin = load("InitialGUI.fxml");


        showScene("InitialGUI");
        System.out.println("\n\n\n\nShare Trip Project\n\n\n\n");
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
                    if (controllerClass == LoginController.class) {
                        return new LoginController(businessLogic, this);
                    } else if (controllerClass == RegisterController.class) {
                        return new RegisterController(businessLogic, this);
                    } else if (controllerClass == QueryRidesController.class) {
                        return new QueryRidesController(businessLogic, this);
                    } else if (controllerClass == CreateRideController.class) {
                        return new CreateRideController(businessLogic, this);
                    } else {
                        return controllerClass
                                .getConstructor(BlFacade.class)
                                .newInstance(businessLogic);
                    }
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


    private Window createRideWin, queryRidesWin, loginWin, registerWin, InitialGUIWin;

    private void showScene(String scene) {
        switch (scene) {
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Login" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
            case "InitialGUI" -> mainWrapper.setCenter(InitialGUIWin.ui);
        }
    }


}
