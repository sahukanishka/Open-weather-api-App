package com.example.liveweather;


//api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
//b7d6a69e341e91ca31cefbd99139d193
//String apiurl = "api.openweathermap.org/data/2.5/weather?q=Delhi&appid=b7d6a69e341e91ca31cefbd99139d193";
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
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
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;



public class MainActivity extends AppCompatActivity  {


    private static  final int REQUEST_LOCATION=1;
    private RequestQueue mrequestqueu;
    private FloatingActionButton btn;
    private TextView yourcity_text;
    private TextView yourstate_text ;
    private  TextView yourcountry_text ;
    private EditText find_city;
    private TextView temp_text;
    private TextView city_text;
    private TextView description_text;
    private TextView country_text;
    private TextView weather_type_text;
    private TextView humidity_text ;
    private TextView preasure_text ;
    private   String city_name ;
    private  TextView showLocationTxt ;
    private  Button alertbox_btn ;
    LocationManager locationManager;
    String latitude,longitude,stateName,countryName,url;
    String cityName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        try
//        {
//            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.floatingActionButton2);
        temp_text = findViewById(R.id.temp_text);
        city_text = findViewById(R.id.city_text);
        description_text = findViewById(R.id.description_text);
        country_text = findViewById(R.id.country_text);
        weather_type_text = findViewById(R.id.weather_type_text);
        preasure_text = findViewById(R.id.pressure_text);
        humidity_text = findViewById(R.id.humidity_text);
//        find_city = findViewById(R.id.find_city);
        showLocationTxt = findViewById(R.id.locations_text);
        yourcity_text = findViewById(R.id.yourcity_text);
        yourstate_text = findViewById(R.id.yourstate_text);
        yourcountry_text = findViewById(R.id.yourcountry_text);
//        alertbox_btn = findViewById(R.id.alertbox_btn);

        //Add permission

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            getLocation();
//            checkforcurrentweather();
        }


        mrequestqueu = Volley.newRequestQueue(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Button pressed", Toast.LENGTH_LONG).show();
                     callalertbox();
                jsonParse();

            }
        });


    }

//    private  void checkforcurrentweather(){

//        if(city_name.isEmpty()){
//             url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=b7d6a69e341e91ca31cefbd99139d193";
//             jsonParse();
//        }
//        else{
//             url = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=b7d6a69e341e91ca31cefbd99139d193";
//             jsonParse();
//        }
//    }

    private void jsonParse() {

        Toast.makeText(MainActivity.this,"function called", Toast.LENGTH_LONG).show();


//        if(city_name.isEmpty()){
//            url = "http://api.openweathermap.org/data/2.5/weather?q="+"Delhi"+"&appid=b7d6a69e341e91ca31cefbd99139d193";
//
//        }
//        else{
//            url = "http://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=b7d6a69e341e91ca31cefbd99139d193";
//
//        }


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

                            double temp_cell = (mainobject.getDouble("temp") );
                            temp_cell = temp_cell - 273 ;
                            temp_cell =  Math.floor(temp_cell * 100) / 100;

                            String temp_view = String.valueOf(temp_cell);
                            String pressure_view = String.valueOf(mainobject.getDouble("pressure"));
                            String humidity_view = String.valueOf(mainobject.getDouble("humidity"));
                            String weather_view = weatherobject.getString("main");
                            String description_view = weatherobject.getString("description");
//
                            String country_view = sysobject.getString("country");
                            String city_view = response.getString("name");
//


                            Toast.makeText(MainActivity.this,
                                    "printing data", Toast.LENGTH_LONG).show();

                            temp_text.setText(temp_view +"°C");
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
    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
//                jsonParse();



                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
                     cityName = addresses.get(0).getAddressLine(0);
                     stateName = addresses.get(0).getAddressLine(1);
                   countryName = addresses.get(0).getAddressLine(2);

                    yourcity_text.setText(cityName);
                    yourstate_text.setText(stateName);
                    yourcountry_text.setText(countryName);

                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    yourcity_text.setText(cityName);
                    yourstate_text.setText(stateName);
                    yourcountry_text.setText(countryName);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    yourcity_text.setText(cityName);
                    yourstate_text.setText(stateName);
                    yourcountry_text.setText(countryName);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }


    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


    private void callalertbox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your City");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city_name = input.getText().toString();
                jsonParse();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }




}



