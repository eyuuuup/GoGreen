package gogreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
    /**
     * testing with the API.
     * @param args args
     * @throws IOException e
     */
    public static void main(String[] args) throws IOException {
        //String url = "https://apis.berkeley.edu/coolclimate/footprint-defaults?input_location_mode=2&input_location=Rotterdam, Nederland&input_income=1&input_size=1&app_id=8dd1d937&app_key=889f860286d08fbfc33b7e2317049eb6";
        String url = "http://impact.brighterplanet.com/automobiles.json?annual_distance=100&timeframe=2019-01-01%2F2020-01-01";
        URL request = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) request.openConnection();
        connection.setRequestMethod("GET");
        getBody(connection);
    }

    static void getBody(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String string = response.toString();
        string = string.substring(string.lastIndexOf("value"), string.lastIndexOf("units"));
    }
}
