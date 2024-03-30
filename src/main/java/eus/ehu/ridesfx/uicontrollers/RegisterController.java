
package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.businessLogic.BlFacadeImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import eus.ehu.ridesfx.ui.MainGUI;

public class RegisterController implements Controller{

    private MainGUIController mainGUIController;

    @FXML
    private
    TextField username;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private
    TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField name;


    @FXML
    private Label Message;

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    private LoginController loginController;

    public RegisterController(BlFacade bl /*, LoginController loginController */) {
        this.businessLogic = bl;
        //this.loginController = loginController;
    }

    public void removeFieldsValue(){
        username.setText("");
        email.setText("");
        password.setText("");
        password2.setText("");
        name.setText("");
        roles.setValue(null);
    }

    /*
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

     */



    /**
     * This method is used to register a new user
     * @param event
     */
    @FXML
    void registerBtnClick(ActionEvent event)  {
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
    void initialize() {
        Message.setVisible(false);
        Message.setWrapText(true);
        Message.setAlignment(javafx.geometry.Pos.CENTER);
        ObservableList<String> options = FXCollections.observableArrayList("Driver", "Traveler");
        roles.setItems(options);
    }


    /*
    @FXML
    void goToLogin(ActionEvent actionEvent) {
       mainGUIController.showScene("login");
    }

     */







    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
