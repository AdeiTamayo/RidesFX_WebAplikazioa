package eus.ehu.ridesfx.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    // Replace "YOUR_USERNAME" with your Geonames username
    private static final String USERNAME = "YOUR_USERNAME";

    public static void main(String[] args) {
        String cityName = "London"; // Specify the city name you want to retrieve information for

        try {
            // Construct the URL for the API request
            String apiUrl = "http://api.geonames.org/searchJSON?q=" + cityName + "&maxRows=1&username=" + USERNAME;
            URL url = new URL(apiUrl);

            // Open a connection to the API URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            // Here, you would extract the desired information from the JSON response
            System.out.println("Response: " + response.toString());

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
