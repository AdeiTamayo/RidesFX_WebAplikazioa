package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


//The class doesn't include controller interface because it is the mainApp, so it doesn't need to assign it.
public class MainGUIController {

    @FXML
    public Label lblDriver;

    @FXML
    public Button cityInfoButton;

    @FXML
    private Label typeOfUser;


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
    Button createRideBtn;

    @FXML
    Button alertsButton;

    @FXML
    Button queryReservationsButton;


    private LoginController loginController;


    private RegisterController registerController;

    private AlertsViewController alertsViewController;

    private QueryRidesController queryRidesController;

    private CreateRideController createRideController;

    private QueryReservationsController queryReservationsController;

    private LocationController LocationController;

    private CityInfoController cityInfoController;


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

    //The following methods are used to allow us to call these methods from another classes.


    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setRegisterController(RegisterController registerController) {
        this.registerController = registerController;
    }

    public void setAlertsViewController(AlertsViewController alertsViewController) {
        this.alertsViewController = alertsViewController;
    }

    public void setQueryRidesController(QueryRidesController queryRidesController) {
        this.queryRidesController = queryRidesController;
    }

    public void setCreateRideController(CreateRideController createRideController) {
        this.createRideController = createRideController;
    }

    public void setQueryReservationsController(QueryReservationsController queryReservationsController) {
        this.queryReservationsController = queryReservationsController;
    }

    public void setLocationController(LocationController locationController) {
        this.LocationController = locationController;
    }

    public void setCityInfoController(CityInfoController cityInfoController) {
        this.cityInfoController = cityInfoController;
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
     * Shows the alerts scene in the GUI.
     *
     * @param event
     */
    @FXML
    void alerts(ActionEvent event) {
        showScene("Alerts");
        alertsViewController.setView();
        alertsViewController.restartGUI();

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
        lblDriver.setAlignment(Pos.CENTER);
    }

    /**
     * Hides the login button on the GUI.
     */
    public void hideButtonLogin() {
        loginMainButton.setVisible(false);
    }

    /**
     * Hides the alerts button on the GUI.
     */
    public void hideButtonAlerts() {
        alertsButton.setVisible(false);
    }

    /**
     * Shows the alerts button on the GUI.
     */
    public void showButtonAlerts() {
        alertsButton.setVisible(true);
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
    public void showButtonCreateRide() {
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
        if (type.equals("NotLoggedInUser")) {
            typeOfUser.setText(type);
            typeOfUser.setAlignment(Pos.CENTER);
        } else {
            typeOfUser.setText(type + ": ");
            typeOfUser.setAlignment(Pos.CENTER);
        }
    }

    /**
     * Shows the initial GUI scene.
     */
    public void showInitialGUI() {
        showScene("InitialGUI");
    }

    /**
     * Shows the query reservations scene.
     */

    public void showQueryReservations() {
        showScene("Query Reservations");
        queryReservationsController.restartGUIQueryReservation();
    }

    /**
     * Hides the query reservations button.
     */
    public void hideButtonReservations() {
        queryReservationsButton.setVisible(false);
    }


    /**
     * Shows the query reservations button.
     */
    public void showButtonReservations() {
        queryReservationsButton.setVisible(true);
    }


    /**
     * Returns the login controller.
     *
     * @return The login controller.
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Returns the current user.
     *
     * @return The current user.
     */
    public User getCurrentUser() {
        return businessLogic.getCurrentUser();
    }

    /**
     * Updates the combo boxes in the query rides scene.
     */

    public void updateComboBoxesQueryRides() {
        queryRidesController.updateComboBox();
    }

    /**
     * Shows the city info scene.
     *
     * @param actionEvent
     */

    public void showCityInfo(ActionEvent actionEvent) {
        showScene("CityInfo");
        cityInfoController.clearCityInfo();
    }

    /**
     * Populates the reservations table.
     */
    public void populateReservationsTable() {
        queryReservationsController.setReservations();
    }


    @FXML
    void initialize() throws IOException {

        setDriverName(businessLogic.getCurrentUser().getName());
        setDriverType(businessLogic.getCurrentUser().getClass().getSimpleName());

        if (businessLogic.getCurrentUser().getClass().getSimpleName().equals("Driver")) {
            hideButtonQueryRides();
            showButtonCreateRide();
            showButtonAlerts();
            hideButtonReservations();

        } else if (businessLogic.getCurrentUser().getClass().getSimpleName().equals("Traveler")) {
            hideButtonCreateRide();
            showButtonAlerts();
            showButtonReservations();
        } else if (businessLogic.getCurrentUser().getClass().getSimpleName().equals("NotLoggedInUser")) {
            hideButtonCreateRide();
            hideButtonAlerts();
            hideButtonReservations();
        }


        changeUserButton.setVisible(false);

        queryRidesWin = load("QueryRides.fxml");
        createRideWin = load("CreateRide.fxml");
        loginWin = load("Login.fxml");
        registerWin = load("Register.fxml");
        InitialGUIWin = load("InitialGUI.fxml");
        alertsWin = load("AlertsView.fxml");
        queryReservationsWin = load("QueryReservations.fxml");
        cityInfoWin = load("CityInfo.fxml");


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
                    } else if (controllerClass == AlertsViewController.class) {
                        return new AlertsViewController(businessLogic, this);
                    } else if (controllerClass == QueryReservationsController.class) {
                        return new QueryReservationsController(businessLogic, this);
                    } else if (controllerClass == LocationController.class) {
                        return new LocationController(businessLogic, this);
                    }else if(controllerClass == CityInfoController.class){
                        return new CityInfoController(businessLogic, this);
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


    private Window createRideWin, queryRidesWin, loginWin, registerWin, InitialGUIWin, alertsWin, queryReservationsWin, cityInfoWin;

    private void showScene(String scene) {
        switch (scene) {
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Login" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
            case "InitialGUI" -> mainWrapper.setCenter(InitialGUIWin.ui);
            case "Alerts" -> mainWrapper.setCenter(alertsWin.ui);
            case "Query Reservations" -> mainWrapper.setCenter(queryReservationsWin.ui);
            case "CityInfo" -> mainWrapper.setCenter(cityInfoWin.ui);
        }
    }


}
