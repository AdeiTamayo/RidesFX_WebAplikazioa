
package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.businessLogic.BlFacadeImplementation;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;

public class LoginController implements Controller{
    private MainGUIController mainGUIController;

    @FXML
    private
    TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label InvalidUser;
    @FXML
    private Label WrongPassword;

    private MainGUI mainGUI;


    private BlFacade businessLogic;

    /**
     * This method is used to register a new user
     * @param event
     */
    @FXML
    void loginBtnClick(ActionEvent event) {
        businessLogic = new BlFacadeImplementation();
        String Email = email.getText();
        String Password = password.getText();
        if (businessLogic.checkUser(Email)==null){
            System.out.println("A user with this email doen't exist. Please register first.");
            WrongPassword.setVisible(false);
            InvalidUser.setVisible(true);
        }
        else{
            if (!businessLogic.checkPassword(Email, Password)){
                InvalidUser.setVisible(false);
                System.out.println("The password is incorrect");
                WrongPassword.setVisible(true);
            } else{
                InvalidUser.setVisible(false);
                WrongPassword.setVisible(false);
                businessLogic.setCurrentDriver(businessLogic.checkUser(Email));
                //mainGUI.showMain();
            }
        }
    }

    //FIXME update this method to show the register scene
    /*
    @FXML
    void handleLabelClick(javafx.scene.input.MouseEvent mouseEvent) {
        //mainGUI= new MainGUI(businessLogic);
        MainGUIController.showScece("Register");

    }

     */
    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    void initialize() {
        InvalidUser.setVisible(false);
        WrongPassword.setVisible(false);
    }
}
