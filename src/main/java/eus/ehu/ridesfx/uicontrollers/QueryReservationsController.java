package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Reservation;
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

import java.util.List;

public class QueryReservationsController implements Controller {


    private MainGUIController mainGUIController;

    private BlFacade businessLogic;
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
    public Button refreshButton;


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

        // Set the columns
        departC.setCellValueFactory(cellData -> new SimpleStringProperty(((Reservation) cellData.getValue()).getRide().getDeparture()));
        arrivalC.setCellValueFactory(cellData -> new SimpleStringProperty(((Reservation) cellData.getValue()).getRide().getArrival()));
        numPlacesC.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        dateC.setCellValueFactory(cellData -> new SimpleObjectProperty<>(((Reservation) cellData.getValue()).getRide().getDate()));
        stateC.setCellValueFactory(new PropertyValueFactory<>("state"));

        setReservations();
    }

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

        alertTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteButton.setVisible(true);
            }
        });
    }

    @FXML
    public void deleteReservation(ActionEvent event) {
        Reservation reservation = (Reservation) alertTable.getSelectionModel().getSelectedItem();
        businessLogic.deleteReservation(reservation);
        alertTable.getItems().remove(reservation);
    }


    public void populateReservationsTable(ActionEvent actionEvent) {
        setReservations();
    }
}
