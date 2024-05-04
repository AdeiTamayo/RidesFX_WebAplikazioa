package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.configuration.UtilDate;
import eus.ehu.ridesfx.domain.*;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;

import javafx.util.Callback;
import eus.ehu.ridesfx.utils.Dates;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QueryRidesController implements Controller {


    @FXML
    private Button alertButton;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label rideDate;

    @FXML
    private TableColumn<Ride, String> qc1;

    @FXML
    private TableColumn<Ride, Integer> qc2;

    @FXML
    private TableColumn<Ride, Float> qc3;

    @FXML
    private ComboBox<Location> comboArrivalCity;

    @FXML
    private ComboBox<Location> comboDepartCity;

    @FXML
    private TableView<Ride> tblRides;

    @FXML
    private Button bookingButton;

    @FXML
    private Label alertMessage;

    @FXML
    private Label correctMessage;

    @FXML
    private Label quantityOfSeatsLabel;

    @FXML
    private ComboBox<Integer> comboNumSeats;

    @FXML
    private Label loggedInError;

    private MainGUIController mainGUIController;

    private List<LocalDate> datesWithBooking = new ArrayList<>();

    private BlFacade businessLogic;

    public QueryRidesController(BlFacade bl, MainGUIController mainGUIController) {
        businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setQueryRidesController(this);
    }


    @FXML
    void closeClick(ActionEvent event) {


        mainGUIController.showInitialGUI();

    }

    /**
     * This method marks the events for the month and year shown
     *
     * @param year
     * @param month
     */
    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year, month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            datesWithBooking.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    // we need to mark (highlight in pink) the events for the previous, current and next month
    // this method will be called when the user clicks on the << or >> buttons
    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    /**
     * This method updates the DatePicker cell factory
     *
     * @param datePicker
     */
    private void updateDatePickerCellFactory(DatePicker datePicker) {

        List<Date> dates = businessLogic.getDatesWithRides(comboDepartCity.getValue(), comboArrivalCity.getValue());

        // extract datesWithBooking from rides
        datesWithBooking.clear();
        for (Date day : dates) {
            datesWithBooking.add(Dates.convertToLocalDateViaInstant(day));
        }

        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (datesWithBooking.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            } else {
                                this.setStyle("-fx-background-color: rgb(235, 235, 235)");
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    void initialize() {

        //Disable alert button until a date is selected where there are no available rides
        alertButton.setVisible(false);
        alertMessage.setVisible(false);
        alertMessage.setAlignment(Pos.CENTER);
        loggedInError.setVisible(false);
        correctMessage.setVisible(false);
        correctMessage.setAlignment(Pos.CENTER);
        comboNumSeats.setVisible(false);
        quantityOfSeatsLabel.setVisible(false);
        bookingButton.setVisible(false);

        alertMessage.setStyle("-fx-text-fill: red");
        correctMessage.setStyle("-fx-text-fill: green");

        updateComboBox();


        // a date has been chosen, update the combobox of Rides
        datepicker.setOnAction(actionEvent -> {

        });

        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {


                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();

                    String month = ((Label) (labels.get(0))).getText();
                    String year = ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);

                    // print month value
                    System.out.println("Month:" + ym.getMonthValue());


                });
            });
        });

        // show just the driver's name in column1
        qc1.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ride, String> data) {
                Driver driver = data.getValue().getDriver();
                return new SimpleStringProperty(driver != null ? driver.getName() : "<no name>");
            }
        });

        qc2.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        qc3.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add listener to TableView's selection model to know when to change the GUI with new buttons
        tblRides.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                if (mainGUIController.getCurrentUser().getClass().getSimpleName().equals("User")) {
                    System.out.println("Please login to book a ride");
                    loggedInError.setVisible(true);
                } else {
                    manageGUi();
                }
            }
        });


    }


    /**
     * This method books a ride
     *
     * @param actionEvent
     */
    @FXML
    public void makeReservation(ActionEvent actionEvent) {

        Ride ride = tblRides.getSelectionModel().getSelectedItem();
        int numSeats = comboNumSeats.getValue();


        Traveler traveler = businessLogic.getCurrentTraveler();


        LocalDateTime currentDateTime = LocalDateTime.now();

        // ZoneId zoneId = ZoneId.systemDefault();
        Date currentDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());


        businessLogic.makeReservation(traveler, ride, numSeats, currentDate);
        correctMessage.setVisible(true);
        correctMessage.setText("Ride requested, pending driver approval");
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event2 -> {
            correctMessage.setVisible(false);

        });
        pause.play();

        //A ride has been booked, update the combobox of Rides
        tblRides.getItems().clear();

        List<Ride> rides = businessLogic.getRides(comboDepartCity.getValue(), comboArrivalCity.getValue(), Dates.convertToDate(datepicker.getValue()));

        for (Ride r : rides) {
            tblRides.getItems().add(r);
        }


    }


    /**
     * This method manages the GUI to work when the user selects a ride
     */
    public void manageGUi() {
        if (tblRides.getSelectionModel().getSelectedItem() != null) {
            bookingButton.setVisible(true);
            loggedInError.setVisible(false);

            // Get the number of seats available for the selected ride and show them in the combobox
            Ride ride = tblRides.getSelectionModel().getSelectedItem();
            List<Integer> availableSeats = businessLogic.getAvailableSeats(ride);
            ObservableList<Integer> seats = FXCollections.observableArrayList(availableSeats);
            comboNumSeats.setItems(seats);

            comboNumSeats.setVisible(true);
            quantityOfSeatsLabel.setVisible(true);
        }
    }

    /**
     * This method clears the GUI
     */
    @FXML
    public void clearGUI(ActionEvent event) {
        comboDepartCity.setValue(null);
        comboArrivalCity.setValue(null);
        datepicker.setValue(null);
        tblRides.getItems().clear();
        comboNumSeats.setVisible(false);
        quantityOfSeatsLabel.setVisible(false);
        bookingButton.setVisible(false);
        alertButton.setVisible(false);
        alertMessage.setVisible(false);
        correctMessage.setVisible(false);
        rideDate.setText("");
        loggedInError.setVisible(false);
    }


    /**
     * This method goes to the login page
     */
    @FXML
    void goToLogin() {
        mainGUIController.showLogin();

    }


    /**
     * This method creates an alert
     *
     * @param event
     */
    @FXML
    public void createAlert(ActionEvent event) {
        loggedInError.setVisible(false);
        correctMessage.setVisible(false);

        if (mainGUIController.getCurrentUser().getClass().getSimpleName().equals("User")) {
            System.out.println("Please login to book a ride");
            loggedInError.setVisible(true);
            return;
        }
        //check if the number of seats is selected
        if (comboNumSeats.getValue() == null) {
            alertMessage.setVisible(true);
            alertMessage.setText("You need to select the number of seats you want to book");
            return;
        }

        //check if the current user is a driver
        if (businessLogic.getCurrentUser() instanceof Driver) {
            alertMessage.setVisible(true);
            alertMessage.setText("Only travelers can create alerts");
            return;
        }

        //check if the alert already exists
        if (businessLogic.createAlert(comboDepartCity.getValue(), comboArrivalCity.getValue(), comboNumSeats.getValue(), Dates.convertToDate(datepicker.getValue()), businessLogic.getCurrentUser().getEmail()) == null) {
            //Display error text: "Alert already exists"
            alertMessage.setVisible(true);
            alertMessage.setText("Alert already exists");

        } else {
            correctMessage.setVisible(true);
            correctMessage.setText("Alert created successfully");


        }
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event2 -> {
            correctMessage.setVisible(false);
            alertMessage.setVisible(false);
        });
        pause.play();

    }


    /**
     * When button is selected Populate and Query Rides.
     *
     * @param event
     */
    @FXML
    void QueryRides(ActionEvent event) {
        comboNumSeats.setVisible(false);
        quantityOfSeatsLabel.setVisible(false);
        bookingButton.setVisible(false);
        correctMessage.setVisible(false);
        loggedInError.setVisible(false);
        Date date = Dates.convertToDate(datepicker.getValue());

        if (comboDepartCity.getValue() == null || comboArrivalCity.getValue() == null) {

            alertMessage.setText("Please select both a departure and arrival city");
            alertMessage.setVisible(true);
        }
        //check if the date is later than today
        else if (new Date().compareTo(date) > 0) {
            alertMessage.setVisible(true);
            alertMessage.setText("The date you entered must be later than today");

        } else {
            alertMessage.setVisible(false);
            alertButton.setVisible(false);
            correctMessage.setVisible(false);

            tblRides.getItems().clear();
            // Vector<domain.Ride> events = businessLogic.getEvents(Dates.convertToDate(datepicker.getValue()));
            List<Ride> rides = businessLogic.getRides(comboDepartCity.getValue(), comboArrivalCity.getValue(), Dates.convertToDate(datepicker.getValue()));
            // List<Ride> rides = Arrays.asList(new Ride("Bilbao", "Donostia", Dates.convertToDate(datepicker.getValue()), 3, 3.5f, new Driver("pepe@pepe.com", "pepe")));
            for (Ride ride : rides) {
                tblRides.getItems().add(ride);
            }

            //if there are no rides, show a message and able the button of creating an alert
            if (rides.isEmpty() && comboArrivalCity.getValue() != null && comboDepartCity.getValue() != null) {
                Label placeholderLabel = new Label("No rides found. Click on the button below to create an alert.");
                placeholderLabel.setAlignment(Pos.CENTER);
                tblRides.setPlaceholder(placeholderLabel);
                quantityOfSeatsLabel.setVisible(true);
                comboNumSeats.setVisible(true);
                //delete elements from the combobox
                comboNumSeats.getItems().clear();
                //add nums from 1 to 7
                List<Integer> availableSeats = new ArrayList<>();
                for (int i = 1; i <= 7; i++) {
                    availableSeats.add(i);
                }
                ObservableList<Integer> observableSeats = FXCollections.observableArrayList(availableSeats);
                comboNumSeats.setItems(observableSeats);


                bookingButton.setVisible(false);
                alertButton.setVisible(true);
                alertMessage.setVisible(false);
                correctMessage.setVisible(false);
            }


            rideDate.setText(Dates.convertToDate(datepicker.getValue()).toString());
        }
    }

    /**
     * This method updates the Comboboxes when ComboBox value changes
     */

    public void updateComboBox() {
        // Update DatePicker cells when ComboBox value changes
        comboArrivalCity.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateDatePickerCellFactory(datepicker));

        ObservableList<Location> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());

        ObservableList<Location> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        /*
        comboArrivalCity.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (comboDepartCity.getValue() != null && newVal != null) {
                datepicker.fireEvent(new ActionEvent()); // Force datepicker action handler to run
            }
        });

         */

        // when the user selects a departure city, update the arrival cities
        comboDepartCity.setOnAction(e -> {
            arrivalCities.clear();
            arrivalCities.setAll(businessLogic.getDestinationCities(comboDepartCity.getValue()));

            /*
            if (comboArrivalCity.getValue() != null) {
                datepicker.fireEvent(new ActionEvent()); // Force datepicker action handler to run if a city is already selected
            }

             */

        });
    }


    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }

    public void setView() {
        alertMessage.setVisible(false);
        loggedInError.setVisible(false);
        correctMessage.setVisible(false);
    }
}
