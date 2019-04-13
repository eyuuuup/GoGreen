package gogreen;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.rmi.ConnectIOException;

/**
 * the API class gets the reduced carbon from the brighterplanet API.
 */
public class Api {
    private static String site = "http://impact.brighterplanet.com/";
    private static final String key = "&key=58e64232-2368-4379-a71c-56c00675b46c";

    /**
     * Private empty constructor.
     */
    private Api() {
    }

    /**
     * this class calculates the amount of CO2 for a given input.
     * @param parameters parameters
     * @return the co2 in kg
     * @throws ConnectIOException if we can't connect with the API we throw an error
     */
    public static double carbonAmount(String parameters) throws ConnectIOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(
                site + "" + parameters + "" + key);

        // we try to look up the produced co2 in the api
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject responseJson = new JSONObject(EntityUtils.toString(entity));
            String carbonString = responseJson.getJSONObject("decisions")
                    .getJSONObject("carbon").get("description").toString();
            return Double.parseDouble(carbonString.replace(" kg", ""));
        } catch (IOException e) {
            throw new ConnectIOException("Failed to calculate carbon footprint");
        }
    }
}
