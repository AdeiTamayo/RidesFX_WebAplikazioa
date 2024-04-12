package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the registration functionality.
 */
public class RegisterController implements Controller {

    private MainGUIController mainGUIController;


    @FXML
    private TextField username;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField name;

    @FXML
    private Label message;

    @FXML
    private Label goToLoginLabel;

    private BlFacade businessLogic;
    private MainGUI mainGUI;
    private LoginController loginController;


    /**
     * Constructs a new RegisterController.
     *
     * @param bl The business logic facade.
     */
    public RegisterController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setRegisterController(this);
    }

    /**
     * Clears all input fields.
     */
    public void removeFieldsValue() {
        username.setText("");
        email.setText("");
        password.setText("");
        password2.setText("");
        name.setText("");
        roles.setValue(null);
    }

    /**
     * Handles the register button click event.
     *
     * @param event The ActionEvent associated with the event.
     */
    @FXML
    void registerBtnClick(ActionEvent event) {
        String Username = username.getText();
        String Password = password.getText();
        String Password2 = password2.getText();
        String Email = email.getText();
        String Name = name.getText();
        String Role = roles.getValue();
        if (Username.isEmpty() || Password.isEmpty() || Email.isEmpty() || Name.isEmpty() || Password2.isEmpty() || roles.getValue() == null) {
            System.out.println("\nPlease fill all the fields\n");
            message.setText("Please fill all the fields");
            message.setVisible(true);
        } else if (Email.indexOf('@') == -1) {
            System.out.println("\nThe email is not valid\n");
            message.setText("The email is not valid");
            message.setVisible(true);
        } else if (!businessLogic.registerUser(Username, Password, Email, Name, Role)) {
            System.out.println("\nA user with this email already exists\n");
            message.setText("A user with this email already exists");
            message.setVisible(true);
        } else if (!Password.equals(Password2)) {
            System.out.println("\nThe passwords are not the same\n");
            message.setText("The passwords are not the same");
            message.setVisible(true);
        } else {

            System.out.println("\nThe user has been registered\n");
            message.setText("The user has been correctly registered!");
            message.setVisible(true);
        }
    }

    @FXML
    void goToLoginAction(MouseEvent mouseEvent) {
        mainGUIController.showLogin();
        mainGUIController.getLoginController().restartLogin();
    }

    @FXML
    void initialize() {
        message.setVisible(false);
        message.setWrapText(true);
        message.setAlignment(javafx.geometry.Pos.CENTER);
        ObservableList<String> options = FXCollections.observableArrayList("Driver", "Traveler");
        roles.setItems(options);
    }

    @FXML
    void clearRegister(ActionEvent event) {
        removeFieldsValue();
    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }
}
