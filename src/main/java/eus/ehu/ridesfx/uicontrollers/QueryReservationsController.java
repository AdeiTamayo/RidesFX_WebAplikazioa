package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.domain.Location;
import eus.ehu.ridesfx.domain.Reservation;
import eus.ehu.ridesfx.domain.Traveler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class QueryReservationsController implements Controller {


    private MainGUIController mainGUIController;
    private BlFacade businessLogic;

    @FXML
    private TableView<Reservation> alertTable;
    @FXML
    private TableColumn<Reservation, String> departC;
    @FXML
    private TableColumn<Reservation, String> arrivalC;
    @FXML
    private TableColumn<Reservation, Integer> numPlacesC;
    @FXML
    private TableColumn<Reservation, Date> dateC;
    @FXML
    private TableColumn<Reservation, String> stateC;
    @FXML
    private Button deleteButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button acceptButton;
    @FXML
    public Button rejectButton;

    public QueryReservationsController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setQueryReservationsController(this);
    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    @FXML
    public void closeClick(ActionEvent event) {
        mainGUIController.showInitialGUI();
    }

    @FXML
    void initialize() {
        deleteButton.setVisible(false);
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);


        // Set the columns
        departC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRide().getFromLocation().getName()));
        arrivalC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRide().getToLocation().getName()));
        numPlacesC.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        dateC.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRide().getDate()));
        stateC.setCellValueFactory(new PropertyValueFactory<>("state"));

        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Show the delete button only if an item is selected

            if (businessLogic.getCurrentUser() instanceof Traveler) {
                deleteButton.setVisible(newSelection != null);
            } else if (businessLogic.getCurrentUser() instanceof Driver) {
                acceptButton.setVisible(newSelection != null);
                rejectButton.setVisible(newSelection != null);

            }


        });

        setReservations();
    }

    /**
     * This method sets the reservations of the current user in the table.
     */

    void setReservations() {
        // Get the reservations
        List<Reservation> reservations = businessLogic.getReservations();

        for (Reservation r : reservations) {
            System.out.println(r);
        }

        if (reservations.isEmpty()) {
            alertTable.placeholderProperty().setValue(new Label("You don't have any reservations yet"));
        }

        // Add the reservations to the table
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList(reservations);
        alertTable.setItems(reservationList);

        /*

        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

                if (newSelection != null) {
                    deleteButton.setVisible(true);
                }

        });

         */
    }

    /**
     * This method sets the reservations of the current driver in the table.
     */

    public void setReservationsDriver() {
        List<Reservation> reservations = businessLogic.getReservationDriver();
        System.out.println(reservations);
        if (reservations.isEmpty()) {
            alertTable.placeholderProperty().setValue(new Label("You don't have any reservations yet"));
        }

        ObservableList<Reservation> reservationList = FXCollections.observableArrayList(reservations);
        alertTable.setItems(reservationList);


    }

    /**
     * This method is called when the delete button is clicked, it deletes the reservation.
     * @param event
     */

    @FXML
    public void deleteReservation(ActionEvent event) {
        Reservation reservation = alertTable.getSelectionModel().getSelectedItem();
        businessLogic.deleteReservation(reservation);
        alertTable.getItems().remove(reservation);
        deleteButton.setVisible(false);
    }

    /**
     * This method is called when the refresh button is clicked, it populates the table with the reservations of the current user.
     * Depending on the user one method or another is called.
     * @param actionEvent
     */
    public void populateReservationsTable(ActionEvent actionEvent) {

        alertTable.getItems().clear();

        if (businessLogic.getCurrentUser() instanceof Traveler) {
            setReservations();
        } else if (businessLogic.getCurrentUser() instanceof Driver) {
            setReservationsDriver();
        }
    }

    /**
     * This method is called when the accept button is clicked, it changes the state of the reservation to "Accepted".
     * @param actionEvent
     */
    public void acceptReservation(ActionEvent actionEvent) {
        businessLogic.changeReservationState(alertTable.getSelectionModel().getSelectedItem(), "Accepted");
        //update table so that it shows the new state of the reservation
        setReservationsDriver();

    }

    /**
     * This method is called when the reject button is clicked, it changes the state of the reservation to "Rejected".
     * @param actionEvent
     */

    public void rejectReservation(ActionEvent actionEvent) {
        businessLogic.changeReservationState(alertTable.getSelectionModel().getSelectedItem(), "Rejected");
        //update table so that it shows the new state of the reservation
        setReservationsDriver();
    }

    /**
     * This method is called when the user logs out, it restarts the GUI of the QueryReservationsController.
     */
    public void restartGUIQueryReservation() {
        deleteButton.setVisible(false);
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);
    }
}