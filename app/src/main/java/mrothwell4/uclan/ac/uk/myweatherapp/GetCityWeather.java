package mrothwell4.uclan.ac.uk.myweatherapp;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Matthew Rothwell on 04/03/2016.
 */
public class GetCityWeather {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=";

    public JSONObject getWeatherData(String location, String api) {
        HttpURLConnection con = null;
        InputStream is = null;
        BASE_URL = "http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=";
        try {
            BASE_URL+=api + "&q="+location;
Log.e("UL", BASE_URL);
            con = (HttpURLConnection) (new URL(BASE_URL)).openConnection();
            con.setRequestMethod("GET");
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            JSONObject citydata = new JSONObject(buffer.toString());
            Log.e("dd", citydata.toString());
            return citydata;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;

    }
}

