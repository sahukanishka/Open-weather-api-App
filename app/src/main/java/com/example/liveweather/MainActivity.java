package com.example.liveweather;


//api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
//b7d6a69e341e91ca31cefbd99139d193
//String apiurl = "api.openweathermap.org/data/2.5/weather?q=Delhi&appid=b7d6a69e341e91ca31cefbd99139d193";
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class MainActivity extends AppCompatActivity {



    private RequestQueue mrequestqueu;
    private FloatingActionButton btn;

    private EditText find_city;
    private TextView temp_text;
    private TextView city_text;
    private TextView description_text;
    private TextView country_text;
    private TextView weather_type_text;
    private TextView humidity_text ;
    private TextView preasure_text ;
    private   String city_name ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.floatingActionButton2);
        temp_text = findViewById(R.id.temp_text);
        city_text = findViewById(R.id.city_text);
        description_text = findViewById(R.id.description_text);
        country_text = findViewById(R.id.country_text);
        weather_type_text = findViewById(R.id.weather_type_text);
        preasure_text = findViewById(R.id.pressure_text);
        humidity_text = findViewById(R.id.humidity_text);
        find_city = findViewById(R.id.find_city);

        mrequestqueu = Volley.newRequestQueue(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Button pressed", Toast.LENGTH_LONG).show();
                     city_name = find_city.getText().toString();
                jsonParse();
            }
        });

    }

    private void jsonParse() {

        Toast.makeText(MainActivity.this,
                "function called", Toast.LENGTH_LONG).show();


        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=b7d6a69e341e91ca31cefbd99139d193";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this,
                                    "try block", Toast.LENGTH_LONG).show();
                            JSONArray weatherarray = response.getJSONArray("weather");
                            JSONObject weatherobject = weatherarray.getJSONObject(0);
                            JSONObject mainobject = response.getJSONObject("main");
                            JSONObject sysobject = response.getJSONObject("sys");


                            String temp_view = String.valueOf(mainobject.getDouble("temp"));
                            String pressure_view = String.valueOf(mainobject.getDouble("pressure"));
                            String humidity_view = String.valueOf(mainobject.getDouble("humidity"));
                            String weather_view = weatherobject.getString("main");
                            String description_view = weatherobject.getString("description");
//                            String icon_view = String.valueOf(weatherarray.getDouble("icon"));
                            String country_view = sysobject.getString("country");
                            String city_view = response.getString("name");
//                            String weather_view = weatherarray.getString(1);
//                            String description_view = weatherarray.getString(2);


//                            String temp = String.valueOf(mainobject.getDouble("temp"));
//                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//                            String city = response.getString("name");
//                              String value = String.valueOf(response.getInt("cases"));

                            Toast.makeText(MainActivity.this,
                                    "printing data", Toast.LENGTH_LONG).show();

                            temp_text.setText(temp_view);
                            city_text.setText(city_view);
                            country_text.setText(country_view);
                            description_text.setText(description_view);
                            weather_type_text.setText(weather_view);
                            humidity_text.setText(humidity_view);
                            preasure_text.setText(pressure_view);
//                            icon_text.setText(icon_view);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mrequestqueu.add(request);
    }


}



