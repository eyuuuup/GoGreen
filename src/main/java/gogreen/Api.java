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

class Api {
    private static String site = "http://impact.brighterplanet.com/";
    private static final String key = "&key=58e64232-2368-4379-a71c-56c00675b46c";

    private Api() {}

    /**
     * this class calculates the amount of CO2 for a given input.
     * @param parameters parameters
     * @return kg of CO2
     */
    static double carbonAmount(String parameters) throws ConnectIOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(
                site + "" + parameters + "" + key);

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
