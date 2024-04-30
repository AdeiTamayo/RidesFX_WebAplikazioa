package eus.ehu.ridesfx.uicontrollers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eus.ehu.ridesfx.API.Utils;
import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.CityInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CityInfoController implements Controller {


    private BlFacade businessLogic;

    private MainGUIController mainGUIController;

    @FXML
    private TextField aboutTextField;

    @FXML
    private TextField cityNameTextField;

    @FXML
    private TextField cityNameToQueryTextField;

    @FXML
    private TextField countryNameTextField;

    @FXML
    private TextField populationTextField;

    @FXML
    private TextField provinceNameTextField;

    @FXML
    private Button queryButton;

    private String cityName;


    public CityInfoController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setCityInfoController(this);
    }

    /**
     * Load city info from the geonames API
     */
    public void loadCityInfo() {


            cityName = cityNameToQueryTextField.getText();
            String username = "adeiProba";
            String json = Utils.request("http://api.geonames.org/searchJSON?username=" + username + "&name=" + cityName + "&maxRows=1&style=LONG");
            System.out.println("json: " + json);
            if (json.isEmpty()) {
                throw new RuntimeException("No city with the following name exists: " + cityName);
            }
            Gson gson = new Gson();

            CityInfo cityInfo = gson.fromJson(json, CityInfo.class);

            // Extract city info from the geonames array
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(json).getAsJsonObject();
            JsonArray geonames = jsonObject.getAsJsonArray("geonames");

            if (geonames.size() > 0) {
                JsonObject cityData = geonames.get(0).getAsJsonObject();
                cityInfo.setToponymName(cityData.get("toponymName").getAsString());
                cityInfo.setCountryName(cityData.get("countryName").getAsString());
                cityInfo.setAdminName1(cityData.get("adminName1").getAsString());
                cityInfo.setFcodeName(cityData.get("fcodeName").getAsString());
                cityInfo.setPopulation(cityData.get("population").getAsInt());
            }

            cityNameTextField.setText(cityInfo.getToponymName());
            countryNameTextField.setText(cityInfo.getCountryName());
            provinceNameTextField.setText(cityInfo.getAdminName1());
            populationTextField.setText(String.valueOf(cityInfo.getPopulation()));
            aboutTextField.setText(cityInfo.getFcodeName());

    }

    /**
     * Set the city name
     *
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Clear the city info
     */
    public void clearCityInfo() {
        cityNameToQueryTextField.setText("");
        cityNameTextField.setText("");
        countryNameTextField.setText("");
        provinceNameTextField.setText("");
        populationTextField.setText("");
        aboutTextField.setText("");


    }

    /**
     * Query the cities when button pressed.
     *
     * @param actionEvent
     */
    public void queryCities(ActionEvent actionEvent) {

        loadCityInfo();
    }

    /**
     * Clear the interface
     *
     * @param actionEvent
     */
    public void clearInterface(ActionEvent actionEvent) {
        clearCityInfo();
    }

    @FXML
    void initialize() {

        loadCityInfo();
    }

    @Override
    public void setMainApp(MainGUIController mainGUIController) {
        this.mainGUIController = mainGUIController;
    }



}
