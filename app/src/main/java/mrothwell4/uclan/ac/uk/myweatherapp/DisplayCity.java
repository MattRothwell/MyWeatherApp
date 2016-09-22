package mrothwell4.uclan.ac.uk.myweatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayCity extends AppCompatActivity {


    static String json = "";
    String cityData ="";
    String api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button hbutton = (Button) findViewById(R.id.homedButton);
        hbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        final Button addbutton = (Button) findViewById(R.id.favdButton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCity.this, Favourites.class);
                startActivity(intent);
            }
        });

        Intent intent1 = getIntent();
        String location = intent1.getExtras().getString("city");

        api = this.getString(R.string.API);

        new cityWeatherAPI().execute(location, api);
    }

    public class cityWeatherAPI extends AsyncTask<String, Void, JSONObject> {
        protected JSONObject doInBackground(String... params){
            GetCityWeather cWeather = new GetCityWeather();
            JSONObject JSONdata = cWeather.getWeatherData(params[0], params[1]);
            //Log.e("city Weather.....", JSONdata.toString());
            return JSONdata;
        }

        protected void onPostExecute(JSONObject cityData){
            {
                TextView cityDataView= (TextView)findViewById(R.id.cityTextView);
                TextView cityDataTemp = (TextView)findViewById(R.id.cityTempView);
                TextView cityWind = (TextView)findViewById(R.id.cityWindView);
                try {
                    JSONArray jsonA = cityData.getJSONArray("weather");
                    JSONObject jsonO = jsonA.getJSONObject(0);
                    cityDataView.setText(jsonO.getString("description"));

                    JSONObject jsonTemp = cityData.getJSONObject("main");
                    cityDataTemp.setText(jsonTemp.getString("temp") + " \u2103");

                    JSONObject jsonWind = cityData.getJSONObject("wind");
                    cityWind.setText("Wind: " + jsonWind.getString("speed") + "MPH");

                } catch (JSONException e){
                e.printStackTrace();
            }
            }
        }
    }



}
