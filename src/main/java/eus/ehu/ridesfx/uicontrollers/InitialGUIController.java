package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;


public class InitialGUIController implements Controller {

    private BlFacade businessLogic;
    private MainGUIController mainGUIController;


    public InitialGUIController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

}
