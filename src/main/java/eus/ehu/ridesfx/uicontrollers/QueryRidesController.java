package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.domain.Ride;
import eus.ehu.ridesfx.domain.Traveler;
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
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import eus.ehu.ridesfx.ui.MainGUI;
import eus.ehu.ridesfx.utils.Dates;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class QueryRidesController implements Controller {

    @FXML
    private BorderPane QueryRidesPane;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

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
    private ComboBox<String> comboArrivalCity;

    @FXML
    private ComboBox<String> comboDepartCity;

//  @FXML
//  private TableView<Event> tblEvents;

    @FXML
    private TableView<Ride> tblRides;

    @FXML
    private Button bookinButton;

    @FXML
    private Label alertMessage;

    @FXML
    private Label quantityOfSeatsLabel;

    @FXML
    private ComboBox<Integer> comboNumSeats;

    @FXML
    private Button clearButton;


    private MainGUI mainGUI;

    private MainGUIController mainGUIController;

    private List<LocalDate> datesWithBooking = new ArrayList<>();

    private BlFacade businessLogic;

    public QueryRidesController(BlFacade bl, MainGUIController mainGUIController) {
        businessLogic = bl;
        this.mainGUIController = mainGUIController;
        this.mainGUIController.setQueryRidesController(this);
    }


    @FXML
    void closeClick(ActionEvent event) {

        // mainGUI.showMain();


        //beste modu batera, mainGUIControllerren istantzia bat sortuta:


        mainGUIController.showInitialGUI();

    }

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

        comboNumSeats.setVisible(false);
        quantityOfSeatsLabel.setVisible(false);
        bookinButton.setVisible(false);




        // Update DatePicker cells when ComboBox value changes
        comboArrivalCity.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateDatePickerCellFactory(datepicker));

        ObservableList<String> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());

        ObservableList<String> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        // when the user selects a departure city, update the arrival cities
        comboDepartCity.setOnAction(e -> {
            arrivalCities.clear();
            arrivalCities.setAll(businessLogic.getDestinationCities(comboDepartCity.getValue()));
        });

        // a date has been chosen, update the combobox of Rides
        datepicker.setOnAction(actionEvent -> {
            if(comboDepartCity.getValue() == null || comboArrivalCity.getValue() == null){
                //TODO add an exception
                System.out.println(" Please select both a departure and arrival city");
                alertMessage.setText("Please select both a departure and arrival city");
                alertMessage.setVisible(true);
            }else {

                alertMessage.setVisible(false);
                alertButton.setVisible(false);

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

                    System.out.println("No rides found for this date");
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


                    bookinButton.setVisible(false);
                    alertButton.setVisible(true);
                    alertMessage.setVisible(false);
                }


                rideDate.setText(Dates.convertToDate(datepicker.getValue()).toString());
            }
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

                manageGUi();
            }
        });


    }


    /*

      private void setupEventSelection() {
        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {

            tblQuestions.getItems().clear();
            for (Question q : tblEvents.getSelectionModel().getSelectedItem().getQuestions()) {
              tblQuestions.getItems().add(q);
            }
          }
        });
      }
    */



    @FXML
    public void bookRide(ActionEvent actionEvent) {


        Date date = Dates.convertToDate(datepicker.getValue());
        Ride ride = tblRides.getSelectionModel().getSelectedItem();
        int numSeats = comboNumSeats.getValue();


        //suposatzen da erreserbatzen sahiatzen bada, traveler izan behar duela
        Traveler traveler = businessLogic.getCurrentTraveler();

        businessLogic.bookRide(date, ride, traveler, numSeats);
        alertMessage.setVisible(true);
        alertMessage.setText("Ride requested, pending driver approval");

        //A ride has been booked, update the combobox of Rides
        tblRides.getItems().clear();

        List<Ride> rides = businessLogic.getRides(comboDepartCity.getValue(), comboArrivalCity.getValue(), Dates.convertToDate(datepicker.getValue()));
        // List<Ride> rides = Arrays.asList(new Ride("Bilbao", "Donostia", Dates.convertToDate(datepicker.getValue()), 3, 3.5f, new Driver("pepe@pepe.com", "pepe")));
        for (Ride r : rides) {
            tblRides.getItems().add(r);
        }





    }


    /**
     * This method manages the GUI to work when the user selects a ride
     */
    public void manageGUi() {
        if (tblRides.getSelectionModel().getSelectedItem() != null) {
            bookinButton.setVisible(true);

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
    public void clearGUI(ActionEvent event){
        comboDepartCity.setValue(null);
        comboArrivalCity.setValue(null);
        datepicker.setValue(null);
        tblRides.getItems().clear();
        comboNumSeats.setVisible(false);
        quantityOfSeatsLabel.setVisible(false);
        bookinButton.setVisible(false);
        alertButton.setVisible(false);
        alertMessage.setVisible(false);
        rideDate.setText("");
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    public void createAlert(ActionEvent event) {
        alertMessage.setVisible(true);
        //check if the current user is a driver
        if(businessLogic.getCurrentUser() instanceof Driver){
            alertMessage.setText("Only travelers can create alerts");
            return;
        }
        if(businessLogic.createAlert(comboDepartCity.getValue(), comboArrivalCity.getValue(), comboNumSeats.getValue(), Dates.convertToDate(datepicker.getValue()), businessLogic.getCurrentUser().getEmail())==null){
            //Display error text: "Alert already exists"
            alertMessage.setText("Alert already exists");
        }
        else{
            alertMessage.setText("Alert created successfully");
        }
    }
}
