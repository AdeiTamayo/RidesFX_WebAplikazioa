package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.domain.Traveler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the login functionality.
 */
public class LoginController implements Controller {

    private MainGUIController mainGUIController;

    private BlFacade businessLogic;

    @FXML
    private Label Text;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label InvalidUser;

    @FXML
    private Label wrongPassword;

    @FXML
    private Label registerLabel;


    /**
     * Constructs a new LoginController.
     *
     * @param bl                The business logic facade.
     * @param mainGUIController The main GUI controller.
     */
    public LoginController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setLoginController(this);
    }

    /**
     * Restart the login controller interface.
     */
    public void restartLogin() {
        registerLabel.setVisible(true);
        Text.setVisible(false);
        password.setText("");
        email.setText("");
        wrongPassword.setVisible(false);
        InvalidUser.setVisible(false);
    }


    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    /**
     * Handles the login button click event.
     *
     * @param event The ActionEvent associated with the event.
     */
    @FXML
    void loginBtnClick(ActionEvent event) {
        String Email = email.getText();
        String Password = password.getText();
        Text.setVisible(false);
        wrongPassword.setVisible(false);
        InvalidUser.setVisible(false);
        Text.setStyle("-fx-text-fill: red");
        if (Email.isEmpty() || Password.isEmpty()) {
            System.out.println("\nPlease fill in all the fields\n");
            Text.setText("Please fill in all the fields");
            Text.setVisible(true);
        } else if (businessLogic.checkUser(Email) == null) {
            System.out.println("\nA user with this email doen't exist. Please register first.\n");
            Text.setStyle("-fx-text-fill: red");
            InvalidUser.setVisible(true);
        } else {
            if (!businessLogic.checkPassword(Email, Password)) {
                System.out.println("\nThe password is incorrect\n");
                wrongPassword.setVisible(true);
            } else {
                Text.setText("\nYou have been correctly logged in!\n");
                Text.setVisible(true);
                Text.setStyle("-fx-text-fill: green");
                businessLogic.setCurrentUser(businessLogic.checkUser(Email));


                //This prints the name of the driver in the console
                if (businessLogic.getCurrentUser() instanceof Driver) {
                    System.out.println("\nThe name of the driver is : \n");
                } else if (businessLogic.getCurrentUser() instanceof Traveler) {
                    System.out.println("\nThe name of the traveler is : \n");
                }
                System.out.println(businessLogic.getCurrentUser().getName());


                mainGUIController.setDriverName(businessLogic.getCurrentUser().getName());
                mainGUIController.hideButtonLogin();
                mainGUIController.hideButtonRegister();
                mainGUIController.showButtonChangeUserButton();

                registerLabel.setVisible(false);

                //This if statement hides the query rides button if the user is a driver and hides the create ride button if the user is a traveler
                if (businessLogic.getCurrentUser() instanceof Driver) {
                    mainGUIController.hideButtonQueryRides();
                    mainGUIController.showButtonCreateRide();
                    mainGUIController.showButtonReservations();
                    mainGUIController.showButtonAlerts();
                } else if (businessLogic.getCurrentUser() instanceof Traveler) {
                    mainGUIController.hideButtonCreateRide();
                    mainGUIController.showButtonQueryRides();
                    mainGUIController.showButtonAlerts();
                    mainGUIController.showButtonReservations();
                }


                if (businessLogic.getCurrentUser().getClass().getSimpleName().equals("Driver")) {
                    mainGUIController.setDriverType("Driver");
                } else {
                    mainGUIController.setDriverType("Traveler");
                }


            }
        }
    }

    @FXML
    void initialize() {
        InvalidUser.setVisible(false);
        wrongPassword.setVisible(false);
        Text.setVisible(false);
        Text.setWrapText(true);
        Text.setAlignment(javafx.geometry.Pos.CENTER);
    }

    @FXML
    void clearLogin(ActionEvent event) {
        restartLogin();
    }

    @FXML
    public void registerLabelClick(MouseEvent mouseEvent) {
        mainGUIController.showRegister();


    }
}

