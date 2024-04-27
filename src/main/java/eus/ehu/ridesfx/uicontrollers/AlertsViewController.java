package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;

import eus.ehu.ridesfx.domain.*;
import eus.ehu.ridesfx.domain.Alert;
import eus.ehu.ridesfx.ui.MainGUI;

import eus.ehu.ridesfx.utils.Dates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class AlertsViewController implements Controller {


    private BlFacade businessLogic;

    private MainGUIController mainGUIController;

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

    @FXML
    public Button createRideButton;

    @FXML
    public Spinner<Integer> seatsQuantitySpinner;

    @FXML
    public Label labelSeatsQuantity;

    @FXML
    public Label priceLabel;

    @FXML
    public Spinner<Integer> priceSpinner;


    @FXML
    void initialize() {
        //Hide the buttons
        deleteButton.setVisible(false);
        bookButton.setVisible(false);
        createRideButton.setVisible(false);
        seatsQuantitySpinner.setVisible(false);
        labelSeatsQuantity.setVisible(false);
        priceLabel.setVisible(false);
        priceSpinner.setVisible(false);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        seatsQuantitySpinner.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        priceSpinner.setValueFactory(valueFactory2);


        // Set the columns of the table
        departC.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        arrivalC.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        numPlacesC.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        dateC.setCellValueFactory(new PropertyValueFactory<>("date"));
        stateC.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Retrieve all the alerts of the current traveler
        List<Alert> alerts = businessLogic.getAlerts();

        //if there are no alerts in the table, show a message
        if (alerts.isEmpty()) {
            alertTable.placeholderProperty().setValue(new Label("You don't have any alerts yet"));
        }

        //add a listener that will show the buttons when a row is selected
        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

            if (businessLogic.getCurrentUser() instanceof Traveler) {
                if (newSelection != null) {
                    deleteButton.setVisible(true);
                    bookButton.setVisible(true);
                    bookButton.setDisable(true);
                    //If the selected alert in the table has "Ride found"
                    if (stateC.getCellData(alertTable.getSelectionModel().getSelectedItem()).equals("Ride found")) {
                        //Enable the button to book the ride
                        bookButton.setDisable(false);
                    }
                }
            } else if (businessLogic.getCurrentUser() instanceof Driver) {
                if (newSelection != null) {
                    seatsQuantitySpinner.setVisible(true);
                    labelSeatsQuantity.setVisible(true);
                    createRideButton.setVisible(true);
                    priceLabel.setVisible(true);
                    priceSpinner.setVisible(true);

                }
            }
        });
    }

    public AlertsViewController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setAlertsViewController(this);

    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    public void closeClick(ActionEvent event) {
        mainGUIController.showInitialGUI();
    }

    public void deleteAlert(ActionEvent event) {
        try {
            Alert alert = (Alert) alertTable.getSelectionModel().getSelectedItem();
            if (alert == null) {
                // Show an error message to the user
                System.out.println("Please select an alert to delete.");
            } else {
                businessLogic.deleteAlert(alert);
                // Update the view
                setView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeReservation(ActionEvent event) {
        //If the selected alert in the table has "Ride found" in the state column then book the ride
        if (stateC.getCellData(alertTable.getSelectionModel().getSelectedItem()).equals("Ride found")) {
            //TODO book a ride(there might be more than one ride that matches the alert)
            Alert a = (Alert) alertTable.getSelectionModel().getSelectedItem();
            List<Ride> ridesList = businessLogic.areMatchingRides(a);
            //book the first ride that matches the alert

            Traveler traveler = (Traveler) businessLogic.getCurrentUser();
            int numPlaces = (int) numPlacesC.getCellData(alertTable.getSelectionModel().getSelectedItem());

            Ride ride = ridesList.get(0);


            businessLogic.makeReservation(traveler, ride, numPlaces);
            businessLogic.deleteAlert(a);
            //update the view
            setView();

        }
    }


    /**
     * This method sets the view of the alerts and is used each time the Alerts button is clicked in the main GUI
     */
    public void setView() {
        //Empty table
        alertTable.getItems().clear();

        User currentUser = businessLogic.getCurrentUser();

        if (currentUser instanceof Traveler) {
            // Retrieve all the alerts of the current traveler
            List<Alert> previousAlerts = businessLogic.getAlerts();
            //check if there are matching rides for any of the alerts
            for (Alert alert : previousAlerts) {
                List<Ride> ridesList = businessLogic.areMatchingRides(alert);
                if (!ridesList.isEmpty()) {
                    //update the state of the alert to "Ride found"
                    businessLogic.updateAlertState(alert);
                }
            }
            //Set the table with the alerts
            List<Alert> alerts = businessLogic.getAlerts();
            alertTable.getItems().addAll(alerts);
        } else if (currentUser instanceof Driver) {
            // Retrieve all the rides of the current driver
            List<Alert> Alerts = businessLogic.getAllAlerts();
            //Set the table with the rides
            alertTable.getItems().addAll(Alerts);
        }
    }


    public void createRide(ActionEvent actionEvent) {
        Alert selectedAlert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        String departCity = selectedAlert.getFromLocation();
        String arrivalCity = selectedAlert.getToLocation();
        Date date = selectedAlert.getDate();
        int numSeats = seatsQuantitySpinner.getValue();
        int price = priceSpinner.getValue();
        Driver driver = (Driver) businessLogic.getCurrentUser();

        if (departCity != null && arrivalCity != null && date != null && numSeats > 0 && price > 0) {
            try {
                Ride newRide = businessLogic.createRide(departCity, arrivalCity, date, numSeats, price, driver.getEmail());
                setView();
            }catch (Exception e) {
                System.out.println("The date must be later than today.");
            }

        } else {
            System.out.println("Please ensure all fields are filled and the number of seats and price are positive.");
        }
    }


    public void restartGUI(){
        deleteButton.setVisible(false);
        bookButton.setVisible(false);
        createRideButton.setVisible(false);
        seatsQuantitySpinner.setVisible(false);
        labelSeatsQuantity.setVisible(false);
        priceLabel.setVisible(false);
        priceSpinner.setVisible(false);
    }
}
