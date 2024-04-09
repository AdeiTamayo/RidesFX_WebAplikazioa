package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.ui.MainGUI;

public class InitialGUIController implements Controller{

    private BlFacade businessLogic;
    private MainGUI mainGUI;


    public InitialGUIController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

}
