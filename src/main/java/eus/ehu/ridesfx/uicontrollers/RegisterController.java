
package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.businessLogic.BlFacadeImplementation;
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
    private
    TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField name;

    @FXML
    private Button loginB;

    @FXML
    private Label ExistingEmail;

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    public RegisterController(BlFacade bl) {
        this.businessLogic = bl;
    }



    /**
     * This method is used to register a new user
     * @param event
     */
    @FXML
    void registerBtnClick(ActionEvent event)  {
        businessLogic = new BlFacadeImplementation();
        String Username = username.getText();
        String Password = password.getText();
        String Email = email.getText();
        String Name = name.getText();
        if(!businessLogic.registerUser(Username, Password, Email, Name)){
            System.out.println("A user with this email already exists");
            loginB.setVisible(false);
            ExistingEmail.setVisible(true);
        }
        else{
            System.out.println("The user has been registered");
            ExistingEmail.setVisible(false);
            loginB.setVisible(true);
        }

    }

    @FXML
    void initialize() {
        loginB.setVisible(false);
        ExistingEmail.setVisible(false);
    }



    @FXML
    void goToLogin(ActionEvent actionEvent) {
 //       mainGUIController.showScene("login");
    }







    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
