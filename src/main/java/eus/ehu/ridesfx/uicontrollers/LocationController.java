package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Location;
import javafx.fxml.FXML;

import java.util.List;

public class LocationController implements Controller{


    private MainGUIController MainGUIController;

    private BlFacade businessLogic;


    public LocationController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.MainGUIController.setLocationController(this);
    }


    @FXML
    void initialize() {
        //TODO
        businessLogic.convertRideToLocation();
    }

    public List<Location> getLocations() {
        return businessLogic.getLocations();
    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.MainGUIController = mainGUIController;
    }






}
