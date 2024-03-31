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
    private Label Message;

    @FXML
    private Label goToLoginLabel;

    private BlFacade businessLogic;
    private MainGUI mainGUI;
    private LoginController loginController;

    public void setMainGUIController(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    /**
     * Constructs a new RegisterController.
     *
     * @param bl The business logic facade.
     */
    public RegisterController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        this.mainGUIController = mainGUIController;
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
        String Role= roles.getValue();
        if(Username.isEmpty() || Password.isEmpty() || Email.isEmpty() || Name.isEmpty()  || Password2.isEmpty() || roles.getValue() == null){
            System.out.println("\nPlease fill all the fields\n");
            Message.setText("Please fill all the fields");
            Message.setVisible(true);
        } else if(Email.indexOf('@') == -1){
            System.out.println("\nThe email is not valid\n");
            Message.setText("The email is not valid");
            Message.setVisible(true);
        } else if(!businessLogic.registerUser(Username, Password, Email, Name, Role)){
            System.out.println("\nA user with this email already exists\n");
            Message.setText("A user with this email already exists");
            Message.setVisible(true);
        } else if(!Password.equals(Password2)){
            System.out.println("\nThe passwords are not the same\n");
            Message.setText("The passwords are not the same");
            Message.setVisible(true);
        }
        else{

            System.out.println("\nThe user has been registered\n");
            Message.setText("The user has been correctly registered!");
            Message.setVisible(true);
        }
    }

    @FXML
    void goToLoginAction(MouseEvent mouseEvent) {
        mainGUIController.showLogin();
        mainGUIController.getLoginController().restartLogin();
    }

    @FXML
    void initialize() {
        Message.setVisible(false);
        Message.setWrapText(true);
        Message.setAlignment(javafx.geometry.Pos.CENTER);
        ObservableList<String> options = FXCollections.observableArrayList("Driver", "Traveler");
        roles.setItems(options);
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
