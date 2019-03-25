package gogreen;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class Api {
    private static String site = "http://impact.brighterplanet.com/";
    private static String key = "&key=5a927d96eca397b6659a3c361ce32254";
    /**
     * testing with the API.
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(CarbonAmount("automobile_trips.json?distance=200") + "kg for driving 100 km");
        System.out.println(CarbonAmount("flights.json?distance=100") + "kg for flying 100 km");
        System.out.println(CarbonAmount("bus_trips.json?distance=100") + "kg for bus traveling 100 km");
        System.out.println(CarbonAmount("diets.json?size=4") + "kg for eating 4 thingies");
        System.out.println(CarbonAmount("electricity_uses.json?energy=1200") + "kg for using 1200 MJ of energy");
    }

    /**
     * this class calculates the amount of CO2 for a given input.
     * @param parameters parameters
     * @return kg of CO2
     */
    static int CarbonAmount(String parameters) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(
                site + "" + parameters + "" + key);

        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            JSONObject responseJson = new JSONObject(EntityUtils.toString(entity));
            String carbonString = responseJson.getJSONObject("decisions").getJSONObject("carbon").get("description").toString();
            return (int) Double.parseDouble(carbonString.replace(" kg", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
