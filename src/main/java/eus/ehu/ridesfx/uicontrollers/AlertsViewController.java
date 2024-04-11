package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.businessLogic.BlFacadeImplementation;
import eus.ehu.ridesfx.domain.Alert;
import eus.ehu.ridesfx.domain.Ride;
import eus.ehu.ridesfx.ui.MainGUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AlertsViewController implements Controller{


    @FXML
    private TableView alertTable;

    @FXML
    private TableColumn departC;
    @FXML
    private TableColumn arrivalC;

    @FXML
    private TableColumn numPlacesC;

    @FXML
    private TableColumn dateC;

    @FXML
    private TableColumn stateC;


    @FXML
    private Button deleteButton;

    @FXML
    private Button bookButton;
    
    private BlFacade businessLogic;
    


    private MainGUI mainGUI;

    private MainGUIController mainGUIController;

    @FXML
    void initialize() {
        //Hide the buttons
        deleteButton.setVisible(false);
        bookButton.setVisible(false);

        // Set the columns of the table
        departC.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        arrivalC.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        numPlacesC.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        dateC.setCellValueFactory(new PropertyValueFactory<>("date"));
        stateC.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Retrieve all the alerts of the current traveler
        List<Alert> alerts= businessLogic.getAlerts();

        //if there are no alerts in the table, show a message
        if(alerts.isEmpty()){
            alertTable.placeholderProperty().setValue(new Label("You don't have any alerts yet"));
        }

        //add a listener that will show the buttons when a row is selected
        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteButton.setVisible(true);
                bookButton.setVisible(true);
                bookButton.setDisable(true);
                //If the selected alert in the table has "Ride found"
                if(stateC.getCellData(alertTable.getSelectionModel().getSelectedItem()).equals("Ride found")){
                    //Enable the button to book the ride
                    bookButton.setDisable(false);
                }
            }
        });
    }
    public AlertsViewController(BlFacade bl, MainGUIController mainGUIController){
        this.businessLogic = bl;
        this.mainGUIController = mainGUIController;
        this.mainGUIController.setAlertsViewController(this);

    }
    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void closeClick(ActionEvent event) {
        mainGUIController.showInitialGUI();
    }

    public void deleteAlert(ActionEvent event) {
        businessLogic.deleteAlert((Alert) alertTable.getSelectionModel().getSelectedItem());
        //update the view
        setView();
    }

    public void bookRide(ActionEvent event) {
        //If the selected alert in the table has "Ride found" in the state column then book the ride
        if(stateC.getCellData(alertTable.getSelectionModel().getSelectedItem()).equals("Ride found")){
            //TODO book a ride(there might be more than one ride that matches the alert)
        }
    }

    /**
     * This method checks if there are matching rides for the given alert
     * @return true if there are matching rides, false otherwise
     */
    public boolean areRidesFound(Alert alert){
        //Check if any alert matches the new ride
        List<Ride> alertList= businessLogic.areMatchingRides(alert);
        if(alertList.isEmpty()){
            return false;
        }else{
            //update the state of the alert to "Ride found"
            businessLogic.updateAlertState((Alert) alertTable.getSelectionModel().getSelectedItem());

            return true;
        }
    }

    /**
     * This method sets the view of the alerts and is used each time the Alerts button is clicked in the main GUI
     */
    public void setView(){
        //Empty table
        alertTable.getItems().clear();
        // Retrieve all the alerts of the current traveler
        List<Alert> previousAlerts= businessLogic.getAlerts();
        //check if there are matching rides for any of the alerts
        for(Alert alert: previousAlerts){
            List<Ride> alertList= businessLogic.areMatchingRides(alert);
            if(!alertList.isEmpty()){
                //update the state of the alert to "Ride found"
                businessLogic.updateAlertState(alert);
            }
        }
        //Set the table with the alerts
        List<Alert> alerts= businessLogic.getAlerts();
        alertTable.getItems().addAll(alerts);

    }


}
