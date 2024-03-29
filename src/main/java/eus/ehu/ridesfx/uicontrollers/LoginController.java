
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

public class LoginController implements Controller {


    private MainGUIController mainGUIController;

    @FXML
    private Label Text;

    @FXML
    private Label TestLabel;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label InvalidUser;
    @FXML
    private Label WrongPassword;


    private MainGUI mainGUI;


    private BlFacade businessLogic;

    //private MainGUIController mainGUIController;


    public LoginController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        this.mainGUIController = mainGUIController;

        //declaration used to make calling from the mainGUIController to this class possible
        this.mainGUIController.setLoginController(this);
    }


    public void setTextMail(String newText) {
        email.setText(newText);
    }

    public void setTextPassword(String newText) {
        password.setText(newText);
    }


    /**
     * This method is used to register a new user
     *
     * @param event
     */
    @FXML
    void loginBtnClick(ActionEvent event) {
        String Email = email.getText();
        String Password = password.getText();
        Text.setVisible(false);
        WrongPassword.setVisible(false);
        InvalidUser.setVisible(false);
        if (Email.equals("") || Password.equals("")) {
            System.out.println("\nPlease fill in all the fields\n");
            Text.setText("Please fill in all the fields");
            Text.setVisible(true);
        } else if (businessLogic.checkUser(Email) == null) {
            System.out.println("\nA user with this email doen't exist. Please register first.\n");
            InvalidUser.setVisible(true);
        } else {
            if (!businessLogic.checkPassword(Email, Password)) {
                System.out.println("\nThe password is incorrect\n");
                WrongPassword.setVisible(true);
            } else {
                Text.setText("\nYou have been correctly logged in!\n");
                Text.setVisible(true);
                businessLogic.setCurrentUser(businessLogic.checkUser(Email));


                //This prints the name of the driver in the console
                System.out.println("\nThe name of the driver is : \n");
                System.out.println(businessLogic.getCurrentUser().getName());


                mainGUIController.setDriverName(businessLogic.getCurrentUser().getName());
                mainGUIController.hideButtonLogin();
                mainGUIController.hideButtonRegister();
                mainGUIController.showButtonChangeUserButton();


            }
        }
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


    @FXML
    void initialize() {

        InvalidUser.setVisible(false);
        WrongPassword.setVisible(false);
        Text.setVisible(false);
        Text.setWrapText(true);
        Text.setAlignment(javafx.geometry.Pos.CENTER);


    }
}
