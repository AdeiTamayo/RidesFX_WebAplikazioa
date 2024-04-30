package eus.ehu.ridesfx.uicontrollers;

import com.google.gson.Gson;
import eus.ehu.ridesfx.API.Utils;
import eus.ehu.ridesfx.businessLogic.BlFacade;
import eus.ehu.ridesfx.domain.CityInfo;
import javafx.fxml.FXML;
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

    private String cityName;


    public CityInfoController(BlFacade bl, MainGUIController mainGUIController) {
        this.businessLogic = bl;
        setMainApp(mainGUIController);
        this.mainGUIController.setCityInfoController(this);
    }


    public void loadCityInfo() {
        cityName = cityNameToQueryTextField.getText();
        String username = "adeiProba";
        String json = Utils.request("http://api.geonames.org/searchJSON?q=" + cityName + "&maxRows=1&username=" + username);
        if (json.isEmpty()) {
            throw new RuntimeException("No city with the following name exists: " + cityName);
        }
        Gson gson = new Gson();

        CityInfo cityInfo = gson.fromJson(json, CityInfo.class);


        cityNameTextField.setText(cityInfo.getToponymName());
        countryNameTextField.setText(cityInfo.getCountryName());
        provinceNameTextField.setText(cityInfo.getAdminName1());
        populationTextField.setText(String.valueOf(cityInfo.getPopulation()));
        aboutTextField.setText(cityInfo.getFcodeName());
    }

    public void setCityName(String cityName) {
    this.cityName = cityName;
    }


    public void clearCityInfo() {
        cityNameToQueryTextField.clear();
        cityNameTextField.clear();
        countryNameTextField.clear();
        provinceNameTextField.clear();
        populationTextField.clear();
        aboutTextField.clear();

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
