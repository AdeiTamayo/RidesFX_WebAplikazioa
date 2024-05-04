package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;

import eus.ehu.ridesfx.configuration.UtilDate;
import eus.ehu.ridesfx.domain.*;
import eus.ehu.ridesfx.domain.Alert;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.util.Date;
import java.util.List;

public class AlertsViewController implements Controller {


    private BlFacade businessLogic;

    private MainGUIController mainGUIController;

    @FXML
    private TableView alertTable;

    @FXML
    private Label labelAlerts;

    @FXML
    private ComboBox<Float> bookPrice;

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
    public Label notificationLabel;


    @FXML
    void initialize() {
        //Hide the buttons
        notificationLabel.setVisible(false);
        deleteButton.setVisible(false);
        bookButton.setVisible(false);
        createRideButton.setVisible(false);
        seatsQuantitySpinner.setVisible(false);
        labelSeatsQuantity.setVisible(false);
        priceLabel.setVisible(false);
        priceSpinner.setVisible(false);
        bookPrice.setVisible(false);
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
                        //Enable the combo box of prices
                        bookPrice.setVisible(true);
                        bookPrice.getItems().clear();
                        //Get the matching rides and their price
                        Alert a = (Alert) alertTable.getSelectionModel().getSelectedItem();
                        List<Ride> ridesList = businessLogic.areMatchingRides(a);
                        for(Ride ride: ridesList){
                            bookPrice.getItems().add(ride.getPrice());
                        }
                        //listener to enable the book button when a price is selected
                        bookPrice.getSelectionModel().selectedItemProperty().addListener((obs2, oldSelection2, newSelection2) -> {
                            if (newSelection2 != null) {
                                bookButton.setDisable(false);
                            }
                        });
                    }
                }
            } else if (businessLogic.getCurrentUser() instanceof Driver) {
                if (newSelection != null) {
                    seatsQuantitySpinner.setVisible(true);
                    //set the seats quantity to the number of seats of the alert
                    seatsQuantitySpinner.getValueFactory().setValue((int) numPlacesC.getCellData(alertTable.getSelectionModel().getSelectedItem()));
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

    /**
     * This method is called when the close button is clicked, it shows the initial GUI.
     *
     * @param event The ActionEvent associated with the event.
     */

    public void closeClick(ActionEvent event) {
        mainGUIController.showInitialGUI();
    }

    /**
     * This method is called when the delete button is clicked, it deletes the alert.
     *
     * @param event The ActionEvent associated with the event.
     */
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

    /**
     * This method is called when the book button is clicked, it makes a reservation of the ride.
     *It appears to the traveler.
     * @param event The ActionEvent associated with the event.
     */
    public void makeReservation(ActionEvent event) {
        //If the selected alert in the table has "Ride found" in the state column then book the ride
        if (stateC.getCellData(alertTable.getSelectionModel().getSelectedItem()).equals("Ride found")) {
            Alert a = (Alert) alertTable.getSelectionModel().getSelectedItem();
            List<Ride> ridesList = businessLogic.areMatchingRides(a);

            //book the  ride that matches the alert with the selected price
            Traveler traveler = (Traveler) businessLogic.getCurrentUser();
            int numPlaces = (int) numPlacesC.getCellData(alertTable.getSelectionModel().getSelectedItem());
            float price= bookPrice.getValue();
            int i=0;
            while(ridesList.get(i).getPrice()!=price){
                i++;
            }
            Ride ride = ridesList.get(i);


            businessLogic.makeReservation(traveler, ride, numPlaces, UtilDate.trim(new Date()));
            businessLogic.deleteAlert(a);

            //update the view

            notificationLabel.setVisible(true);
            notificationLabel.setText("Ride booked.");
            notificationLabel.setStyle("-fx-text-fill: green;");

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event2-> {
                setView();
            });
            pause.play();

        }
    }


    /**
     * This method sets the view of the alerts and is used each time the Alerts button is clicked in the main GUI
     */
    public void setView() {
        //Empty table
        alertTable.getItems().clear();
        notificationLabel.setVisible(false);
        bookButton.setDisable(true);
        bookPrice.setVisible(false);

        User currentUser = businessLogic.getCurrentUser();

        if (currentUser instanceof Traveler) {
            labelAlerts.setText("ALERTS");
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
            labelAlerts.setText("ALERTS: create a ride for the following existing alerts");
            // Retrieve all the rides of the current driver
            List<Alert> Alerts = businessLogic.getAllAlerts();
            //Set the table with the rides
            alertTable.getItems().addAll(Alerts);
        }
    }


    /**
     * This method is called when the create ride button is clicked, it creates a ride.
     * It appears to the driver.
     * @param actionEvent The ActionEvent associated with the event.
     */
    public void createRide(ActionEvent actionEvent) {
        Alert selectedAlert = (Alert) alertTable.getSelectionModel().getSelectedItem();
        Location departCity = selectedAlert.getFromLocation();
        Location arrivalCity = selectedAlert.getToLocation();
        Date date = selectedAlert.getDate();
        int numSeats = seatsQuantitySpinner.getValue();
        int price = priceSpinner.getValue();
        Driver driver = (Driver) businessLogic.getCurrentUser();

        if (departCity != null && arrivalCity != null && date != null && numSeats > 0 && price > 0) {
            try {
                Ride newRide = businessLogic.createRide(departCity, arrivalCity, date, numSeats, price, driver.getEmail());
                setView();
                notificationLabel.setVisible(true);
                notificationLabel.setStyle("-fx-text-fill: green;");
                System.out.println("Ride created successfully");

            }catch (Exception e) {
                System.out.println("The date must be later than today.");
                notificationLabel.setText("The date must be later than today.");
                notificationLabel.setStyle("-fx-text-fill: red;");
            }

        } else {
            System.out.println("Please ensure all fields are filled and the number of seats and price are positive.");
            notificationLabel.setText("Please ensure all fields are filled and the number of seats and price are positive.");
            notificationLabel.setStyle("-fx-text-fill: red;");
        }
    }


    /**
     * This method is called when the restart button is clicked, it restarts the GUI.
     */
    public void restartGUI(){
        deleteButton.setVisible(false);
        bookButton.setVisible(false);
        bookPrice.setVisible(false);
        createRideButton.setVisible(false);
        seatsQuantitySpinner.setVisible(false);
        labelSeatsQuantity.setVisible(false);
        priceLabel.setVisible(false);
        priceSpinner.setVisible(false);
        seatsQuantitySpinner.getValueFactory().setValue(0);
        priceSpinner.getValueFactory().setValue(0);

    }
}
